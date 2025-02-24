package View.controllers;

import Controller.Controller;
import View.*;
import View.managers.SceneManager;
import View.managers.SessionManager;
import View.utilies.ImageAdder;
import View.utilies.NoteNode;
import javafx.scene.Parent;
import Model.Note;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainScreenController {
    //TODO Tämä on se search field mihin kirjoitetaan hakusana
    public TextField searchField;
    public ImageView profilePic;
    public Label noteCounterLabel;
    public ScrollPane noteArea;
    List<Note> notes = new ArrayList<>();
    @FXML
    public Label usernameLabel;
    IControllerForGUI controller = Controller.getInstance();
    User user = SessionManager.getCurrentUser();
    ImageAdder imageAdder = new ImageAdder();

    public void initialize() {
        int noteCount = user.getNotes().size();
        String username = user.getUsername();
        usernameLabel.setText(username);
        noteCounterLabel.setText("Notes: " + noteCount);
        profilePic.setImage(user.getProfilePicture());
        System.out.println("init");
        drawNotes("all",null);


    }

    @FXML
    public void logout(MouseEvent mouseEvent) {
        //clear the current user and switch to the start screen
        SessionManager.clearUser();
        SceneManager.switchScene("StartScreen.fxml");

    }
    @FXML
    public void sortNotes() {
        //sort notes by the search field
        String search = searchField.getText();
        if(!search.equals("")){
            drawNotes("search",search);
        }



    }

    @FXML
    public void addNote() {
        //open the add note window as a modal
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/NoteAdd.fxml"));
            Stage addNoteStage = new Stage();
            addNoteStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            addNoteStage.setScene(scene);
            addNoteStage.show();
            NoteAddController controller = loader.getController();
            controller.setStage(addNoteStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void editUser(MouseEvent mouseEvent) {
        //open the edit user window as a modal
        try {
            System.out.println("asddasasd");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/EditUser.fxml"));
            Stage editUserStage = new Stage();
            editUserStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            editUserStage.setScene(scene);
            editUserStage.show();
            EditUserController controller = loader.getController();
            //passing the stage to the controller
            controller.setStage(editUserStage);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void drawNotes(String type,String search) {
        int i;
        int j;
        //Create a gridpane for the notes
        GridPane noteGrid = new GridPane(10, 10);
        noteArea.setContent(noteGrid);
        if(type.equals("all")){
            notes = user.getNotes();
            // Create button for adding a new note
            Button addNoteButton = new Button("+");
            addNoteButton.setId("addNoteButton");
            addNoteButton.setPrefHeight(200.0);
            addNoteButton.setPrefWidth(200.0);
            addNoteButton.setMnemonicParsing(false);

            // add style classes to the button
            addNoteButton.getStyleClass().addAll("note","addNote");

            // Set button action
            addNoteButton.setOnMouseClicked(event -> addNote());
            //add add note button to the grid
            noteGrid.add(addNoteButton, 0, 0);
            //set i for 1 because the first column is for the add note button
            j = 0;
            i = 1;
        }else{
            //sort notes by the search field value
            notes = user.sortNotes(search);
            j = 0;
            i = 0;
        }


        //add notes to the grid
        for (Note note : notes) {
            NoteNode noteNode = new NoteNode(note);
            VBox noteVBox = noteNode.createNoteNode();
            noteGrid.add(noteVBox, i, j);
            i++;
            if (i > 1) {
                i = 0;
                j++;
            }

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

    public void addNoteImage(MouseEvent mouseEvent) {
    }
}

