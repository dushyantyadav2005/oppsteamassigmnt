import java.sql.*;
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
        System.out.println("3. return ");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        courseManager mn = new courseManager();
        switch (choice) {
            case 1:
                mn.addCourse(scanner);
                break;
            case 2:
                mn.updateCourse(scanner);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
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
        studentManager sn=new studentManager();
        switch (choice) {
            case 1:
                sn.addStudent(scanner);
                break;
            case 2:
                sn.updateStudent(scanner);
                break;
            case 3:
                sn.deleteStudent(scanner);
                break;
            case 4:
                System.out.println("Logging out...");
                return; // Exit the student menu
            default:
                System.out.println("Invalid choice.");
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
        System.out.println("3. filter compalin : ");
        System.out.println("4. return");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();// Consume newline

        switch (choice) {
            case 1:
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "SELECT complaint_id, student_id, complaint_text, complaint_date, status FROM complaints";
                        PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery();

                        System.out.println("All Complaints:");
                        while (resultSet.next()) {
                            System.out.println("Complaint ID: " + resultSet.getInt("complaint_id") +
                                    ", Student ID: " + resultSet.getInt("student_id") +
                                    ", Complaint: " + resultSet.getString("complaint_text") +
                                    ", Date: " + resultSet.getTimestamp("complaint_date") +
                                    ", Status: " + resultSet.getString("status"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                break;
            case 2:
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT complaint_id, student_id, complaint_text, complaint_date, status FROM complaints";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();

                    System.out.println("All Complaints:");
                    while (resultSet.next()) {
                        System.out.println("Complaint ID: " + resultSet.getInt("complaint_id") +
                                ", Student ID: " + resultSet.getInt("student_id") +
                                ", Complaint: " + resultSet.getString("complaint_text") +
                                ", Date: " + resultSet.getTimestamp("complaint_date") +
                                ", Status: " + resultSet.getString("status"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Get input from administrator to update complaint status
                System.out.print("Enter Complaint ID to resolve: ");
                int complaintId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                System.out.print("Enter new status (pending/resolved): ");
                String newStatus = scanner.nextLine();

                // Update complaint status
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, newStatus);
                    statement.setInt(2, complaintId);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Complaint status updated successfully.");
                    } else {
                        System.out.println("Failed to update complaint status. Complaint ID may not exist.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case 3:
                System.out.println("Filter complaints based on:");
                System.out.println("1. Status");
                System.out.println("2. Date");
                int filterChoice = scanner.nextInt();
                scanner.nextLine(); // consume the newline

                String filterQuery = "SELECT complaint_id, student_id, complaint_text, complaint_date, status FROM complaints";
                if (filterChoice == 1) {
                    filterQuery += " WHERE status = ?";
                } else if (filterChoice == 2) {
                    filterQuery += " WHERE complaint_date >= ?";
                }

                try (Connection connection = DatabaseConnection.getConnection()) {
                    PreparedStatement statement = connection.prepareStatement(filterQuery);

                    // Set parameters based on filter choice
                    if (filterChoice == 1) {
                        System.out.print("Enter status (pending/resolved): ");
                        String status = scanner.nextLine();
                        statement.setString(1, status);
                    } else if (filterChoice == 2) {
                        System.out.print("Enter date (yyyy-mm-dd): ");
                        String date = scanner.nextLine();
                        statement.setDate(1, Date.valueOf(LocalDate.parse(date)));
                    }

                    ResultSet resultSet = statement.executeQuery();
                    System.out.println("Filtered Complaints:");
                    while (resultSet.next()) {
                        System.out.println("Complaint ID: " + resultSet.getInt("complaint_id") +
                                ", Student ID: " + resultSet.getInt("student_id") +
                                ", Complaint: " + resultSet.getString("complaint_text") +
                                ", Date: " + resultSet.getTimestamp("complaint_date") +
                                ", Status: " + resultSet.getString("status"));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }

    }
}
