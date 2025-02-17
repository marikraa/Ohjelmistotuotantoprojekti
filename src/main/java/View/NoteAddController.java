package View;

import Controller.Controller;
import Model.Note;
import Model.User;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class NoteAddController {
    ImageAdder imageAdder;
    public TextField titleField;
    IControllerForGUI controller;
    public TextField dueDateField;
    public ImageView noteImage;
    public TextArea noteContent;
    private Stage noteStage;
    Image selectedImage;
    User user;
    public NoteAddController() {
        imageAdder = new ImageAdder();
        controller = Controller.getInstance();
        user = SessionManager.getCurrentUser();
    }

    public void addNote(MouseEvent mouseEvent) {
        List<Note> notes =  controller.addNote(user.getUsername(),titleField.getText(),noteContent.getText(),selectedImage,dueDateField.getText());
        if(notes==null){
           ErrorPopup.showError("Error","Note could not be added");
        }
        else{
            user.setNotes(notes);
            noteStage.close();
            //refresh main screen
            SceneManager.switchScene("MainScreen.fxml");
        };


    }

    public void addNoteImage(MouseEvent mouseEvent) {
       selectedImage = imageAdder.addPicture(mouseEvent);

    }

    public void setStage(Stage addNoteStage) {
        this.noteStage= addNoteStage;

    }
}
