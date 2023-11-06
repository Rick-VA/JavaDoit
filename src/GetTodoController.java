import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class GetTodoController {
    public ArrayList<String> todo = new ArrayList<String>();

    public GetTodoController() {


        String sql = "select * from todos";
        String url = "jdbc:postgresql://localhost:5432/javadoit";
        String username = "postgres";
        String password = "password";

        try (
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet result = preparedStatement.executeQuery();

            ) {
            while (result.next()) {

                if (Objects.equals(result.getString("done"), "t")) {
                    todo.add("✔️ " + result.getString("todo"));
                } else {
                    todo.add("❌ " + result.getString("todo"));
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> toDo()  {
        return todo;
    }
}
