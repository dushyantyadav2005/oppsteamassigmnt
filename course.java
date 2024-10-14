import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
    public static boolean courseExists(String courseCode) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM courses WHERE course_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if the course exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
