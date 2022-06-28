
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean gottaCloseTheThreadSomehow = false;

    @FXML
    private TextArea MessageBox;
    @FXML
    private Button SendMessageButton;
    @FXML
    public TextField LoginTextField;
    @FXML
    private Button LoginButton;
    @FXML
    private TextField SendMessageBox;
    @FXML
    private TextArea OnlineUsersBox;

    public ClientController(String server_address) {
        try {
            this.socket = new Socket(server_address, 5000);
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            InputThread inputThread = new InputThread();
            Thread thread = new Thread(inputThread);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLoginButtonClicked() {
        if (LoginTextField.getText() != null) {
            out.println(LoginTextField.getText());
            LoginButton.setDisable(true);
            SendMessageButton.setDisable(false);
        }
    }

    @FXML
    public void onSendMessageButtonClicked() {
        String outMessage = SendMessageBox.getText();
        if (outMessage != null) {
            out.println(outMessage);
        }
    }

    @FXML
    private void onLogoutButtonClicked() {
        try {
            out.println("!quit");
            gottaCloseTheThreadSomehow = true;
            socket.close();
            System.exit(0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class InputThread implements Runnable {

        @Override
        public void run() {
            String inMessage;
            try {
                while (true) {
                    if (gottaCloseTheThreadSomehow) {
                        break;
                    }
                    inMessage = in.readLine();
                    if (inMessage != null) {
                        if (inMessage.contains("!update_online!,")) {
                            OnlineUsersBox.setText("");
                            String[] names = inMessage.split(",");
                            for (int i = 1; i < names.length; i++) {
                                OnlineUsersBox.setText(OnlineUsersBox.getText() + names[i] + "\n");
                            }

                        } else {
                            MessageBox.setText(MessageBox.getText() + "\n" + inMessage);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
