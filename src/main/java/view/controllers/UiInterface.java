package view.controllers;

import javafx.stage.Stage;
import model.Note;
import view.IControllerForGUI;

public interface UiInterface {
    void setController(IControllerForGUI controller);

    void setNoteToEdit(Note note);

    void setStage(Stage stage);

    void initialize();
}
