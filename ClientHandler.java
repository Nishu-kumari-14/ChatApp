import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

            // Ask for username
            out.println("Enter your username:");
            username = in.readLine();
            ChatServer.clientHandlers.add(this);
            ChatServer.broadcast(username + " joined the chat!", this);
            out.println("Now you can chat with your loved ones");
            String clientMessage;
            while (!socket.isClosed()) {
                clientMessage = in.readLine();

                // Save the message to the database
                DatabaseHandler.saveMessage(username, clientMessage);
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
}