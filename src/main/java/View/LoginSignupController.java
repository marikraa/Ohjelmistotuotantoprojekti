package View;

import Controller.CRUDController;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.io.File;
import java.util.Objects;

import javafx.scene.image.Image;


public class LoginSignupController {
    //create controller for CRUD operations

    CRUDController crudController = new CRUDController();

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
       User user = crudController.login(usernameField.getText(), passwordField.getText());
        if (user == null) {
            throw new Error("Wrong credentials");
        }
        //if login succesfull set user to session manager and switch to main screen
        else{
            SessionManager.setCurrentUser(user);
            SceneManager.switchScene("MainScreen.fxml");
        }



    }


    @FXML
    public void signup(MouseEvent mouseEvent) {
        //check if fields are empty
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
            throw new Error("Username or password is empty");
        }
        //check if passwords match
        else if (!passwordField.getText().equals(passwordField2.getText())) {
            throw new Error("Passwords do not match");
        }


        else {
            Image profPic;
            //if user has not selected a profile picture, set default
            if(selectedFile==null){
                profPic = new Image(Objects.requireNonNull(getClass().getResource("/images/defaultProfilePic.png")).toExternalForm());

            }
            else{
                profPic = new Image(selectedFile.toURI().toString());
            }
            //User user = new User(usernameField.getText(), passwordField.getText(), null);
            User currentUser = crudController.signup(usernameField.getText(),passwordField.getText(),profPic);
            //if user has selected a profile picture, set it
            /*if (selectedFile != null) {
                user.setProfilePic(new Image(selectedFile.toURI().toString()));
            }*/

            //TODO:maybe try block here???

                //if signup succesfull set user to session manager and switch to main screen
                SessionManager.setCurrentUser(currentUser);
                SceneManager.switchScene("MainScreen.fxml");


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

        }


    }

}
