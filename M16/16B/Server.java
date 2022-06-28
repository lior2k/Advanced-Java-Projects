

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

    private final DatagramSocket server_socket;
    private final byte[] buffer;

    public Server() throws SocketException {
        server_socket = new DatagramSocket(8888);
        buffer = new byte[1024];
    }

    public void runConnectionCheck()  {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                server_socket.receive(packet);
                if (data(buffer) != null) {
                    System.out.println("Debug: received " + data(buffer));
                    server_socket.send(packet);
                }
//            Thread.sleep(12000); enable this line if you want to check the 10 seconds timeout exception
            }
        } catch (IOException e) {
            System.out.println("Error occurred during runConnection loop");
        }
        
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server();
        server.runConnectionCheck();
    }
}
