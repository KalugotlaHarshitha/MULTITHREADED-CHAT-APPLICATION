import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
   private static Set<ClientHandler> clients = new HashSet();

   public ChatServer() {
   }

   public static void main(String[] var0) {
      try {
         ServerSocket var1 = new ServerSocket(12345);

         try {
            System.out.println("Chat Server started...");

            while(true) {
               Socket var2 = var1.accept();
               System.out.println("New client connected!");
               ClientHandler var3 = new ClientHandler(var2);
               clients.add(var3);
               (new Thread(var3)).start();
            }
         } catch (Throwable var5) {
            try {
               var1.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public static void broadcast(String var0, ClientHandler var1) {
      Iterator var2 = clients.iterator();

      while(var2.hasNext()) {
         ClientHandler var3 = (ClientHandler)var2.next();
         if (var3 != var1) {
            var3.sendMessage(var0);
         }
      }

   }

   public static void removeClient(ClientHandler var0) {
      clients.remove(var0);
   }
}
