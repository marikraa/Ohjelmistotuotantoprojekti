package FrontEnd;

import Controller.CRUDController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class GUIController {
    //create controller for CRUD operations
    CRUDController crudController = new CRUDController();
    //create json parser
    ObjectMapper objectMapper = new ObjectMapper();
    User user;

    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    TextField searchNote;

    public void sortNotes(ActionEvent actionEvent) {
        user.sortNotes(searchNote.getText());

    }

    public void logOut(MouseEvent mouseEvent) {
        if(crudController.logOut(username)){
            SceneManager.switchScene("StartScreen.fxml");
        }
    }


    //try to login with given credentials and get user data if successful
    public void login(ActionEvent actionEvent) {
        try {
            String jsonData = crudController.login(username.getText(), password.getText());
            if (jsonData == null) {
                throw new Error("Wrong credentials");
            }
            user = objectMapper.readValue(jsonData, User.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
