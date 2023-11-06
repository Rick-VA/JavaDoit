import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTodoController {
    public AddTodoController(String newTodo) {
        String sql = "INSERT INTO todos (todo) VALUES (?)";
        String url = "jdbc:postgresql://localhost:5432/javadoit";
        String username = "postgres";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Create a PreparedStatement with the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the value for the first parameter (the todo) using newTodo
            preparedStatement.setString(1, newTodo);

            // Execute the query to insert the newTodo value into the database
            preparedStatement.executeUpdate();

            // Close the prepared statement and the database connection
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}