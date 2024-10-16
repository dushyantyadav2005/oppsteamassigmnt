import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class courseManager {

    public void addCourse(Scanner scanner) {
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Semester ID: ");
        int sem_id = scanner.nextInt();
        System.out.print("Enter Course Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course Prerequisites: ");
        String pre = scanner.nextLine();
        System.out.print("Enter Drop Deadline (YYYY-MM-DD): ");
        String deadlineInput = scanner.nextLine();
        System.out.print("Enter capacity: ");
        int c = scanner.nextInt();

        LocalDate dropDeadline = LocalDate.parse(deadlineInput, DateTimeFormatter.ISO_LOCAL_DATE);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO courses (course_code, course_name, credits, semester_id, prerequisites, drop_deadline,capacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            statement.setString(2, courseName);
            statement.setInt(3, credits);
            statement.setInt(4, sem_id);
            statement.setString(5, pre);
            statement.setDate(6, java.sql.Date.valueOf(dropDeadline)); // Set the drop_deadline
            statement.setInt(7, c); // Set the capacity

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

    public void updateCourse(Scanner scanner) {
        System.out.println("\nOptions to change :");
        System.out.println("1. Course Name : ");
        System.out.println("2. Credits : ");
        System.out.println("3. Semester : ");
        System.out.println("4. Prerequisites : ");
        System.out.println("5. Drop Deadline : ");
        System.out.println("6. Return ");
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
                System.out.print("Enter Course Code to update: ");
                courseCo = scanner.nextLine();
                System.out.print("Enter Semester ID to update: ");
                int sem = scanner.nextInt();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE courses SET semester_id = ? WHERE course_code = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, sem);
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
            case 4:
                System.out.print("Enter Course Code to update: ");
                courseCo = scanner.nextLine();
                System.out.print("Enter Prerequisites: ");
                String pre = scanner.nextLine();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE courses SET prerequisites = ? WHERE course_code = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, pre);
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
            case 5:
                System.out.print("Enter Course Code to update: ");
                courseCo = scanner.nextLine();
                System.out.print("Enter New Drop Deadline (YYYY-MM-DD): ");
                String deadlineInput = scanner.nextLine();

                LocalDate dropDeadline = LocalDate.parse(deadlineInput, DateTimeFormatter.ISO_LOCAL_DATE);

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE courses SET drop_deadline = ? WHERE course_code = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setDate(1, java.sql.Date.valueOf(dropDeadline)); // Set the drop_deadline
                    statement.setString(2, courseCo);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Drop deadline updated successfully.");
                    } else {
                        System.out.println("Failed to update drop deadline or course not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                System.out.println("Logging out...");
                return; // Exit the update course menu
            default:
                System.out.println("Invalid choice.");
        }
    }


//    public void deleteCourse(Scanner scanner) {
//        System.out.print("Enter Course Code to delete: ");
//        String courseCode = scanner.nextLine();
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String query = "DELETE FROM course_registration WHERE course_code = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, courseCode);
//
//            int rowsAffected = statement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Course deleted successfully.");
//            } else {
//                System.out.println("Failed to delete course or course not found.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
