import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Professor extends User {
    private int professorId;

    public Professor(String email, String password) {
        super(email, password);
        this.professorId = fetchProfessorId(email); // Fetch professor ID based on email
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void viewAssignedCourses() {
        // Logic to view courses assigned to the professor
    }

    public void viewStudentsInCourse(String courseCode) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT s.student_id, u.email " +
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
                        ", Email: " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
