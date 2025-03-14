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
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Read messages from server in a separate thread
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine())!=null) {
                        System.out.println(serverMessage);
                        if(serverMessage.equals("Disconnected")){
                            exit(0);

                        }


                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }).start();

            // Send messages to server

            String userInput;
            while ((userInput = consoleInput.readLine())!=null) {

                out.println(userInput);

            }


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}