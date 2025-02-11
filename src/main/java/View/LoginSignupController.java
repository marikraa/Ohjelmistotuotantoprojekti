package View;

import Controller.Controller;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.Objects;

import javafx.scene.image.Image;


public class LoginSignupController {
    //create controller for CRUD operations

    Controller controller = Controller.getInstance();

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


    //try to login with given credentials and get user data if successful
    @FXML
    public void login(MouseEvent mouseEvent) {
        User user = controller.login(usernameField.getText(), passwordField.getText());
        if (user == null) {
            ErrorPopup.showError("Login failed", "Username or password is incorrect");
        }
        //if login succesfull set user to session manager and switch to main screen
        else {
            SessionManager.setCurrentUser(user);
            SceneManager.switchScene("MainScreen.fxml");
        }


    }


    @FXML
    public void signup(MouseEvent mouseEvent) {
        //check if fields are empty
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
            ErrorPopup.showError("Empty fields", "Please fill all fields");
        }
        //check if passwords match
        else if (!passwordField.getText().equals(passwordField2.getText())) {
            ErrorPopup.showError("Passwords do not match", "Please check passwords");
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
            User currentUser = controller.signup(usernameField.getText(), passwordField.getText(), profPic);
            if(currentUser == null){
                ErrorPopup.showError("Username already exists", usernameField.getText() + " is already taken");
            return;
            }

            //if signup succesfull set user to session manager and switch to main screen
            SessionManager.setCurrentUser(currentUser);
            SceneManager.switchScene("MainScreen.fxml");


        }


    }

    @FXML
    public void addProfilePicture(MouseEvent mouseEvent) {
        ImageAdder imageAdder = new ImageAdder();
        profilePic.setImage(imageAdder.addPicture(mouseEvent));

    }

}
