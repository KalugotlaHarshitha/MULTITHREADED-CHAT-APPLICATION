import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Chat Server started...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String clientMessage;
            while (true) {
                clientMessage = reader.readLine(); // Read message from client
                if (clientMessage == null || clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected. Closing server...");
                    break;
                }

                System.out.println("Received from client: " + clientMessage);

                // Custom response logic
                String serverResponse;
                if(clientMessage.equalsIgnoreCase("hello")){
                    serverResponse="Hii, how can I help you";
                }
                else if (clientMessage.equalsIgnoreCase("how are you")) {
                    serverResponse = "Fine";
                } 
                else if(clientMessage.equalsIgnoreCase("what's up")){
                    serverResponse="Nothing,how about you";
                }
                else {
                    serverResponse = "Server: " + clientMessage; // Echo back
                }

                writer.println(serverResponse); // Send message back to client
                System.out.println("Sent to client: " + serverResponse);
            }

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
