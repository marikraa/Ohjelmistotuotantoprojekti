package view.controllers;

import model.Note;
import model.User;
import view.*;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.PopupWindow;
import view.utilies.ImageAdder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class EditUserController implements UiInterface {
    public ChoiceBox languageSelector;
    Locale locale = SessionManager.getLocale();
    ResourceBundle rb = ResourceBundle.getBundle("language", locale);

    public Label editUserLabel;
    public TextField editUsernameField;
    public PasswordField oldPasswordField;
    public PasswordField newPasswordField;
    public Button deleteUserButton;
    public Button editUserButton;
    public Label newPasswordLabel;
    public Label oldPasswordLabel;
    public Label userNameLabel;
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
        newPasswordLabel.setText(rb.getString("editNewPassword"));
        oldPasswordLabel.setText(rb.getString("editOldPassword"));
        userNameLabel.setText(rb.getString("username"));
        deleteUserButton.setText(rb.getString("deleteButton"));
        editUserButton.setText(rb.getString("editButton"));
        editUserLabel.setText(rb.getString("editUser"));
        editUsernameField.setText(SessionManager.getCurrentUser().getUsername());
        profilePic.setImage(new Image(user.getProfilePictureUrl()));
        newPasswordField.setText(SessionManager.getCurrentUser().getPassword());
        oldPasswordField.setText(SessionManager.getCurrentUser().getPassword());
        List<String> languages = SessionManager.getLanguages();//add languages to selector
        for(String language: languages){
            languageSelector.getItems().add(language);
        }
        languageSelector.setValue(SessionManager.getLanguageString());//get language Abbreviation
        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           user.setLanguageCode(newValue.toString());//set user language to selected
        });








    }


    // Delete user method
    public void deleteUser(MouseEvent mouseEvent) {
        //ask for confirmation before deleting user
        String deleteAccountMessage = rb.getString("deleteAccountMessage");
        String deleteAccountTitle = rb.getString("deleteAccountTitle");
        if (PopupWindow.askForConfirmation(deleteAccountTitle, deleteAccountMessage)) {   //if user confirms, delete user
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
        Image newProfilePicture = new Image(user.getProfilePictureUrl());
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

        //TODO: Käyttäjän laittama language code
        String languageCode = user.getLanguageCode();
        Boolean isUpdated = controller.updateUser(oldUsername, newUsername, newPassword, newProfilePicture, languageCode);


        if (isUpdated) {
            //if database is updated, update user object
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            user.setProfilePictureUrl(newProfilePicture.getUrl());
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
