package FrontEnd;

import Controller.CRUDController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class MainScreenController {

    public Label notecounter;
    public Label username;
    CRUDController crudController = CRUDController.getInstance();

    public void initialize() {
        int noteCount = SessionManager.getCurrentUser().getNotes().size();
        username.setText(SessionManager.getCurrentUser().getUsername());
        notecounter.setText("Note"+(noteCount > 1 ? "s":"")+": "+ noteCount);

    }
    @FXML
    public void logout(MouseEvent mouseEvent) {
        SessionManager.clearUser();
        SceneManager.switchScene("StartScreen.fxml");

    }

    public void sortNotes(ActionEvent actionEvent) {
    }



}


