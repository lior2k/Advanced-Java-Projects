

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket server_socket;
    private final Vector<handle_client> clients;
    private final ExecutorService pool;
    private Vector<String> onlineUsers;

    public Server() {
        try {
            server_socket = new ServerSocket(5000);
            onlineUsers = new Vector<>();
        } catch(IOException e) {
            e.printStackTrace();
        }
        clients = new Vector<>();
        pool = Executors.newCachedThreadPool();
        System.out.println("----- Server is running! -----");
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket client = server_socket.accept();
                handle_client hc = new handle_client(client);
                clients.add(hc);
                pool.execute(hc);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for(handle_client hc : clients) {
            if (hc != null) {
                hc.sendMessage(message);
            }
        }
    }

    public class handle_client implements Runnable {
        private final Socket client_socket;
        private BufferedReader in;
        private PrintWriter out;

        public handle_client(Socket client) {
            this.client_socket = client;
        }

        public void update_online() {
            StringBuilder st = new StringBuilder("!update_online!,");
            for (String name: onlineUsers) {
                st.append(name).append(",");
            }
            System.out.println("debug: sending online users: " + st);
            broadcast(st.toString());
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client_socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
                String client_username = in.readLine();
                System.out.println(client_username + " connected!");
                broadcast(client_username + " joined the chat!");
                onlineUsers.add(client_username);
                update_online();
                String message;
                while((message = in.readLine()) != null) {
                    System.out.println("debug: recieved " + message);
                    if (message.startsWith("!quit")) {
                        broadcast(client_username + " left the chat!");
                        onlineUsers.remove(client_username);
                        update_online();
                        clients.remove(this);
                        break;
                    } else {
                        broadcast(client_username + ": " + message);
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}