import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTodoController {
    public EditTodoController(String todoItem, DefaultListModel<String> listModel, JList<String> todoList) {
        JFrame editFrame = new JFrame("Edit todo");
        editFrame.setSize(300, 150);

        JPanel editPanel = new JPanel();
        JTextField editField = new JTextField(todoItem, 20);
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editedTodo = editField.getText();

                listModel.setElementAt(editedTodo, todoList.getSelectedIndex());

                editFrame.dispose();
            }
        });

        editPanel.add(editField);
        editPanel.add(saveButton);
        editFrame.add(editPanel);

        editFrame.setVisible(true);
    }
}
