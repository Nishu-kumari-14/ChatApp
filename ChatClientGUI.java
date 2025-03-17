import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ChatClientGUI extends JFrame {
    private JTextArea chatArea; // To display chat history
    private JTextField inputField; // For typing messages
    private JButton sendButton; // To send messages
    private PrintWriter out; // To send messages to the server

    public ChatClientGUI(PrintWriter out) {
        this.out = out;

        // Set up the JFrame
        setTitle("Chatroom");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat area (non-editable)
        chatArea = new JTextArea();
        chatArea.setEditable(false); // Prevent users from editing the chat history
        JScrollPane scrollPane = new JScrollPane(chatArea); // Add scroll bars
        add(scrollPane, BorderLayout.CENTER);

        // Input panel (for text field and send button)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        //set fonts
        inputField.setFont(new Font("Arial", Font.BOLD, 15));
        chatArea.setFont(new Font("Arial", Font.BOLD, 15));

        //Add Borders
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                new EmptyBorder(8, 10, 8, 10) // Padding inside the field
        ));
        inputField.setPreferredSize(new Dimension(0, 40));

        // Add action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Display the JFrame
        setVisible(true);
    }
    //clear chat area
    void clearChat(){
        chatArea.setText("");
    }

    // Method to handle sending messages
    private void sendMessage() {
        String message = inputField.getText().trim();
        chatArea.append("Me: "+message + "\n");
        if (!message.isEmpty()) {
            out.println(message); // Send the message to the server
            inputField.setText(""); // Clear the input field
        }

    }

    // Method to display incoming messages
    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }
}