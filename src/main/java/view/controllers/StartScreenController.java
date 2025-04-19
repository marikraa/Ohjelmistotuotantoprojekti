package view.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Note;
import view.IControllerForView;
import view.managers.SceneManager;
import view.managers.SessionManager;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is a controller for startscreen of the application. User can select either login or signup.
 */
public class StartScreenController implements UiInterface {
    private String languageString = SessionManager.getLanguageString();
    @FXML
    public Label languageLabel;
    @FXML
    public Button loginButton;
    @FXML
    public Button signupButton;
    @FXML
    public Label appTitle;
    @FXML
    public Label notRegisteredLabel;
    @FXML
    public ChoiceBox<String> languageSelector;


    public void initialize() {
        List<String> languages = SessionManager.getLanguages();
        for (String language : languages) {
            languageSelector.getItems().add(language);
        }
        //languageSelector.getItems().addAll("EN", "FI", "JA","AR");//set the language options
        languageSelector.setValue(languageString);//set the default language
        setLanguage();
        //add a listener to the language selector
        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            languageString = newValue;
            setLanguage();
        });

    }

    //set backend controller
    @Override
    public void setController(IControllerForView controller) {
        //not needed in this

    }


    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }

    @Override
    public void setStage(Stage stage) {
    }


    //these methods switch the scene to the login or signup screen

    /**
     *This is called when user clicks login button. It opens login screen.
     */
    @FXML
    public void handleLogin() {
        //switch to login screen
        SceneManager.switchScene("LoginScreen.fxml");


    }

    /**
     * This is called when user click signup button. It opens signup screen.
     */
    @FXML
    public void handleSignup() {
        //switch to signup screen
        SceneManager.switchScene("SignupScreen.fxml");

    }

    /**
     * This is setter function for language. When user selects a language from the dropdown, this function is called.
     */
    public void setLanguage() {
        //set the language of the UI
        SessionManager.setLanguage(languageString);
        Locale locale = SessionManager.getLocale();
        ResourceBundle rb = ResourceBundle.getBundle("language", locale);
        languageLabel.setText(rb.getString("language"));
        loginButton.setText(rb.getString("login"));
        signupButton.setText(rb.getString("signup"));
        appTitle.setText(rb.getString("appTitle"));
        notRegisteredLabel.setText(rb.getString("noAccount"));
    }


}
