import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
    protected String email;
    protected String password;
    protected int userId; // Add user ID for tracking

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Login method
    public boolean login(){
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT user_id FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.email);
            statement.setString(2, this.password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.userId = resultSet.getInt("user_id");
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Fetch user details from the database
    public void fetchUserDetails() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Assuming other user details are available
                System.out.println("User ID: " + resultSet.getInt("user_id"));
                System.out.println("Email: " + resultSet.getString("email"));
                // Add any other user details you want to display
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Change user password
    public boolean changePassword(String newPassword) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, this.email);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getters for email and password (if needed)
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
