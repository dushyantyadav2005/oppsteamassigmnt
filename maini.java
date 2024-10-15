import java.sql.*;

public class maini {
    private static final String url="jdbc:mysql://localhost:3306/mydb";
    private static final String username="root";
    private static final String password="Dus@2005";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "DELETE from user WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setInt(1, 3);
//            ResultSet resultSet = preparedStatement.executeQuery();
//             if(resultSet.next()) {
//                 {
//                     System.out.println(resultSet.getInt("marks"));
//                 }
// }
            int affectedRows = preparedStatement.executeUpdate(); // Call without the query

            if (affectedRows > 0) {
                System.out.println("DATA insertion successful");
            } else {
                System.out.println("DATA insertion failed");
            }

            // Close resources
//            preparedStatement.close();
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
