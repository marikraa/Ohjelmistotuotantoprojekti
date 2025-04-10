package view.controllers;

import model.Note;
import model.User;
import view.*;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.PopupWindow;
import view.utilies.ImageAdder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//login and signup controller is used to handle login and signup operations both fxml uses this controller
public class LoginSignupController implements UiInterface {
    public VBox container;
    public Button signUpButton;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label loginHeader;
    public Label signUpHeader;
    public Button loginButton;
    public Button addImageButton;
    Image selectedImage;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    ImageView profilePicView;
    ProgressIndicator progressIndicator;


    private IControllerForGUI controller;
    Stage stage;
    Locale locale = SessionManager.getLocale();
    ResourceBundle rb = ResourceBundle.getBundle("language", locale);



    //set backend controller
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage=stage;

    }

    @Override
    public void initialize() {

        if(signUpButton!=null){
            signUpButton.setText(rb.getString("signup"));
            signUpHeader.setText(rb.getString("signup"));
            addImageButton.setText(rb.getString("addImage"));
        }
        if(loginHeader!=null){
            loginHeader.setText(rb.getString("login"));
            loginButton.setText(rb.getString("login"));

        }
        passwordLabel.setText(rb.getString("password"));
        usernameLabel.setText(rb.getString("username"));
        //passwordField.setPromptText(rb.getString("newPassword"));
        //usernameField.setPromptText(rb.getString("newUsername"));

    }


    //try to login with given credentials and get user data if successful

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

    @FXML
    public void signup() {
        signUpButton.setVisible(false);
        signUpButton.setManaged(false);
        showLoadingIndicator();
        new Thread(() -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                Platform.runLater(() -> {
                    hideLoadingIndicator();
                    String error = rb.getString("error");
                    String emptyErrorMessage = rb.getString("errorMessage");
                    PopupWindow.showError(error, emptyErrorMessage);
                });
                return;
            }


            Image profPic = (selectedImage == null)
                    ? new Image(Objects.requireNonNull(getClass().getResource("/images/defaultProfilePic.png")).toExternalForm())
                    : selectedImage;
            String languageCode = SessionManager.getLanguageString();
            User user = controller.signup(usernameField.getText(), passwordField.getText(), profPic, languageCode);

            Platform.runLater(() -> {
                hideLoadingIndicator();
                if (user == null) {
                    String message = MessageFormat.format(rb.getString("errorUsername"),usernameField.getText());
                    String error = rb.getString("errorUsernameTitle");
                    PopupWindow.showError(error, message);
                    SceneManager.switchScene("SignupScreen.fxml");
                } else {
                    openMainScreen(user);
                }
            });
        }).start();


    }
    private void showLoadingIndicator() {
        if (progressIndicator == null) {
            progressIndicator = new ProgressIndicator();
            progressIndicator.setPrefSize(40, 40);
        }

        container.getChildren().removeIf(node -> node instanceof VBox); // Poistaa aiemmat indikaattorit
        String loadingIndicatorText = rb.getString("loading");
        Label loadingLabel = new Label(loadingIndicatorText);
        loadingLabel.getStyleClass().addAll("smalltext");
        VBox vbox = new VBox( loadingLabel, progressIndicator);
        vbox.setAlignment(Pos.CENTER);
        container.getChildren().add(vbox);
    }

    private void hideLoadingIndicator() {
        container.getChildren().removeIf(node -> node instanceof VBox); // Poistaa latausindikaattorin
    }

    @FXML
    public void addProfilePicture(MouseEvent mouseEvent) {//when user clicks add image button open file chooser
        ImageAdder imageAdder = new ImageAdder();
        selectedImage =imageAdder.addPicture(mouseEvent);
        profilePicView.setImage(selectedImage);


    }

    public void openMainScreen(User currentUser) {
        SessionManager.setCurrentUser(currentUser);
        SceneManager.switchScene("MainScreen.fxml");
    }

    @FXML
    public void openStartScreen() {
        SceneManager.switchScene("StartScreen.fxml");
    }

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }




}
