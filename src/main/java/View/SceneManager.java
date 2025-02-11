package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;

public class SceneManager {
    public static Stage primaryStage;

    //set progrmas main stage at the start
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/GUI/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // Haetaan näytön koko
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            // Lasketaan keskipiste vaakasuunnassa
            double centerX = (screenBounds.getWidth() - scene.getWidth()) / 2;
            primaryStage.setScene(scene);
            primaryStage.setY(10);
            primaryStage.setX(centerX*0.7);
            primaryStage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
