import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class studentManager {
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

        System.out.print("Enter current semester: ");
        int currsem = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter course done: ");
        String doned = scanner.nextLine();

        System.out.print("Enter contact details (phone/email): ");
        String contactDetails = scanner.nextLine();
//        scanner.nextLine();  // Consume newline

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
            String query = "INSERT INTO students (user_id, student_id, enrollment_date, major,current_semester_id,course_done,contact_details) VALUES (?, ?, ?, ?, ?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, student_id);
            statement.setString(3, enrollmentDate);
            statement.setString(4, student_major);
            statement.setInt(5, currsem);
            statement.setString(6, doned);
            statement.setString(7, contactDetails);

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
        System.out.println("3. Contact details");
        System.out.println("4. Logout");
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
                // Update major
                System.out.print("Enter Student ID: ");
                student_id = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter new contact details (phone/email): ");
                String contactDetails = scanner.nextLine();

                // Update in database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE students SET major = ? WHERE student_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, contactDetails);
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

            case 4:
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
}
