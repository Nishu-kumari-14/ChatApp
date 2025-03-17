import javax.swing.*;
import java.sql.*;

// Save the message to the database
//DatabaseHandler.saveMessage(username, clientMessage); in calss ClientHandler

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/chat";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    static int flag;

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
   public static void fetchData(String username,String password) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM chat_history WHERE username = ? OR message = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            flag = 0;

            while(resultSet.next()){
                    if(resultSet.getString("username").equals(username)) {
                        System.out.println("after 1st if");
                        if(resultSet.getString("message").equals(password)){
                            flag = 1;
                        }else{
                            flag = 2;
                        }
                        break;

                    }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error while checking DataBase" + e.getMessage());

        }
    }
}