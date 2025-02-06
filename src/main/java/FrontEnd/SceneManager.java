package FrontEnd;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public  class SceneManager {
    public static Stage primaryStage;

    //set progrmas main stage at the start
    public static void setStage(Stage stage){
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/GUI/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
