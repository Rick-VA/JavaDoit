import javax.swing.*;
import java.awt.*;

public class TodoItem extends JPanel {
    private String doneItem;

    public TodoItem(String todoItem) {
        setBackground(Color.yellow);
        setBorder(BorderFactory.createLineBorder(Color.black));

        add(new Checkbox());

        JLabel label = new JLabel(todoItem);
        add(label);

        String[]  todoItems = todoItem.split(" | ");

        if (todoItems[0].equals("✔️")) {
            doneItem = "✔️";
        } else {
            doneItem = "❌";
        }


        String todoItem1 = todoItems[2];

        JButton editButton = new JButton("Edit");
        add(editButton);

        editButton.addActionListener(e -> {
            new EditTodoController(todoItem1, label, doneItem);
        });

        setVisible(true);
    }

}
