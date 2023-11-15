import javax.swing.*;
import java.awt.*;

public class TodoItem extends JPanel {
    private String doneItem;
    private JLabel label;
    private Checkbox checkbox;

    public TodoItem(String todoItem, int id) {
        setBackground(Color.yellow);
        setBorder(BorderFactory.createLineBorder(Color.black));

        checkbox = new Checkbox();

        checkbox.setName(String.valueOf(id));
        add(checkbox);

        label = new JLabel(todoItem);
        add(label);

        String[]  todoItems = todoItem.split(" | ");

        if (todoItems[0].equals("✔️")) {
            doneItem = "✔️";
        } else {
            doneItem = "❌";
        }

        JButton editButton = new JButton("Edit");
        add(editButton);

        editButton.addActionListener(e -> {
            new EditTodoController(getTodoText(),this);
        });

        setVisible(true);
    }

    public String getTodoText() {
        return label.getText();
    }

    public boolean isSelected() {
        return checkbox.getState();
    }
    public String getCheckbox() {
        return checkbox.getName();
    }
    public void setLabel(String todoItem) {
        label.setText(todoItem);
    }

}
