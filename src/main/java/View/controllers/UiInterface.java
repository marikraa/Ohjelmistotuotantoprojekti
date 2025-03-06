package View.controllers;

import Model.Note;
import View.IControllerForGUI;
import javafx.stage.Stage;

public interface UiInterface {
    void setController(IControllerForGUI controller);

    void setNoteToEdit(Note note);
    void setStage(Stage stage);
}
