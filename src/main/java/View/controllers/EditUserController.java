package View.controllers;

import Model.Note;
import Model.User;
import View.*;
import View.managers.SceneManager;
import View.managers.SessionManager;
import View.utilies.PopupWindow;
import View.utilies.ImageAdder;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class EditUserController implements UiInterface {
    public TextField editUsernameField;

    public PasswordField oldPasswordField;
    public PasswordField newPasswordField;
    private IControllerForGUI controller;


    public ImageView profilePic;

    Stage editUserStage;
    private ImageAdder imageAdder = new ImageAdder();
    private Image selectedImage;
    private User user = SessionManager.getCurrentUser();

    //set backend controller
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    public void setStage(Stage stage) {
        this.editUserStage = stage;
    }

    //User edit window
    public void initialize() {
        System.out.println("Edit user");
        editUsernameField.setText(SessionManager.getCurrentUser().getUsername());
        profilePic.setImage(SessionManager.getCurrentUser().getProfilePicture());
        newPasswordField.setText(SessionManager.getCurrentUser().getPassword());
        oldPasswordField.setText(SessionManager.getCurrentUser().getPassword());

    }


    // Delete user method
    public void deleteUser(MouseEvent mouseEvent) {
        //ask for confirmation before deleting user
        if (PopupWindow.askForConfirmation("Delete user", "Are you sure you want to delete your account?")) {   //if user confirms, delete user
            Boolean isDeleted = controller.deleteUser(user);
            //check if user is deleted from database
            if (isDeleted) {
                System.out.println("User deleted");
                editUserStage.close();
                SessionManager.clearUser();
                SceneManager.switchScene("StartScreen.fxml");
            }

        }
    }


    // Update user method
    public void updateUser() {

        System.out.println("Update user");
        //get user input
        String oldUsername = user.getUsername();
        String newUsername = editUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String oldPassword = oldPasswordField.getText();
        Image newProfilePicture = user.getProfilePicture();
        if (selectedImage != null) {
            newProfilePicture = selectedImage;
        }

        //if old password is correct, update password
        if (oldPassword.equals(user.getPassword())) {
            System.out.println("Old password is correct");
            //if new password is same as old password, keep old password
            if (oldPassword.equals(newPassword)) {
                System.out.println("New password is same as old password");
                newPassword = oldPassword;
            }

        } else {
            PopupWindow.showError("Wrong password", "Old password is incorrect");
            return;
        }


        Boolean isUpdated = controller.updateUser(oldUsername, newUsername, newPassword, newProfilePicture);


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


    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }
}
