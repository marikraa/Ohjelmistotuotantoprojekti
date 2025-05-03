package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.managers.SceneManager;

import java.io.IOException;

/**
 * The main GUI class for the application.
 * This class initializes the primary stage and manages scene switching.
 */
public class GUI extends Application {

    /**
     * Starts the JavaFX application by setting up the primary stage and loading the initial scene.
     *
     * @param primaryStage The primary stage for this application.
     * @throws IOException If there is an error loading the initial scene.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Set the application icon
        Image icon = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        primaryStage.getIcons().add(icon);

        // Set the stage and switch to the initial scene
        SceneManager.setStage(primaryStage);
        SceneManager.switchScene("StartScreen.fxml");

        // Handle application close request
        primaryStage.setOnCloseRequest(evt -> {
            Platform.exit();
            System.exit(0);
        });
    }
}