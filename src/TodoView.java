import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TodoView extends JFrame {
    private JList<String> todoList;
    private DefaultListModel<String> listModel;
    private JTextField todoInputField;

    public TodoView() {
        super("todo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);

        listModel = new DefaultListModel<>();

        GetTodoController database = new GetTodoController();


        for (String items: database.todo) {
            listModel.addElement(items);
        }

        todoList = new JList<>(listModel);

        todoList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = todoList.locationToIndex(e.getPoint());
                    if (index >=0) {
                        String selectedItem = listModel.getElementAt(index);

                        int pipeIndex = selectedItem.indexOf("|");
                        String doneItem = selectedItem.substring(0, pipeIndex -1).trim();
                        selectedItem =  selectedItem.substring(pipeIndex + 1).trim();

                        new EditTodoController(selectedItem, listModel, todoList, doneItem);
                    }
                }
            }
        });

        todoInputField = new JTextField(20);

        JButton addButton = new JButton("Add todo");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTodo();
            }
        });

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(todoList);
        add(scrollPane, BorderLayout.CENTER);
        add(todoInputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void addTodo() {
        String newTodo = todoInputField.getText();
        if (!newTodo.isEmpty()) {
            listModel.addElement("❌ | " + newTodo);

            new AddTodoController(newTodo);

            todoInputField.setText("");
        }
    }
}
