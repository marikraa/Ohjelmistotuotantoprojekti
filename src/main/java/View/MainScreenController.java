package View;

import Controller.Controller;
import javafx.application.Platform;
import javafx.scene.Parent;
import Model.Note;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainScreenController {


    public ImageView profilePic;
    public Label noteCounterLabel;
    List<Note> notes = new ArrayList<>();
    public Label usernameLabel;
    public GridPane noteGrid;
    IControllerForGUI controller = Controller.getInstance();
    User user = SessionManager.getCurrentUser();
    ImageAdder imageAdder = new ImageAdder();

    public void initialize() {
        int noteCount = user.getNotes().size();
        String username = user.getUsername();
        usernameLabel.setText(username);
        noteCounterLabel.setText("Notes: " + noteCount);
        profilePic.setImage(user.getProfilePicture());
    }

    @FXML
    public void logout(MouseEvent mouseEvent) {
        //clear the current user and switch to the start screen
        SessionManager.clearUser();
        SceneManager.switchScene("StartScreen.fxml");

    }

    public void sortNotes(ActionEvent actionEvent) {
    }

    public void addNote(ActionEvent actionEvent) {
        String title = "New Note";
        String content = "My first note";
        notes = controller.addNote(SessionManager.currentUser.getUsername(), title, content);
    }


    public void drawNotes() {
        notes = user.getNotes();
        for (Note note : notes) {

        }

    }


    @FXML
    public void addProfilePicture(MouseEvent mouseEvent) {
        Image selectedImage = imageAdder.addPicture(mouseEvent);
        //set user profile picture to the image that user has selected
        if (selectedImage != null) {
            profilePic.setImage(selectedImage);

        }
    }

    @FXML
    public void editUser(MouseEvent mouseEvent) {
        try {
            System.out.println("asddasasd");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/EditUser.fxml"));
            Stage editUserStage = new Stage();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            editUserStage.setScene(scene);
            editUserStage.show();
            EditUserController controller = loader.getController();
            //passing the stage to the controller
            controller.setEditUserStage(editUserStage);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}

