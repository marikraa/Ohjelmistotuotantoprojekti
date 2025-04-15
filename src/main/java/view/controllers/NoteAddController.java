package view.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Note;
import model.User;
import view.IControllerForGUI;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.ImageAdder;
import view.utilies.PopupWindow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is controller for adding note
 */
public class NoteAddController implements UiInterface {
    @FXML
    public Label addNoteLabel;
    @FXML
    public Button addNoteImage;
    @FXML
    public Label noteTitleLabel;
    @FXML
    public Label notificationTimeLabel;
    @FXML
    public DatePicker dateSelector;
    @FXML
    public Spinner<Integer> hourSpinner;
    @FXML
    public Spinner<Integer> minuteSpinner;
    @FXML
    public Button addNoteButton;
    @FXML
    public TextField titleField;
    @FXML
    public ImageView noteImage;
    @FXML
    public TextArea noteContent;
    Locale locale = SessionManager.getLocale();
    ResourceBundle rb = ResourceBundle.getBundle("language", locale);
    ImageAdder imageAdder;
    IControllerForGUI controller;
    Image selectedImage;
    User user;
    private Stage noteStage;


    public NoteAddController() {
        imageAdder = new ImageAdder();
        user = SessionManager.getCurrentUser();
        dateSelector = new DatePicker();
    }

    //set backend controller
    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    public void initialize() {
        addNoteLabel.setText(rb.getString("addNote"));
        noteTitleLabel.setText(rb.getString("lblTitle"));
        titleField.setPromptText(rb.getString("noteTitle"));
        addNoteImage.setText(rb.getString("addImage"));
        addNoteButton.setText(rb.getString("addNote"));
        notificationTimeLabel.setText(rb.getString("editNotificationTime"));
        dateSelector.setValue(LocalDate.now());
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

    }

    /**
     * This handles note adding functionality
     */
    //this is method is called when the user clicks the add note button
    public void addNote() {
        LocalDate selectedDate = dateSelector.getValue();
        LocalTime selectedTime = LocalTime.of(hourSpinner.getValue(), minuteSpinner.getValue());
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);
        if (selectedImage == null) {
            selectedImage = new Image("/images/placeholder.png");
        }

        List<Note> notes = controller.addNote(user.getUsername(), titleField.getText(), noteContent.getText(), selectedImage, selectedDateTime);
        if (notes == null) {
            PopupWindow.showError("Error", "Note could not be added");
        } else {
            user.setNotes(notes);
            noteStage.close();
            //refresh main screen
            SceneManager.switchScene("MainScreen.fxml");
        }


    }

    /**
     * This handles Image adding to note
     */
    //this method is called when the wants to add an image to the note
    public void addImage() {
        selectedImage = imageAdder.addPicture();
        if (selectedImage != null) {
            noteImage.setImage(selectedImage);
        }


    }

    //this is setter method for the stage so stage can be closed when the note is added
    public void setStage(Stage addNoteStage) {
        this.noteStage = addNoteStage;

    }

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this controller
    }


}
