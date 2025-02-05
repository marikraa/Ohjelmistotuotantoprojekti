package FrontEnd;
import javafx.application.Application;


import javafx.stage.Stage;
import java.io.IOException;


public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
     SceneManager.setStage(primaryStage);
        SceneManager.switchScene("StartScreen.fxml");


    }

    public static void main(String[] args) {
        launch(args);
    }
}
