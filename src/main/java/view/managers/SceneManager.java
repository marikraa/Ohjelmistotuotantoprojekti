package view.managers;

import controller.Controller;
import model.Note;
import view.IControllerForGUI;
import view.controllers.UiInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;



import java.io.IOException;


//this class is used to switch scenes and open modal windows
public class SceneManager {
    private static Stage primaryStage;
    private static final IControllerForGUI controller = new Controller();
    private static final Logger LOGGER = Logger.getLogger(SceneManager.class.getName());
    private SceneManager(){

    }

    //set programs main stage at the start
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }


    //this is used to switch main screens
    public static void switchScene(String fxmlFile) {
        UiInterface primaryUiController;
        try {

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/GUI/" + fxmlFile));
            Parent root = loader.load();
            primaryUiController = loader.getController();
            //set THE backend controller for the UI controller
            primaryUiController.setController(controller);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryUiController.setStage(primaryStage);
            primaryStage.show();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    //this is used to open a modal window
    public static void openModal(String fxmlFile, Note note) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/GUI/" + fxmlFile));
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = loader.load();
            UiInterface modalUiController = loader.getController();
            Scene scene = new Scene(root);
            modalUiController.setController(controller);
            modalStage.setScene(scene);
            modalUiController.setStage(modalStage);
            if (note != null) {
                modalUiController.setNoteToEdit(note);
            }
            modalStage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }


    }
}
