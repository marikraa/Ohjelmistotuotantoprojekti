package View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartScreenController {

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
}
