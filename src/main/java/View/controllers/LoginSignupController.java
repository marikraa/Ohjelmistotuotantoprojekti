package View.controllers;

import Model.Note;
import Model.User;
import View.*;
import View.managers.SceneManager;
import View.managers.SessionManager;
import View.utilies.PopupWindow;
import View.utilies.ImageAdder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//login and signup controller is used to handle login and signup operations both fxml uses this controller
public class LoginSignupController implements UiInterface {
    public VBox container;
    private IControllerForGUI controller;
    Stage stage;


    Image selectedImage;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField passwordField2;
    @FXML
    ImageView profilePicView;
    ProgressIndicator progressIndicator;
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


    }


    //try to login with given credentials and get user data if successful

    @FXML
    public void login() {
        showLoadingIndicator();
        new Thread(() -> {
            User user = controller.login(usernameField.getText(), passwordField.getText());
            Platform.runLater(() -> {
                hideLoadingIndicator();
                if (user == null) {
                    PopupWindow.showError("Login failed", "Username or password is incorrect");
                } else {
                    openMainScreen(user);
                }
            });
        }).start();
    }

    @FXML
    public void signup() {
        showLoadingIndicator();
        new Thread(() -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
                Platform.runLater(() -> {
                    hideLoadingIndicator();
                    PopupWindow.showError("Empty fields", "Please fill all fields");
                });
                return;
            }

            if (!passwordField.getText().equals(passwordField2.getText())) {
                Platform.runLater(() -> {
                    hideLoadingIndicator();
                    PopupWindow.showError("Passwords do not match", "Please check passwords");
                });
                return;
            }

            Image profPic = (selectedImage == null)
                    ? new Image(Objects.requireNonNull(getClass().getResource("/images/defaultProfilePic.png")).toExternalForm())
                    : selectedImage;

            User user = controller.signup(usernameField.getText(), passwordField.getText(), profPic);
            Platform.runLater(() -> {
                hideLoadingIndicator();
                if (user == null) {
                    PopupWindow.showError("Username already exists", usernameField.getText() + " is already taken");
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
        Label loadingLabel = new Label("Loading...");
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
    // Simuloi latausta asettamalla ProgressIndicatorin arvo
    private void simulateLoading(ProgressIndicator progressIndicator) {
        // Simuloidaan latausprosessi (tässä esimerkissä 5 sekuntia)
        new Thread(() -> {
            try {
                // Aseta progressindikaattori "indeterminate" tilaan, eli pyörimään
                this.progressIndicator.setProgress(-1.0);  // Tämä tekee siitä pyörivän (indeterminate)

                // Voit myös asettaa progress-arvon (esim. 0-1)
                for (double progress = 0.0; progress <= 1.0; progress += 0.1) {
                    Thread.sleep(10000);  // Simuloi aikaa
                    final double p = progress;  // Final-muuttuja käyttöliittymän säilyttämiseksi

                    // Päivitä käyttöliittymä JavaFX-säikeessä
                    javafx.application.Platform.runLater(() -> this.progressIndicator.setProgress(p));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
