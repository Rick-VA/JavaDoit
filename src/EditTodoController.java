import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EditTodoController {
    public EditTodoController(String todoItem, JLabel label, String doneItem) {
        JFrame editFrame = new JFrame("Edit todo");
        editFrame.setSize(300, 150);

        JPanel editPanel = new JPanel();
        JTextField editField = new JTextField(todoItem, 20);
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JCheckBox doneCheckBox = new JCheckBox("Done");
        if (doneItem.equals("✔️")) {
            doneCheckBox.setSelected(true);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editedTodo = editField.getText();


                String sql = "UPDATE todos SET todo = ?, done = ? WHERE todo = ?";
                String url = "jdbc:postgresql://localhost:5432/javadoit";
                String username = "postgres";
                String password = "password";

                try(Connection connection = DriverManager.getConnection(url, username, password)) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, editedTodo);

                    if (doneCheckBox.isSelected()) {
                        preparedStatement.setBoolean(2, true);
                    } else {
                        preparedStatement.setBoolean(2, false);
                    }
                    preparedStatement.setString(3, todoItem);

                    preparedStatement.executeUpdate();

                    preparedStatement.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }


                if (doneCheckBox.isSelected()) {
                    label.setText("✔️ | " + editedTodo);
                } else {
                    label.setText("❌ | " + editedTodo);
                }

                editFrame.dispose();
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "DELETE FROM todos WHERE todo = ?";
                String url = "jdbc:postgresql://localhost:5432/javadoit";
                String username = "postgres";
                String password = "password";

                try(Connection connection = DriverManager.getConnection(url, username, password)) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, todoItem);

                    preparedStatement.executeUpdate();

                    preparedStatement.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                label.remove(label);

                editFrame.dispose();
            }
        });

        editPanel.add(editField);
        editPanel.add(deleteButton);
        editPanel.add(doneCheckBox);
        editPanel.add(saveButton);
        editFrame.add(editPanel);

        editFrame.setVisible(true);
    }
}
