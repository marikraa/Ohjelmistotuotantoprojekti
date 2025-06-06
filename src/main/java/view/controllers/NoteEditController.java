package view.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Note;
import view.IControllerForView;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.ImageAdder;
import view.utilies.PopupWindow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is controller for the Editing note
 */
public class NoteEditController implements UiInterface {
    private static final Logger LOGGER = Logger.getLogger(NoteEditController.class.getName());
    private ResourceBundle rb;
    private  IControllerForView controller;
    private  Stage stage;
    private Note currentNote;


    @FXML
    public Label notificationTimeLabel;
    @FXML
    public Label titleLabel;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    @FXML
    public ImageView noteImage;
    @FXML
    public TextArea noteContent;
    @FXML
    public DatePicker dateSelector;
    @FXML
    public Spinner<Integer> hourSpinner;
    @FXML
    public Spinner<Integer> minuteSpinner;
    @FXML
    public TextField noteTitleField;
    @FXML
    public Label noteTitle;
    @FXML
    public CheckBox editCheckbox;
    @FXML
    public Button editProfilePic;

    public void initialize() {
        Locale locale = SessionManager.getLocale();
        rb = ResourceBundle.getBundle("language", locale);
        noteImage.setOnMouseEntered(event -> {
            noteImage.setCursor(Cursor.HAND);
            String tooltipString = rb.getString("bigImageTip");
            Tooltip tooltip = new Tooltip(tooltipString);
            Tooltip.install(noteImage, tooltip);
        });
        setTexts();
        editCheckbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (editCheckbox.isSelected()) {
                enableEditing();
            } else {
                disableEditing();
            }
        });
    }


    private void setTexts() {
        noteTitleField.setPromptText(rb.getString("noteTitle"));
        deleteButton.setText(rb.getString("deleteButton"));
        editButton.setText(rb.getString("editButton"));
        dateSelector.setPromptText(rb.getString("dateLabel"));
        editCheckbox.setText(rb.getString("editNote"));
        titleLabel.setText(rb.getString("lblTitle"));
        notificationTimeLabel.setText(rb.getString("editNotificationTime"));
        editProfilePic.setText(rb.getString("editImage"));
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }
    @Override
    public void setController(IControllerForView controller) {
        this.controller = controller;
    }

    /**
     * This function handles the deleting selected note
     */
    public void deleteNote() {
        String deleteNoteTitle = rb.getString("deleteNoteTitle");
        String deleteNoteMessage = rb.getString("deleteNoteMessage");
        if (Boolean.TRUE.equals(PopupWindow.askForConfirmation(deleteNoteTitle, deleteNoteMessage))) {
            try {
                boolean isDeleted = controller.deleteNote(currentNote);
                if (Boolean.TRUE.equals(isDeleted)) {
                    SessionManager.getCurrentUser().removeNote(currentNote);//remove from local storage
                    SceneManager.switchScene("MainScreen.fxml");
                    stage.close();
                } else {
                    LOGGER.log(Level.WARNING, () -> "Note deletion failed: controller returned false for note: " + currentNote);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e, () -> "Exception occurred while deleting note: " + currentNote);
            }


        }
    }

    /**
     * This handles the editing of the note and updating it to database.
     */
    public void updateNote() {
        currentNote.setTitle(noteTitleField.getText());
        currentNote.setBody(noteContent.getText());
        currentNote.setImageUrl(noteImage.getImage().getUrl());
        LocalDate selectedDate = dateSelector.getValue();
        LocalTime selectedTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);
        currentNote.setNotificationTime(selectedDateTime);

        if (controller.updateNote(currentNote)) {
            //if update is successful, go back to main screen
            SceneManager.switchScene("MainScreen.fxml");
            stage.close();

        }


    }

    /**
     * This function sets the note to be edited. It is called in a main screen after note is clicked.
     * @param note is a passed parameter from click event. And it is the note to be edited.
     */
    @Override
    public void setNoteToEdit(Note note) {
        this.currentNote = note;
        noteImage.setImage(new Image(currentNote.getImageUrl()));
        noteContent.setText(currentNote.getBody());
        noteTitleField.setText(currentNote.getTitle());
        String noTitleLabel = rb.getString("lblNoTitle");
        if (currentNote.getTitle().isEmpty()) {
            noteTitle.setText(noTitleLabel);
        } else {
            noteTitle.setText(note.getTitle());
        }
        dateSelector.setValue(currentNote.getNotificationTime().toLocalDate());
        hourSpinner.getValueFactory().setValue(currentNote.getNotificationTime().getHour());
        minuteSpinner.getValueFactory().setValue(currentNote.getNotificationTime().getMinute());


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This handles image adding to note
     *
     */
    private void addNoteImage() {
        ImageAdder imageAdder = new ImageAdder();
        Image selectedImage = imageAdder.addPicture();
        if (selectedImage != null) {
            noteImage.setImage(selectedImage);
        }
    }

    /**
     * This is called when edit note checkbox is checked. Allows note to be edited.
     */
    private void enableEditing() {
        noteTitleField.setEditable(true);
        noteContent.setEditable(true);
        dateSelector.setDisable(false);
        hourSpinner.setDisable(false);
        minuteSpinner.setDisable(false);
        editProfilePic.setOnMouseClicked(e->addNoteImage());

    }

    /**
     * This is called when edit note checkbox is unchecked. Disables editing of note.
     */
    private void disableEditing() {
        noteContent.setEditable(false);
        noteTitleField.setEditable(false);
        dateSelector.setDisable(true);
        hourSpinner.setDisable(true);
        minuteSpinner.setDisable(true);
        editProfilePic.setOnMouseClicked(event -> {
        });

    }

    /**
     * When note image is clicked it will be shown as a bigger picture.
     */
    public void openImage() {
        PopupWindow.showImage(noteImage.getImage());
    }
}
