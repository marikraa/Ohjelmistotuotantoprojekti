package FrontEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartScreenController {

    //these methods switch the scene to the login or signup screen
    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        SceneManager.switchScene("LoginScreen.fxml");

    }

    @FXML
    public void handleSignup(ActionEvent actionEvent) {
        //switch to signup screen
        SceneManager.switchScene("SignupScreen.fxml");
        //initialize user and add default profile picture


    }
}
