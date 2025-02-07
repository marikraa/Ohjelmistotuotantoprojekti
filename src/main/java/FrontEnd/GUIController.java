package FrontEnd;

import Controller.CRUDController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import java.io.File;

import javafx.scene.image.Image;

public class GUIController {
    //create controller for CRUD operations
    CRUDController crudController = new CRUDController();
    //create json parser
    ObjectMapper objectMapper = new ObjectMapper();
    User user;
    File selectedFile;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField passwordField2;
    @FXML
    ImageView profilePic;
    @FXML
    TextField searchNote;


    @FXML
    public void sortNotes(ActionEvent actionEvent) {
        user.sortNotes(searchNote.getText());

    }

    @FXML
    public void logOut(MouseEvent mouseEvent) {
        if (crudController.logOut(usernameField.getText())) {
            user = null;
            SceneManager.switchScene("StartScreen.fxml");

        }
    }


    //try to login with given credentials and get user data if successful
    @FXML
    public void login(MouseEvent mouseEvent) {
        user= crudController.login(usernameField.getText(), passwordField.getText());
        if (user == null) {
            throw new Error("Wrong credentials");
        }


    }


    @FXML
    public void signup(MouseEvent mouseEvent) {
        //initialize user and add default profile pic
        user = new User(null, null, null);
        profilePic.setImage(user.getProfilePicture());
        //check if fields are empty
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
            throw new Error("Username or password is empty");
        }
        //check if passwords match
        else if (!passwordField.getText().equals(passwordField2.getText())) {
            throw new Error("Passwords do not match");
        }

        //if everything is ok, set user data
        else{
            //if user has selected a profile picture, set it
            if (selectedFile != null) {
                user.setProfilePic(new Image(selectedFile.toURI().toString()));
            }
            user.setUsername(usernameField.getText());
            user.setPassword(passwordField.getText());

            //TODO:maybe try block here???
            crudController.signUp(user);

        }



    }

    @FXML
    public void addPicture(MouseEvent mouseEvent) {
        System.out.println("add picture");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profilePic.setImage(image);
            user.setProfilePic(image);

        }


    }


    //these methods switch the scene to the login or signup screen
    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        SceneManager.switchScene("LoginScreen.fxml");

    }

    @FXML
    public void handleSignup(ActionEvent actionEvent) {
        SceneManager.switchScene("SignupScreen.fxml");
    }
}
