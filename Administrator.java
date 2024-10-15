import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Administrator extends User {
    final static String email = "dyadav15112005@gmail.com";
    final static String password = "12345";

    public Administrator(String e, String p) {
        super(e, p);  // Assuming the superclass has this constructor
        if (e.equals(email) && p.equals(password)) {
        } else {
            System.out.println("Invalid email or password");
            System.exit(0);
        }
    }

    public void manageCourseCatalog() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Manage Course Catalog:");
        System.out.println("1. Add Course");
        System.out.println("2. Update Course");
        System.out.println("3. Delete Course");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                addCourse(scanner);
                break;
            case 2:
                updateCourse(scanner);
                break;
            case 3:
                deleteCourse(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addCourse(Scanner scanner) {
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Course Credits: ");
        int credits = scanner.nextInt();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO courses (course_code, course_name,credits) VALUES (?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            statement.setString(2, courseName);
            statement.setInt(3, credits);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Failed to add course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourse(Scanner scanner) {
        System.out.println("\nOption to change :");
//        System.out.println("1. course code : ");
        System.out.println("1. course name : ");
        System.out.println("2. credits : ");
        System.out.println("3. return ");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter Course Code to update: ");
                String courseCod = scanner.nextLine();
                System.out.print("Enter New Course Name: ");
                String newCourseName = scanner.nextLine();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE courses SET course_name = ? WHERE course_code = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, newCourseName);
                    statement.setString(2, courseCod);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Course updated successfully.");
                    } else {
                        System.out.println("Failed to update course or course not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.print("Enter Course Code to update: ");
                String courseCo = scanner.nextLine();
                System.out.print("Enter New Credits: ");
                String newCredits = scanner.nextLine();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE courses SET credits = ? WHERE course_code = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, newCredits);
                    statement.setString(2, courseCo);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Course updated successfully.");
                    } else {
                        System.out.println("Failed to update course or course not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                System.out.println("Logging out...");
                return; // Exit the student menu
            default:
                System.out.println("Invalid choice.");
        }

    }

    private void deleteCourse(Scanner scanner) {
        System.out.print("Enter Course Code to delete: ");
        String courseCode = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM courses WHERE course_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("Failed to delete course or course not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void manageStudentRecords() {
         Scanner scanner = new Scanner(System.in);
        System.out.println("\nStudent changes:");
        System.out.println("1. ADD students : ");
        System.out.println("2. UPDATE students");
        System.out.println("3. DELETE students : ");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addStudent(scanner);
                break;
            case 2:
                updateStudent(scanner);
                break;
            case 3:
                deleteStudent(scanner);
                break;
            case 4:
                System.out.println("Logging out...");
                return; // Exit the student menu
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to add a student
    public void addStudent(Scanner scanner) {
        int user_id = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertSQL = "INSERT INTO users (email, password, user_type) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            System.out.print("Enter email: ");
            String emailInput = scanner.nextLine();

            System.out.print("Enter password: ");
            String passwordInput = scanner.nextLine();

            System.out.print("Enter user type (student, professor, administrator): ");
            String userTypeInput = scanner.nextLine();

            // Setting the parameters for the PreparedStatement
            preparedStatement.setString(1, emailInput);
            preparedStatement.setString(2, passwordInput);
            preparedStatement.setString(3, userTypeInput);

            // Executing the insert query
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User inserted successfully.");

                // Query to retrieve the user_id of the inserted user
                String selectSQL = "SELECT user_id FROM users WHERE email = ?";
                PreparedStatement selectPreparedStatement = connection.prepareStatement(selectSQL);
                selectPreparedStatement.setString(1, emailInput);
                ResultSet rs = selectPreparedStatement.executeQuery();

                // Check if the result set has data
                if (rs.next()) {
                    user_id = rs.getInt("user_id");  // Retrieve the user_id
                    System.out.println("Retrieved user_id: " + user_id);
                } else {
                    System.out.println("No user found with the given email.");
                }
            } else {
                System.out.println("User insertion failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

// Student information
        System.out.print("Enter Student ID: ");
        int student_id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Student Major: ");
        String student_major = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("Enter enrollment date of student in format yyyy-MM-dd: ");
        String enrollmentDate = scanner.nextLine();

        try {
            // Parse the input string to LocalDate
            LocalDate date = LocalDate.parse(enrollmentDate, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the correct format.");
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO students (user_id, student_id, enrollment_date, major) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, student_id);
            statement.setString(3, enrollmentDate);
            statement.setString(4, student_major);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    // Method to update student information
    public void updateStudent(Scanner scanner) {
        System.out.println("\nWhat do you want to update?:");
        System.out.println("1. Enrollment date");
        System.out.println("2. Major");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        switch (choice) {
            case 1:
                // Update enrollment date
                System.out.print("Enter Student ID: ");
                int student_id = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                System.out.print("Enter enrollment date of student in format yyyy-MM-dd: ");
                String enrollmentDate = scanner.nextLine();

                // Validate date format
                try {
                    LocalDate date = LocalDate.parse(enrollmentDate, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in the correct format.");
                    return; // Exit the case if the date format is invalid
                }

                // Update in database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE students SET enrollment_date = ? WHERE student_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, enrollmentDate);
                    statement.setInt(2, student_id);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Student updated successfully.");
                    } else {
                        System.out.println("Failed to update student or student not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                // Update major
                System.out.print("Enter Student ID: ");
                student_id = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter new major: ");
                String newmajor = scanner.nextLine();

                // Update in database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE students SET major = ? WHERE student_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, newmajor);
                    statement.setInt(2, student_id);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Student updated successfully.");
                    } else {
                        System.out.println("Failed to update student or student not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                System.out.println("Logging out...");
                return; // Exit the student menu

            default:
                System.out.println("Invalid choice.");
        }
    }


    // Method to delete a student
public void deleteStudent(Scanner scanner) {
    System.out.print("Enter Student ID to delete: ");
    String student_id = scanner.nextLine();

    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "DELETE FROM students WHERE student_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, student_id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Failed to delete student or student not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
 }
}
    public void assignProfessors() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course Code to assign professor: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Professor Email: ");
        String professorEmail = scanner.nextLine();

        // Assigning professor to the course logic goes here
    }

    public void handleComplaints() {
        Scanner scanner= new Scanner(System.in);
        System.out.println("\nComplain options :");
        System.out.println("1. View complain : ");
        System.out.println("2. resolve complain : ");
        System.out.println("3. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();// Consume newline

        switch (choice) {
            case 1:
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT * FROM complaints";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();

                    System.out.println("Pending Complaints:");
                    while (resultSet.next()) {
                        System.out.println("Complaint ID: " + resultSet.getInt("complaint_id") +
                                ", Complaint: " + resultSet.getString("complaint_text"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT * FROM complaints";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();

                    System.out.println("Pending Complaints:");
                    while (resultSet.next()) {
                        System.out.println("Complaint ID: " + resultSet.getInt("complaint_id") +
                                ", Complaint: " + resultSet.getString("complaint_text"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                System.out.println();
                System.out.println("compalint to resolve id : ");
                int y=scanner.nextInt();
//                scanner.nextInt();
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "DELETE FROM complaints WHERE complaint_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, y);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Complaint resolved successfully.");
                    } else {
                        System.out.println("Failed to resolve compalin.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
               return;
            default:
                System.out.println("Invalid choice.");
        }

    }
}
