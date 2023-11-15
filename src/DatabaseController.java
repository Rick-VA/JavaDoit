import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DatabaseController {
    public ArrayList<String> todo = new ArrayList<String>();
    public ArrayList<Integer> id = new ArrayList<Integer>();

    public DatabaseController() {


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
                    todo.add("✔️ | " + result.getString("todo"));
                } else {
                    todo.add("❌ | " + result.getString("todo"));
                }

                id.add(result.getInt("id"));

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> toDo()  {
        return todo;
    }


    public void removeTodoItem(String todoText) {
        String sql = "DELETE FROM todos WHERE todo = ?";
        String url = "jdbc:postgresql://localhost:5432/javadoit";
        String username = "postgres";
        String password = "password";

        String[] todoItems = todoText.split(" | ");

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, todoItems[2]);

            preparedStatement.executeUpdate();
            System.out.println("Executing SQL query: " + preparedStatement);
            preparedStatement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        todo.remove(todoText);
    }

    public void editTodo(String todoText, String doneItem, JCheckBox checkBox, String editedTodo) {
        String sql = "UPDATE todos SET todo = ?, done = ? WHERE id = ?";
        String url = "jdbc:postgresql://localhost:5432/javadoit";
        String username = "postgres";
        String password = "password";

        System.out.println(doneItem);

        int id = Integer.parseInt(doneItem);


        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, editedTodo);

            if (checkBox.isSelected()) {
                preparedStatement.setBoolean(2, true);
            } else {
                preparedStatement.setBoolean(2, false);
            }
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        todo.remove(todoText);
        todo.add(doneItem + " | " + editedTodo);
    }
}
