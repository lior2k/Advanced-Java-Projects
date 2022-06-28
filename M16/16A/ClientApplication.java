

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the server hostname(eg 127.0.0.1): ");
        String server_address = sc.nextLine();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientView.fxml"));
        ClientController client_controller = new ClientController(server_address);
        fxmlLoader.setController(client_controller);
        fxmlLoader.setLocation(getClass().getResource("ClientView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600, 400);
        stage.setTitle("Client App");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}