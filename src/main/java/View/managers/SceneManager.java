package View.managers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;

public class SceneManager {
    public static Stage primaryStage;
    @FXML
    Font normal;
    @FXML
    Font bold;

    //set progrmas main stage at the start
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/GUI/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            // Lasketaan keskipiste vaakasuunnassa
            double centerX = (screenBounds.getWidth() - scene.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - scene.getHeight()) / 2;
            Font normal = Font.loadFont(SceneManager.class.getResourceAsStream("/fonts/AmaticSC-Regular.ttf"),10);
            Font bold = Font.loadFont(SceneManager.class.getResourceAsStream("/fonts/AmaticSC-Bold.ttf"),10);

            primaryStage.setScene(scene);
            primaryStage.setY(centerY*0.6);
            primaryStage.setX(centerX*0.7);
            primaryStage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
