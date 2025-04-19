package view.controllers;

import javafx.stage.Stage;
import model.Note;
import view.IControllerForView;

/**
 * This is interface of the UI controllers. It helps session manager to handle every controller same way.
 */
public interface UiInterface {
    void setController(IControllerForView controller);

    void setNoteToEdit(Note note);

    void setStage(Stage stage);
}
