package View;
import View.managers.SceneManager;
import javafx.application.Application;


import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.IOException;


public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
     SceneManager.setStage(primaryStage);
        SceneManager.switchScene("StartScreen.fxml");
        primaryStage.setOnCloseRequest(evt -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
