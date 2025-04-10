package view.controllers;
import model.Note;
import view.IControllerForGUI;
import view.managers.SceneManager;
import view.managers.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartScreenController implements UiInterface {
    String languageString = SessionManager.getLanguageString();
    Locale locale;
    ResourceBundle rb;
    public Label languageLabel;
    public Button loginButton;
    public Button signupButton;
    public Label appTitle;
    public Label notRegisteredLabel;
    public ChoiceBox languageSelector;
    IControllerForGUI controller;
    Stage stage;

    @Override
    public void initialize() {
        List<String> languages = SessionManager.getLanguages();
        for(String language: languages){
            languageSelector.getItems().add(language);
        }
        //languageSelector.getItems().addAll("EN", "FI", "JA","AR");//set the language options
        languageSelector.setValue(languageString);//set the default language
        setLanguage();
        //add listener to the language selector
        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            languageString = newValue.toString();
            setLanguage();
        });

    }
    //set backend controller
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;

    }

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    //these methods switch the scene to the login or signup screen

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        //switch to login screen
        SceneManager.switchScene("LoginScreen.fxml");




    }

    @FXML
    public void handleSignup(ActionEvent actionEvent) {
        //switch to signup screen
        SceneManager.switchScene("SignupScreen.fxml");

    }


    public void setLanguage() {
        //set the language of the UI
        SessionManager.setLanguage(languageString);
        locale = SessionManager.getLocale();
        rb = ResourceBundle.getBundle("language",locale);
        languageLabel.setText(rb.getString("language"));
        loginButton.setText(rb.getString("login"));
        signupButton.setText(rb.getString("signup"));
        appTitle.setText(rb.getString("appTitle"));
        notRegisteredLabel.setText(rb.getString("noAccount"));
    }


}
