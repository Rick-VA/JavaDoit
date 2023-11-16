import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EditTodoController {
    private String updatedText;
    public JCheckBox doneCheckBox;
    private String doneItem;

    public EditTodoController(String todoItem,TodoItem todo) {

        String[] todoItem1 = todoItem.split(" | ");;

        String todoItem2 = todoItem1[2];

        doneItem = todoItem1[0];

        JFrame editFrame = new JFrame("Edit todo");
        editFrame.setSize(300, 150);

        JPanel editPanel = new JPanel();
        JTextField editField = new JTextField(todoItem2, 20);
        JButton saveButton = new JButton("Save");
        doneCheckBox = new JCheckBox("Done");

        if (doneItem.equals("✔️")) {
            doneCheckBox.setSelected(true);
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doneCheckBox.isSelected()) {
                    updatedText = "✔️ | " + editField.getText();
                } else {
                    updatedText = "❌ | " + editField.getText();
                }

                todo.setLabel(updatedText);
                String id = todo.getCheckbox();
                new DatabaseController().editTodo(todoItem2, id, doneCheckBox, editField.getText());
                editFrame.dispose();
            }
        });

        editPanel.add(editField);
        editPanel.add(saveButton);
        editPanel.add(doneCheckBox);
        editFrame.add(editPanel);

        editFrame.setVisible(true);
    }
}
