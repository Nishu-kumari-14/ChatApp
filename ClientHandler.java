import javax.swing.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private String password;
    static boolean clearChatArea = false;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            System.out.println("Inside run");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

            try{
                DatabaseHandler.establishConnection(); // Connection Establishment with the database
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error while checking DataBase" + e.getMessage());
            }

            System.out.println("Connection Establihsed");
            while (true) {
                out.println("Enter your username:");
                username = in.readLine();
                out.println("Enter password");
                password = in.readLine();
                DatabaseHandler.fetchData(username, password);
                if (DatabaseHandler.flag == 1) {
                    out.println("clear"); //to clear chat area
                    ChatServer.broadcast(username + " rejoined the chat!", this);
                    out.println("Now you can chat with your loved ones");

                    break;
                } else if (DatabaseHandler.flag == 2) {
                    JOptionPane.showMessageDialog(null, "user exists....try different combination");
                    out.println("clear"); //to clear chat area


                } else {
                    saveInformation(username, password);
                    out.println("clear"); //to clear chat area
                    ChatServer.broadcast(username + " joined the chat!", this);
                    out.println("Now you can chat with your loved ones");
                    break;
                }
            }
            System.out.println("Outside while");
            ChatServer.clientHandlers.add(this);
            String clientMessage;
            while (!socket.isClosed()) {
                clientMessage = in.readLine();
                // Check if the message is null (client disconnected)
                if (clientMessage == null) {
                    break; // Exit the loop if the client disconnects
                }


                if(clientMessage.equals("exit")){
                    out.println("Disconnected");
                    ChatServer.broadcast(username + " disconnected", this);
                    break;
                }
                // Broadcast the message to all clients
                ChatServer.broadcast(username + ": " + clientMessage, this);


            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
            System.out.println("Client name:" + username);
        } finally {
            ChatServer.removeClient(this);
            try {

                socket.close();
                System.out.println(username + " disconnected");

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }
    void saveInformation(String username,String password){
        //save user information to the database

        DatabaseHandler.saveMessage(username,password);
    }
}