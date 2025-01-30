package View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class View extends Application {




    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("UI.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Note wizard");

        primaryStage.setOnCloseRequest(evt -> {
            Platform.exit();
            System.exit(0);
        });



    }

    public static void main(String[] args) {
        launch(args);
    }
}
