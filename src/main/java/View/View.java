package View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;


public class View extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(View.class.getResource("UI.fxml"));
        // JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
        Scene scene = new Scene(loader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
