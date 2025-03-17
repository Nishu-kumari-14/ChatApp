import javax.swing.*;
import java.io.*;
import static java.lang.System.exit;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 11111;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Create and display the GUI
            ChatClientGUI gui = new ChatClientGUI(out);

            // Read messages from server in a separate thread
            Thread thread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine())!=null) {

                        if( (serverMessage.equals("clear"))){
                              gui.clearChat();
                        }else{
                            gui.displayMessage(serverMessage);
                        }
                        if(serverMessage.equals("Disconnected")){
                             exit(0);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading from server: " + ex.getMessage());
                }
            });
            thread.start();



            try{
                thread.join();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + ex.getMessage());
        }
    }
}