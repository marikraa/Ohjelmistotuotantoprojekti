package View;

import Controller.CRUDController;
import Model.Note;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;


public class MainScreenController {
    List<Note> notes = new ArrayList<>();
    public Label notecounter;
    public Label username;
    public GridPane noteGrid;
    CRUDController crudController = CRUDController.getInstance();
    User user = SessionManager.getCurrentUser();

    public void initialize() {
        int noteCount = SessionManager.getCurrentUser().getNotes().size();
        username.setText(SessionManager.getCurrentUser().getUsername());
        notecounter.setText("Notes: " + noteCount);


    }
    @FXML
    public void logout(MouseEvent mouseEvent) {
        SessionManager.clearUser();
        SceneManager.switchScene("StartScreen.fxml");

    }

    public void sortNotes(ActionEvent actionEvent) {
    }

    public void addNote(ActionEvent actionEvent) {
        Note newNote = new Note("New Note", "Write your note here");
        notes = crudController.addNote(newNote);
    }


public void drawNotes(){
        notes = user.getNotes();
        for(Note note : notes){

        }

}

}


