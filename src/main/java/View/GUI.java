package View;
import Controller.Controller;
import View.controllers.StartScreenController;
import View.managers.SceneManager;
import javafx.application.Application;


import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.IOException;

//this sets the stage and switches the scenes
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
