import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received: " + message);

                // Check if the client said "hii"
                if (message.equalsIgnoreCase("hii")) {
                    sendMessage("hii, how can I help you?");
                } 
                else if(message.equalsIgnoreCase("how are you")){
                    sendMessage("Fine,what about you");
                }
                else if(message.equalsIgnoreCase("what's up")){
                    sendMessage("Nothing");
                }
                else if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;  // Exit loop and close connection
                }
                else {
                    ChatServer.broadcast(message, this); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                ChatServer.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
