import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

class CourseFullException extends Exception {
    public CourseFullException(String message) {
        super(message);
    }
}
 class DropDeadlinePassedException extends Exception {
    public DropDeadlinePassedException(String message) {
        super(message);
}
}

public class Student extends User {
    private int studentId;

    public Student(String email, String password) throws InvalidLoginException  {
        super(email, password);
        try {
            if (super.login()) {
                this.studentId = fetchStudentId(email); // Fetch student ID based on email
            } else {
                throw new InvalidLoginException("Invalid user not found");
            }
        }catch(InvalidLoginException e) {
            System.out.println("invalid user not found");
            System.exit(0);
        }
    }

    private int fetchStudentId(String email) {
        int id = -1;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT student_id FROM students WHERE user_id = (SELECT user_id FROM users WHERE email = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("student_id");
                System.out.println(id);
            }else{
                System.out.println("No student found");
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void viewAvailableCourses() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to get available courses for the student based on their current semester
            String query = "SELECT * FROM courses WHERE semester_id = (SELECT current_semester_id FROM students WHERE student_id = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Available Courses:");
            while (resultSet.next()) {
                System.out.println("Course Code: " + resultSet.getString("course_code") +
                        ", Course Name: " + resultSet.getString("course_name") +
                        ", Credits: " + resultSet.getInt("credits"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerForCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course Code to register: ");
        String courseCode = scanner.nextLine().trim();

        if (courseExists(courseCode)&&!isAlreadyPresent(courseCode)) {
            Connection connection = null;
            PreparedStatement checkCapacityStmt = null;
            ResultSet rs = null;

            try {
                connection = DatabaseConnection.getConnection();

                // Check if the course is full
                String checkCapacityQuery = "SELECT capacity, (SELECT COUNT(*) FROM course_registration WHERE course_code = ?) AS enrolled_students FROM courses WHERE course_code = ?";
                checkCapacityStmt = connection.prepareStatement(checkCapacityQuery);
                checkCapacityStmt.setString(1, courseCode);
                checkCapacityStmt.setString(2, courseCode);
                rs = checkCapacityStmt.executeQuery();

                if (rs.next()) {
                    int capacity = rs.getInt("capacity");
                    int enrolledStudents = rs.getInt("enrolled_students");

                    if (enrolledStudents >= capacity) {
                        throw new CourseFullException("The course " + courseCode + " is full.");
                    } else {
                        // Course has available slots, proceed with registration
                        System.out.println("Course has available slots. Proceeding with registration...");

                        // Insert into course_registration table
                        String insertQuery = "INSERT INTO course_registration (student_id, course_code) VALUES (?, ?)";
                        PreparedStatement registerStmt = connection.prepareStatement(insertQuery);
                        registerStmt.setInt(1, this.studentId); // Assuming studentId is defined in your class
                        registerStmt.setString(2, courseCode);

                        int rowsAffected = registerStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Successfully registered for course: " + courseCode);
                        } else {
                            System.out.println("Failed to register for course.");
                        }

                        // Close register statement
                        registerStmt.close();
                    }
                }
            } catch (CourseFullException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (checkCapacityStmt != null) checkCapacityStmt.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Course does not exist or prerequisites are not met.");
        }
    }


    private boolean courseExists(String courseCode) {
        boolean exists = false;
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check for prerequisites of the course
            String query = "SELECT prerequisites FROM courses WHERE course_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            ResultSet resultSet = statement.executeQuery();

            String prerequisites = null;
            if (resultSet.next()) {
                prerequisites = resultSet.getString("prerequisites");
            }

            String[] prerequisiteCourses = {};
            if (prerequisites != null) {
                prerequisiteCourses = prerequisites.split(",");
            }

            // Fetch completed courses for the student
            query = "SELECT course_done FROM students WHERE student_id = ?";
            PreparedStatement statement2 = connection.prepareStatement(query);
            statement2.setInt(1, studentId);
            resultSet = statement2.executeQuery();

            String doneCourses = null;
            if (resultSet.next()) {
                doneCourses = resultSet.getString("course_done");
            }

            HashSet<String> doneCoursesSet = new HashSet<>();
            if (doneCourses != null) {
                String[] doneCoursesArray = doneCourses.split(",");
                for (String co : doneCoursesArray) {
                    doneCoursesSet.add(co.trim());
                }

                // Check if all prerequisites are met
                for (String c : prerequisiteCourses) {
                    if (!doneCoursesSet.contains(c.trim())) {
                        System.out.println("Missing prerequisite: " + c);
                        return false;
                    }
                }
            }

            exists = true; // Course exists and prerequisites are met
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void dropCourse() {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter Course Code to drop: ");
        String courseCode = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkDeadlineQuery = "SELECT drop_deadline FROM courses WHERE course_code = ?";
            PreparedStatement checkDeadlineStmt = connection.prepareStatement(checkDeadlineQuery);
            checkDeadlineStmt.setString(1, courseCode);
            ResultSet rs = checkDeadlineStmt.executeQuery();

            if (rs.next()) {
                Date dropDeadline = rs.getDate("drop_deadline");
                LocalDate currentDate = LocalDate.now();

                if (currentDate.isAfter(((java.sql.Date) dropDeadline).toLocalDate())) {
                    throw new DropDeadlinePassedException("The drop deadline for the course " + courseCode + " has passed.");
                }

                // Proceed to delete the course
                String query = "DELETE FROM course_registration WHERE course_code = ? AND student_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, courseCode);
                statement.setInt(2, studentId);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Course deleted successfully.");
                } else {
                    System.out.println("Failed to delete course or course not found.");
                }
            } else {
                System.out.println("Course not found.");
            }
        } catch (DropDeadlinePassedException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlreadyPresent(String courseCode) {
        boolean exists = false;
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if the student is already registered for the course
            String query = "SELECT registration_id FROM course_registration WHERE course_code = ? AND student_id = ?";
            PreparedStatement statement3 = connection.prepareStatement(query);
            statement3.setString(1, courseCode);
            statement3.setInt(2, studentId);
            ResultSet resultSet = statement3.executeQuery();

            if (resultSet.next()) {
                System.out.println("Student is already registered for the course");
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void viewSchedule() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM course_registration WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.studentId);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Your Schedule:");
            while (resultSet.next()) {
                System.out.println("Course Code: " + resultSet.getString("course_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void trackGrades() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM grades WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.studentId);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Your Grades:");
            while (resultSet.next()) {
                System.out.println("Course Code: " + resultSet.getString("course_code") +
                        ", Grade: " + resultSet.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void submitComplaint() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your complaint: ");
        String complaintText = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO complaints (student_id, complaint_text) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.studentId);  // Assuming 'studentId' is defined in your class
            statement.setString(2, complaintText);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Complaint submitted successfully.");

                // Query to retrieve the complaint_id of the inserted complaint
                String selectSQL = "SELECT complaint_id FROM complaints WHERE complaint_text = ?";
                PreparedStatement selectPreparedStatement = connection.prepareStatement(selectSQL);
                selectPreparedStatement.setString(1, complaintText);
                ResultSet resultSet1 = selectPreparedStatement.executeQuery();

                // Check if the result set has data before accessing it
                if (resultSet1.next()) {
                    int id = resultSet1.getInt("complaint_id");
                    System.out.println("Complaint ID is: " + id);
                } else {
                    System.out.println("No complaint found with the given text.");
                }
            } else {
                System.out.println("Failed to submit complaint.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void statusCompalin()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your complaint id: ");
        int c_id = scanner.nextInt();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT status FROM complaints WHERE complaint_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, c_id);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                String status = resultSet.getString("status");
                System.out.println("Complaint"+" Status is: " + status);
            } else {
                System.out.println("No complain found with the given id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
