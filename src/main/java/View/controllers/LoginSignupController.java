package View.controllers;

import Model.Note;
import Model.User;
import View.*;
import View.managers.SceneManager;
import View.managers.SessionManager;
import View.utilies.PopupWindow;
import View.utilies.ImageAdder;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.stage.Stage;

//login and signup controller is used to handle login and signup operations both fxml uses this controller
public class LoginSignupController implements UiInterface {
    private IControllerForGUI controller;
    Stage stage;


    // IControllerForGUI controller = Controller.getInstance();

    //create json parser

    File selectedFile;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField passwordField2;
    @FXML
    ImageView profilePic;
    //set backend controller
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage=stage;

    }


    //try to login with given credentials and get user data if successful

    @FXML
    public void login(MouseEvent mouseEvent) {
        User user = controller.login(usernameField.getText(), passwordField.getText());

        if (user == null) {
            PopupWindow.showError("Login failed", "Username or password is incorrect");
        }
        //if login successful set user to session manager and switch to main screen
        else {
            openMainScreen(user);
        }
    }

    @FXML
    public void signup(MouseEvent mouseEvent) {


        //check if fields are empty
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
            PopupWindow.showError("Empty fields", "Please fill all fields");
        }
        //check if passwords match
        else if (!passwordField.getText().equals(passwordField2.getText())) {
            PopupWindow.showError("Passwords do not match", "Please check passwords");
        }

        //if fields are not empty and passwords match, try to signup
        else {
            Image profPic;
            //if user has not selected a profile picture, set default
            if (selectedFile == null) {
                profPic = new Image(Objects.requireNonNull(getClass().getResource("/images/defaultProfilePic.png")).toExternalForm());

            } else {
                profPic = new Image(selectedFile.toURI().toString());
            }
            User user = controller.signup(usernameField.getText(), passwordField.getText(), profPic);
            if (user == null) {
                PopupWindow.showError("Username already exists", usernameField.getText() + " is already taken");
                return;
            }

            //if signup succesfull set user to session manager and switch to main screen
            openMainScreen(user);


        }


    }

    @FXML
    public void addProfilePicture(MouseEvent mouseEvent) {
        ImageAdder imageAdder = new ImageAdder();
        profilePic.setImage(imageAdder.addPicture(mouseEvent));

    }

    public void openMainScreen(User currentUser) {
        SessionManager.setCurrentUser(currentUser);
        SceneManager.switchScene("MainScreen.fxml");
    }

    @FXML
    public void openStartScreen() {
        SceneManager.switchScene("StartScreen.fxml");
    }

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }
}
