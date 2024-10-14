import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student extends User {
    private int studentId;

    public Student(String email, String password) {
        super(email, password);
        this.studentId = fetchStudentId(email); // Fetch student ID based on email
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void viewAvailableCourses() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM courses";
            PreparedStatement statement = connection.prepareStatement(query);
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
        String courseCode = scanner.nextLine();

        if (courseExists(courseCode)) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO course_registration (student_id, course_code) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, this.studentId);
                statement.setString(2, courseCode);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Successfully registered for course: " + courseCode);
                } else {
                    System.out.println("Failed to register for course.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Course does not exist.");
        }
    }

    private boolean courseExists(String courseCode) {
        boolean exists = false;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM courses WHERE course_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            ResultSet resultSet = statement.executeQuery();
            exists = resultSet.next();
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
            statement.setInt(1, this.studentId);
            statement.setString(2, complaintText);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Complaint submitted successfully.");
            } else {
                System.out.println("Failed to submit complaint.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
