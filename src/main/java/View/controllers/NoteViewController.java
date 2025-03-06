package View.controllers;

import Model.Note;
import View.IControllerForGUI;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NoteViewController implements UiInterface {
    public ImageView noteImage;
    public TextArea noteContent;
    public DatePicker dateSelector;
    public Spinner hourSpinner;
    public Spinner minuteSpinner;
    public TextField noteTitleField;
    public Label noteTitle;
    public CheckBox editCheckbox;
    IControllerForGUI controller;

    Stage stage;
    Note currentNote;
    Note oldNote;

    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    public void initialize() {
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        editCheckbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (editCheckbox.isSelected()) {
                enableEditing();
            } else {
                disableEditing();
            }
        });
    }


    public void deleteNote() {
        //controller.deleteNote();
    }

    public void updateNote() {
        oldNote = currentNote;
        currentNote.setTitle(noteTitleField.getText());
        currentNote.setBody(noteContent.getText());
        LocalDate selectedDate = dateSelector.getValue();
        LocalTime selectedTime = LocalTime.of((int) hourSpinner.getValue(), (int) minuteSpinner.getValue());
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);
        currentNote.setNotificationTime(selectedDateTime);

        if (!controller.updateNote(currentNote)) {
            //if update fails, revert to old note
            currentNote = oldNote;
        }
        stage.close();


    }

    @Override
    public void setNoteToEdit(Note note) {
        this.currentNote = note;
        noteImage.setImage(new Image(currentNote.getImage()));
        noteContent.setText(currentNote.getContent());
        noteTitleField.setText(currentNote.getTitle());
        dateSelector.setValue(currentNote.getDate().toLocalDate());
        hourSpinner.getValueFactory().setValue(currentNote.getDate().getHour());
        minuteSpinner.getValueFactory().setValue(currentNote.getDate().getMinute());

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addNoteImage(MouseEvent mouseEvent) {
    }


    public void enableEditing() {
        noteContent.setEditable(true);
        noteTitleField.setEditable(true);
        dateSelector.setDisable(false);
        hourSpinner.setDisable(false);
    }

    public void disableEditing() {
        noteContent.setEditable(false);
        noteTitleField.setEditable(false);
        dateSelector.setDisable(true);
        hourSpinner.setDisable(true);

    }
}
