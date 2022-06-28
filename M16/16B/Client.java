

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {

    private final DatagramSocket client_socket;
    private byte[] outputBuffer;
    private final byte[] inputBuffer;
    private final String serverAddress;
    public int responses;

    public Client(String serverAddress) throws SocketException {
        client_socket = new DatagramSocket();
        client_socket.setSoTimeout(10000);
        outputBuffer = new byte[1024];
        inputBuffer = new byte[1024];
        this.serverAddress = serverAddress;
        responses = 0;
    }

    synchronized public static StringBuilder data(byte[] a) {
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

    public void runConnectionCheck() {
        Client.OutputThread outputThread = new Client.OutputThread();
        Thread thread = new Thread(outputThread);
        thread.start();

        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(inputBuffer, inputBuffer.length);
                client_socket.receive(packet);
                if (data(inputBuffer) != null) {
                    responses++;
                    System.out.println("Server acknowledge message [packet number: " + data(inputBuffer) + ", from: " + packet.getAddress() + "]");
                }
                if (data(inputBuffer).toString().equals("10")) {
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Program terminated due to SocketTimeoutException!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class OutputThread implements Runnable {

        @Override
        public void run() {
            DatagramPacket packet;
            try {
                for (int i = 1; i <= 10; i++) {
                    String st = String.valueOf(i);
                    outputBuffer = st.getBytes(StandardCharsets.UTF_8);
                    packet = new DatagramPacket(outputBuffer, outputBuffer.length, InetAddress.getByName(serverAddress), 8888);
                    client_socket.send(packet);
                }
            } catch (IOException e) {
                System.out.println("Exception occurred during output thread loop");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Bad usage, server address");
            System.exit(0);
        }

        Client client = new Client(args[0]);
        client.runConnectionCheck();

        System.out.println("Total acknowledgements received: " + client.responses);
    }

}
