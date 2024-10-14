import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator extends User {
    public Administrator(String email, String password) {
        super(email, password);
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

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO courses (course_code, course_name) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            statement.setString(2, courseName);

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
        System.out.print("Enter Course Code to update: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter New Course Name: ");
        String newCourseName = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE courses SET course_name = ? WHERE course_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newCourseName);
            statement.setString(2, courseCode);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("Failed to update course or course not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        // Similar to manage courses but for students
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
    }
}
