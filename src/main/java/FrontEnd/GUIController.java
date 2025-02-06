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
        if(crudController.logOut(usernameField.getText())){
            user = null;
            SceneManager.switchScene("StartScreen.fxml");

        }
    }


    //try to login with given credentials and get user data if successful
    @FXML
    public void login(MouseEvent mouseEvent) {
        try {
            String jsonData = crudController.login(usernameField.getText(), passwordField.getText());
            if (jsonData == null) {
                throw new Error("Wrong credentials");
            }
            user = objectMapper.readValue(jsonData, User.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void signup(MouseEvent mouseEvent) {

        //check if fields are empty
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()){
            throw new Error("Username or password is empty");
        }
        //check if passwords match
        if(!passwordField.getText().equals(passwordField2.getText())){
            throw new Error("Passwords do not match");
        }
        //sign up with given credentials
        Image profileImage = (selectedFile != null) ? new Image(selectedFile.toURI().toString()) : null;
        crudController.signUp(usernameField.getText(), passwordField.getText(), profileImage);

    }
    @FXML
    public void addPicture(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            Image image = new Image (selectedFile.toURI().toString());
            user.setProfilePicture(image);
            profilePic.setImage(image);
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
