package View;

import Controller.Controller;
import Model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class EditUserController {
    IControllerForGUI controller = Controller.getInstance();

    public TextField editUsernameField;

    public TextField editPasswordField;
    public ImageView profilePic;

    Stage editUserStage;
    private ImageAdder imageAdder = new ImageAdder();
    private Image selectedImage;
    private User user = SessionManager.getCurrentUser();
    //User edit window
    public void initialize(){
        System.out.println("Edit user");
        editUsernameField.setText(SessionManager.getCurrentUser().getUsername());
        profilePic.setImage(SessionManager.getCurrentUser().getProfilePicture());

    }
    // Stage setter
    public void setEditUserStage(Stage stage) {
        this.editUserStage = stage;
    }
    public void deleteUser(MouseEvent mouseEvent) {
        Boolean isDeleted = controller.deleteUser(user);
        if (isDeleted) {
            System.out.println("User deleted");
            editUserStage.close();
            SessionManager.clearUser();
            SceneManager.switchScene("StartScreen.fxml");
        }
    }

    public void updateUser(MouseEvent actionEvent) {
        String newUsername = editUsernameField.getText();
        String newPassword = editPasswordField.getText();
        Image newProfilePicture = user.getProfilePicture();
        if(selectedImage != null){
            newProfilePicture = selectedImage;
        }
        Boolean isUpdated = controller.updateUser(newUsername, newPassword, newProfilePicture);
        if (isUpdated) {
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            user.setProfilePicture(newProfilePicture);
            System.out.println("User updated");
            editUserStage.close();

            //refresh main screen
            SceneManager.switchScene("MainScreen.fxml");
        }


    }

    @FXML
    public void addProfilePicture(MouseEvent mouseEvent) {
        selectedImage = imageAdder.addPicture(mouseEvent);
        //set user profile picture to the image that user has selected
        if (selectedImage != null) {
            profilePic.setImage(selectedImage);

        }
    }


}
