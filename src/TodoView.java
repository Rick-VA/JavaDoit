import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TodoView extends JFrame {
    public JPanel todoPanel;
    private JTextField todoInputField;

    public TodoView() {
        super("todo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);

        todoPanel = new JPanel();
        todoPanel.setLayout(new BoxLayout(todoPanel, BoxLayout.Y_AXIS));

        GetTodoController database = new GetTodoController();


        for (String items: database.todo) {
            addTodoPanel(items);
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

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(todoInputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    public void addTodoPanel(String todoItem) {
        todoPanel.add(new TodoItem(todoItem));
        todoPanel.revalidate();
        todoPanel.repaint();
    }

    public void editTodoPanel(String todoItem) {
        System.out.println(todoItem);
    }

    public void addTodo() {
        String newTodo = todoInputField.getText();
        if (!newTodo.isEmpty()) {
            addTodoPanel("❌ | " + newTodo);

            new AddTodoController(newTodo);

            todoInputField.setText("");
        }
    }
}
