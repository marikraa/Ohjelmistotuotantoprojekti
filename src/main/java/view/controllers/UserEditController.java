package view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Note;
import model.User;
import view.IControllerForView;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.ImageAdder;
import view.utilies.PopupWindow;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is controller for user editing screen
 */

public class UserEditController implements UiInterface {
    private final User user = SessionManager.getCurrentUser();
    private final ImageAdder imageAdder = new ImageAdder();
    private  ResourceBundle rb;
    private Stage editUserStage;
    private IControllerForView controller;
    private Image selectedImage;



    @FXML
    public ChoiceBox<String> languageSelector;
    @FXML
    public Label editUserLabel;
    @FXML
    public TextField editUsernameField;
    @FXML
    public PasswordField oldPasswordField;
    @FXML
    public PasswordField newPasswordField;
    @FXML
    public Button deleteUserButton;
    @FXML
    public Button editUserButton;
    @FXML
    public Label newPasswordLabel;
    @FXML
    public Label oldPasswordLabel;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label languageLabel;
    @FXML
    private  ImageView profilePic;


    //set backend controller
    @Override
    public void setController(IControllerForView controller) {
        this.controller = controller;
    }

    public void setStage(Stage stage) {
        this.editUserStage = stage;
    }

    //User edit window
    public void initialize() {
        Locale locale = SessionManager.getLocale();
        rb = ResourceBundle.getBundle("language",  locale);
        setTexts();
        List<String> languages = SessionManager.getLanguages();//add languages to selector
        for (String language : languages) {
            languageSelector.getItems().add(language);
        }
        languageSelector.setValue(SessionManager.getLanguageString());//get language Abbreviation
        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> user.setLanguageCode(newValue)//set user language to selected
        );


    }

    public void setTexts() {
        languageLabel.setText(rb.getString("language"));
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

    }

    /**
     * This is called when user clicks on the edit button. It passes delete command to database.
     */
    // Delete user method
    public void deleteUser() {
        //ask for confirmation before deleting user
        String deleteAccountMessage = rb.getString("deleteAccountMessage");
        String deleteAccountTitle = rb.getString("deleteAccountTitle");
        if (Boolean.TRUE.equals(PopupWindow.askForConfirmation(deleteAccountTitle, deleteAccountMessage))) {   //if user confirms, delete user
            boolean isDeleted = controller.deleteUser(user);
            //check if user is deleted from database
            if (Boolean.TRUE.equals(isDeleted)) {
                editUserStage.close();
                SessionManager.clearUser();
                SceneManager.switchScene("StartScreen.fxml");
            }

        }
    }

    /**
     * This is called when user wants to update own profile info. It passes update command to database.
     */
    public void updateUser() {
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
        if (!oldPassword.equals(user.getPassword())) {
            PopupWindow.showError("Wrong password", "Old password is incorrect");
            return;
        }

        String languageCode = user.getLanguageCode();
        boolean isUpdated = controller.updateUser(oldUsername, newUsername, newPassword, newProfilePicture, languageCode);
        if (isUpdated) {
            //if database is updated, update user object
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            user.setProfilePictureUrl(newProfilePicture.getUrl());
            editUserStage.close();
            //refresh main screen
            SceneManager.switchScene("MainScreen.fxml");
        }


    }

    /**
     * This function handles user profile pic adding
     */
    @FXML
    public void addProfilePicture() {
        selectedImage = imageAdder.addPicture();
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
