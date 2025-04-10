package view.controllers;

import model.Note;
import view.IControllerForGUI;
import javafx.stage.Stage;

public interface UiInterface {
    void setController(IControllerForGUI controller);
    void setNoteToEdit(Note note);
    void setStage(Stage stage);

    void initialize();
}
