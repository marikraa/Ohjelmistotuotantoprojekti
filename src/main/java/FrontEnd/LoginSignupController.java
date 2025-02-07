package FrontEnd;

import Controller.CRUDController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.io.File;
import javafx.scene.image.Image;


public class LoginSignupController {
    //create controller for CRUD operations

    CRUDController crudController = CRUDController.getInstance();
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
        //if everything is ok, set user data
        else {
            User user = new User(usernameField.getText(), passwordField.getText(), null);
            //if user has selected a profile picture, set it
            if (selectedFile != null) {
                user.setProfilePic(new Image(selectedFile.toURI().toString()));
            }

            //TODO:maybe try block here???
            if(crudController.signUp(user)){
                //if signup succesfull set user to session manager and switch to main screen
                SessionManager.setCurrentUser(user);
                SceneManager.switchScene("MainScreen.fxml");
            }

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
