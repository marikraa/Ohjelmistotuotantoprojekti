package View;

import Controller.Controller;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class EditUserController {

    public TextField oldPasswordField;
    public TextField editUsernameField;
    public TextField newPasswordField;
    IControllerForGUI controller = Controller.getInstance();


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


    // Delete user method
    public void deleteUser(MouseEvent mouseEvent) {
        Boolean isDeleted = controller.deleteUser(user);
        if (isDeleted) {
            System.out.println("User deleted");
            editUserStage.close();
            SessionManager.clearUser();
            SceneManager.switchScene("StartScreen.fxml");
        }
    }




    public void updateUser(MouseEvent mouseEvent) {
        System.out.println("Update user");
        String oldUsername = user.getUsername();
        String newUsername = editUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String oldPassword = oldPasswordField.getText();
        Image newProfilePicture = user.getProfilePicture();
        if(selectedImage != null){
            newProfilePicture = selectedImage;
        }

        //if old password is correct, update password
        if(oldPassword.equals(user.getPassword())){
            System.out.println("Old password is correct");
            //if new password is same as old password, keep old password
            if(oldPassword.equals(newPassword)){
                System.out.println("New password is same as old password");
                newPassword = oldPassword;
            }

        }
        else{
            ErrorPopup.showError( "Wrong password","Old password is incorrect");
            return;
        }


        Boolean isUpdated = controller.updateUser(oldUsername,newUsername, newPassword, newProfilePicture);


        if (isUpdated) {
            //if database is updated, update user object
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
