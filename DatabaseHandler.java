import java.sql.*;

// Save the message to the database
//DatabaseHandler.saveMessage(username, clientMessage); in calss ClientHandler

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/chat";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void saveMessage(String username, String message) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO chat_history (username, message) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, message);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}