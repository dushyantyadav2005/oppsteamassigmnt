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
        courseManager mn = new courseManager();
        switch (choice) {
            case 1:
                mn.addCourse(scanner);
                break;
            case 2:
                mn.updateCourse(scanner);
                break;
            case 3:
                mn.deleteCourse(scanner);
                break;
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
