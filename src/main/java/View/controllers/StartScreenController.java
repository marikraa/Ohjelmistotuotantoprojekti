package View.controllers;
import Model.Note;
import View.IControllerForGUI;
import View.managers.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StartScreenController implements UiInterface {

    IControllerForGUI controller;
    Stage stage;

    //set backend controller
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;

    }

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    //these methods switch the scene to the login or signup screen

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        //switch to login screen
        SceneManager.switchScene("LoginScreen.fxml");




    }

    @FXML
    public void handleSignup(ActionEvent actionEvent) {
        //switch to signup screen
        SceneManager.switchScene("SignupScreen.fxml");

    }

}
