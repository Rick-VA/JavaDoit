import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TodoView extends JFrame {
    public JPanel todoPanel;
    private JTextField todoInputField;
    private DatabaseController database;

    public TodoView() {
        super("todo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);

        todoPanel = new JPanel();
        todoPanel.setLayout(new BoxLayout(todoPanel, BoxLayout.Y_AXIS));

        database = new DatabaseController();


        for (String items: database.todo) {
            addTodoPanel(items, database.id.get(database.todo.indexOf(items)));
        }

        JScrollPane scrollPane = new JScrollPane(todoPanel);

        todoInputField = new JTextField(20);

        JButton addButton = new JButton("Add todo");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTodo();
            }
        });

        JButton deleteButton = new JButton("Delete selected todo");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItems();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(todoInputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    public void addTodoPanel(String todoItem, Integer id) {
        todoPanel.add(new TodoItem(todoItem, id));
        todoPanel.revalidate();
        todoPanel.repaint();
    }

    public void addTodo() {
        String newTodo = todoInputField.getText();
        if (!newTodo.isEmpty()) {
            addTodoPanel("❌ | " + newTodo, new AddTodoController(newTodo).id);
            todoInputField.setText("");
        }
    }

    private void deleteSelectedItems() {
        List<TodoItem> selectedItems = getSelectedItems();
        for (TodoItem selectedItem : selectedItems) {
            todoPanel.remove(selectedItem);
            database.removeTodoItem(selectedItem.getTodoText(), selectedItem.getCheckbox());
        }
        revalidateAndRepaint();
    }

    private List<TodoItem> getSelectedItems() {
        List<TodoItem> selectedItems = new ArrayList<>();
        for (Component component : todoPanel.getComponents()) {
            if (component instanceof TodoItem) {
                TodoItem todoItem = (TodoItem) component;
                if (todoItem.isSelected()) {
                    selectedItems.add(todoItem);
                    System.out.println(todoItem.getCheckbox());
                }
            }
        }
        return selectedItems;
    }

    private void revalidateAndRepaint() {
        todoPanel.revalidate();
        todoPanel.repaint();
    }
}
