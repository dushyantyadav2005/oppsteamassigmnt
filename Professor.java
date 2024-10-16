import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Professor extends User {
    private int professorId;

    public Professor(String email, String password) {
        super(email, password);
        if(super.login()) {
            this.professorId = fetchProfessorId(email); // Fetch professor ID based on email
        }else{
            System.out.println("No user exist");
        }
    }

    private int fetchProfessorId(String email) {
        int id = -1;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT professor_id FROM professors WHERE user_id = (SELECT user_id FROM users WHERE email = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("professor_id");
            }else{
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void viewAssignedCourses() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT courses FROM professors WHERE professor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, professorId);
            ResultSet resultSet = statement.executeQuery();
            String courses="";
            if (resultSet.next()) {
                 courses = resultSet.getString("courses");
            }else{
                System.out.println("No courses found");
                return;
            }
            String[] course = courses.split(",");

            // Print the result
            for (String c : course) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Courseschange()
    {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT courses FROM professors WHERE professor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, professorId);
            ResultSet resultSet = statement.executeQuery();
            String courses="";
            if (resultSet.next()) {
                courses = resultSet.getString("courses");
            }else{
                System.out.println("No courses found");
                return;
            }
            String[] course = courses.split(",");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter course ID: ");
            String p=scanner.nextLine();

            // Print the result
            for (String c : course) {
                if(p.equalsIgnoreCase(c))
                {
                    System.out.println("\nChanges you want:");
                    System.out.println("1. course_name : ");
                    System.out.println("2. credits : ");
                    System.out.println("3. return : ");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            String updateSQL = "UPDATE courses SET course_name = ? WHERE course_code = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                            System.out.println("Enter new course name: : ");
                            String newname=scanner.nextLine();
                            preparedStatement.setString(1, newname);
                            preparedStatement.setString(2, p);
                            int affectedRows = preparedStatement.executeUpdate(); // Call without the query

                            if (affectedRows > 0) {
                                System.out.println("name updated successfully");
                            } else {
                                System.out.println("name update failed");
                            }
                            break;
                        case 2:
                            String updatSQL = "UPDATE courses SET credits = ? WHERE course_code = ?";
                            PreparedStatement preparedSatement = connection.prepareStatement(updatSQL);
                            System.out.println("Enter new credits : ");
                            int newcredits=scanner.nextInt();
                            preparedSatement.setInt(1, newcredits);
                            preparedSatement.setString(2, p);
                            int affectedRow = preparedSatement.executeUpdate(); // Call without the query

                            if (affectedRow > 0) {
                                System.out.println("credits updated successfully");
                            } else {
                                System.out.println("credits update failed");
                            }
                            break;
                        case 3:
                            System.out.println("Logging out...");
                            return; // Exit the professor menu
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewStudentsInCourse(String courseCode) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT s.student_id, u.email, s.contact_details " +
                    "FROM course_registration cr " +
                    "JOIN students s ON cr.student_id = s.student_id " +
                    "JOIN users u ON s.user_id = u.user_id " +
                    "WHERE cr.course_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Students in Course: " + courseCode);
            while (resultSet.next()) {
                System.out.println("Student ID: " + resultSet.getInt("student_id") +
                        ", Email: " + resultSet.getString("email") +
                        ", Contact: " + resultSet.getString("contact_details"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
