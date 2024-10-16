import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class courseManager {

    public void addCourse(Scanner scanner) {
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Semester id: ");
        int sem_id = scanner.nextInt();
        System.out.print("Enter Course Credits: ");
        int credits = scanner.nextInt();
        System.out.print("Enter Course prerequisites: ");
        String pre = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO courses (course_code, course_name,credits,semester_id,prerequisites) VALUES (?, ?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            statement.setString(2, courseName);
            statement.setInt(3, credits);
            statement.setInt(4, sem_id);
            statement.setString(5, pre);


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
        System.out.println("\nOption to change :");
//        System.out.println("1. course code : ");
        System.out.println("1. course name : ");
        System.out.println("2. credits : ");
        System.out.println("3. Semester : ");
        System.out.println("4. prerequisites : ");
        System.out.println("5. return ");
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
                System.out.print("Enter prerequisites : ");
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
            case 5:
                System.out.println("Logging out...");
                return; // Exit the student menu
            default:
                System.out.println("Invalid choice.");
        }

    }

    public void deleteCourse(Scanner scanner) {
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

}
