package view.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Note;
import model.User;
import view.IControllerForGUI;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.ImageAdder;
import view.utilies.PopupWindow;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is controller for the user registration and login.
 */
//login and signup controller is used to handle login and signup operations both fxml uses this controller
public class LoginSignupController implements UiInterface {
    @FXML
    public VBox container;
    @FXML
    public Button signUpButton;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label loginHeader;
    @FXML
    public Label signUpHeader;
    @FXML
    public Button loginButton;
    @FXML
    public Button addImageButton;
    ProgressIndicator progressIndicator;
    Stage stage;
    Locale locale = SessionManager.getLocale();
    ResourceBundle rb = ResourceBundle.getBundle("language", locale);
    Image selectedImage;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    ImageView profilePicView;
    private IControllerForGUI controller;

    //set backend controller
    @Override
    public void initialize() {
        setTexts();

    }

    public void setTexts() {
        if (signUpButton != null) {
            signUpButton.setText(rb.getString("signup"));
            signUpHeader.setText(rb.getString("signup"));
            addImageButton.setText(rb.getString("addImage"));
        }
        if (loginHeader != null) {
            loginHeader.setText(rb.getString("login"));
            loginButton.setText(rb.getString("login"));

        }
        passwordLabel.setText(rb.getString("password"));
        usernameLabel.setText(rb.getString("username"));
    }
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;

    }


    //try to login with given credentials and get user data if successful

    /**
     * This handles login part.  Cheks if user exists and if true, Main screen will open
     */
    @FXML
    public void login() {
        showLoadingIndicator();
        loginButton.setVisible(false);
        loginButton.setManaged(false);
        new Thread(() -> {
            User user = controller.login(usernameField.getText(), passwordField.getText());
            Platform.runLater(() -> {
                hideLoadingIndicator();
                if (user == null) {
                    String error = rb.getString("errorLoginTitle");
                    String errorMsg = rb.getString("errorLogin");
                    PopupWindow.showError(error, errorMsg);
                    SceneManager.switchScene("LoginScreen.fxml");
                } else {
                    openMainScreen(user);
                }
            });
        }).start();
    }

    /**
     * This handles signup part. If User not taken, user will be added to database and mainscreen will open.
     */
    @FXML
    public void signup() {
        signUpButton.setVisible(false);
        signUpButton.setManaged(false);
        addImageButton.setVisible(false);
        addImageButton.setManaged(false);
        showLoadingIndicator();
        new Thread(() -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                Platform.runLater(() -> {
                    hideLoadingIndicator();
                    String error = rb.getString("error");
                    String emptyErrorMessage = rb.getString("errorMessage");
                    PopupWindow.showError(error, emptyErrorMessage);
                });
                return;
            }


            Image profPic = (selectedImage == null) ? new Image(Objects.requireNonNull(getClass().getResource("/images/defaultProfilePic.png")).toExternalForm()) : selectedImage;
            String languageCode = SessionManager.getLanguageString();
            User user = controller.signup(usernameField.getText(), passwordField.getText(), profPic, languageCode);

            Platform.runLater(() -> {
                hideLoadingIndicator();
                if (user == null) {
                    String message = MessageFormat.format(rb.getString("errorUsername"), usernameField.getText());
                    String error = rb.getString("errorUsernameTitle");
                    PopupWindow.showError(error, message);
                    SceneManager.switchScene("SignupScreen.fxml");
                } else {
                    openMainScreen(user);
                }
            });
        }).start();


    }

    /**
     * This shows loading indicator when processing user data
     */
    private void showLoadingIndicator() {
        if (progressIndicator == null) {
            progressIndicator = new ProgressIndicator();
            progressIndicator.setPrefSize(40, 40);
        }

        container.getChildren().removeIf(VBox.class::isInstance); // Delete an indicator already exists
        String loadingIndicatorText = rb.getString("loading");
        Label loadingLabel = new Label(loadingIndicatorText);
        loadingLabel.getStyleClass().addAll("smalltext");
        VBox vbox = new VBox(loadingLabel, progressIndicator);
        vbox.setAlignment(Pos.CENTER);
        container.getChildren().add(vbox);
    }

    /**
     * Hides loading indicator
     */
    private void hideLoadingIndicator() {
        container.getChildren().removeIf(VBox.class::isInstance); // Delete indicator
    }

    /**
     * If user clicks add image button in signup this will process it.
     *
     */
    @FXML
    public void addProfilePicture() {//when user clicks add image button open file chooser
        ImageAdder imageAdder = new ImageAdder();
        selectedImage = imageAdder.addPicture();
        profilePicView.setImage(selectedImage);


    }

    /**
     * this will open mainscreen after login or signup is successful.
     * @param currentUser is user which registered or logged in
     */
    //when singed/lodged in user, set user to session manager and open main screen
    public void openMainScreen(User currentUser) {
        SessionManager.setCurrentUser(currentUser);
        SceneManager.switchScene("MainScreen.fxml");
    }

    /**
     * Log out is done by this
     */
    @FXML
    public void openStartScreen() {
        SceneManager.switchScene("StartScreen.fxml");
    }

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }


}
