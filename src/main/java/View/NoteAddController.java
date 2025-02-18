package View;

import Controller.Controller;
import Model.Note;
import Model.User;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class NoteAddController {
    public DatePicker dateSelector;
    public Spinner hourSpinner;
    public Spinner minuteSpinner;
    ImageAdder imageAdder;
    public TextField titleField;
    IControllerForGUI controller;
    public ImageView noteImage;
    public TextArea noteContent;
    private Stage noteStage;
    DatePicker datePicker;
    Image selectedImage;
    User user;

    public NoteAddController() {
        imageAdder = new ImageAdder();
        controller = Controller.getInstance();
        user = SessionManager.getCurrentUser();
        datePicker = new DatePicker();
    }

    public void initialize() {
        datePicker.setValue(LocalDate.now());
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }


    //this is method is called when the user clicks the add note button
    public void addNote(MouseEvent mouseEvent) {
        LocalDate selectedDate = datePicker.getValue();
        LocalTime selectedTime = LocalTime.of((int) hourSpinner.getValue(), (int) minuteSpinner.getValue());
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);
        if(selectedImage == null){
            selectedImage = new Image("/images/placeholder.png");
        }

        List<Note> notes = controller.addNote(user.getUsername(), titleField.getText(), noteContent.getText(), selectedImage, selectedDateTime);
        if (notes == null) {
            ErrorPopup.showError("Error", "Note could not be added");
        } else {
            user.setNotes(notes);
            noteStage.close();
            //refresh main screen
            SceneManager.switchScene("MainScreen.fxml");
        }
        ;


    }

    //this method is called when the wants to add an image to the note
    public void addNoteImage(MouseEvent mouseEvent) {
        selectedImage = imageAdder.addPicture(mouseEvent);
        if (selectedImage != null) {
            noteImage.setImage(selectedImage);
        }


    }

    //this is setter method for the stage so stage can be closed when the note is added
    public void setStage(Stage addNoteStage) {
        this.noteStage = addNoteStage;

    }
}
