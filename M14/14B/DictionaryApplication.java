 

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DictionaryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DictionaryView.fxml"));
        DictionaryController dc = new DictionaryController();
        fxmlLoader.setController(dc);
        fxmlLoader.setLocation(getClass().getResource("DictionaryView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600, 400);
        stage.setTitle("Dictionary App");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}