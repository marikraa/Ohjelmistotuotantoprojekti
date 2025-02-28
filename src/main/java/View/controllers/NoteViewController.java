package View.controllers;

import Controller.Controller;
import Model.Note;
import View.IControllerForGUI;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NoteViewController {
    public ImageView noteImage;
    public TextArea noteContent;
    public TextField titleField;
    public DatePicker dateSelector;
    public Spinner hourSpinner;
    public Spinner minuteSpinner;
    IControllerForGUI controller = Controller.getInstance();
    Stage stage;
    Note currentNote;

    public void initialize() {


    }
    public void deleteNote() {
        //controller.deleteNote();
    }

    public void updateNote() {

    }


    public void setNoteView(Note note) {
        this.currentNote = note;
    }

    public void setStage(Stage stage) {
       this.stage = stage;
    }

    public void addNoteImage(MouseEvent mouseEvent) {
    }

    public void addNote(MouseEvent mouseEvent) {
    }
}
