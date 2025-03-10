package View.controllers;

import View.*;
import View.managers.SceneManager;
import View.managers.SessionManager;
import View.utilies.ImageAdder;
import View.utilies.NoteNode;
import javafx.application.Platform;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainScreenController implements UiInterface {
    private IControllerForGUI controller;
    public TextField searchField;
    public ImageView profilePic;
    public Label noteCounterLabel;
    public ScrollPane noteArea;
    Stage stage;
    User user = SessionManager.getCurrentUser();
    @FXML
    public Label usernameLabel;
    //IControllerForG UI controller = Controller.getInstance();
    GridPane noteGrid;
    ImageAdder imageAdder = new ImageAdder();
    List<Note> filteredNotes = new ArrayList<>();


    @Override
    public void setController(IControllerForGUI controller) {
        this.controller = controller;
    }

    public void initialize() {
        int noteCount = user.getNotes().size();
        String username = user.getUsername();
        usernameLabel.setText(username);
        noteCounterLabel.setText("Notes: " + noteCount);
        profilePic.setImage(new Image(user.getProfilePictureUrl()));
        System.out.println("Main init");
        drawNotes();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
            if (newValue.isEmpty()) {
                drawNotes();
            } else {
                String search = newValue.toLowerCase(); // Muutetaan pieniksi kirjaimiksi
                filteredNotes.clear();
                for (Note note : user.getNotes()) {
                    String title = note.getTitle().toLowerCase();
                    if (isSubsequence(search, title)) { // Tarkistetaan järjestys
                        filteredNotes.add(note);
                    }
                }

                drawFilteredNotes(filteredNotes);
            }
        });});


    }

    @FXML
    public void logout(MouseEvent mouseEvent) {
        //clear the current user and switch to the start screen
        SessionManager.clearUser();
        SceneManager.switchScene("StartScreen.fxml");

    }


    @FXML
    public void addNote() {
        //open the add note window as a modal
        SceneManager.openModal("NoteAdd.fxml", null);

    }


    @FXML
    public void editUser(MouseEvent mouseEvent) {
        SceneManager.openModal("EditUser.fxml", null);
    }

    public void drawNotes() {
        if (noteGrid == null) {
            noteGrid = new GridPane(10, 10);
            noteGrid.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
            noteArea.setContent(noteGrid);
        } else {
            noteGrid.getChildren().clear(); // Tyhjennä vanha sisältö
        }


        Button addNoteButton = createAddButton();
        noteGrid.add(addNoteButton, 0, 0);

        int i = 1, j = 0;
        for (Note note : user.getNotes()) {
            Button noteButton = createNoteButton(note);
            noteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openNoteView(note));

            noteGrid.add(noteButton, i, j);
            i++;
            if (i > 1) {
                i = 0;
                j++;
            }
        }
    }

    //this method is used to draw the notes that match the search query
    private void drawFilteredNotes(List<Note> filteredNotes) {
        noteGrid.getChildren().clear();

        if (filteredNotes.isEmpty()) {
            ImageView notFound = new ImageView(new Image("./images/NotFound.png"));
            notFound.setFitHeight(400);
            notFound.setFitWidth(400);
            notFound.getStyleClass().add("notFound");
            noteArea.setContent(notFound);
            return;
        }

        int i = 0, j = 0;
        for (Note note : filteredNotes) {
            Button noteButton = createNoteButton(note);
            noteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openNoteView(note));

            noteGrid.add(noteButton, i, j);
            i++;
            if (i > 1) {
                i = 0;
                j++;
            }
        }
    }

    public Button createAddButton() {
        // Create button for adding a new note
        Button addNoteButton = new Button("+");
        addNoteButton.setId("addNoteButton");
        addNoteButton.setPrefSize(200.0, 200.0);
        addNoteButton.setMnemonicParsing(false);

        // add style classes to the button
        addNoteButton.getStyleClass().addAll("note", "addNote");

        // Set button action
        addNoteButton.setOnMouseClicked(event -> addNote());
        //add add note button to the grid
        return addNoteButton;
    }
    //open the note view window as a modal when a note is clicked

    public void openNoteView(Note note) {
        SceneManager.openModal("FullNote.fxml", note);

    }


    public Button createNoteButton(Note note) {
        Button noteButton = new Button();
        NoteNode noteNode = new NoteNode(note);
        VBox noteVBox = noteNode.createNoteNode();
        noteButton.getStyleClass().addAll("note");
        noteButton.setGraphic(noteVBox);
        noteButton.setPrefSize(200, 200);
        //noteButton.setPrefWidth(200.0)
        return noteButton;
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

    @Override
    public void setNoteToEdit(Note note) {
        //not used in this class
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private boolean isSubsequence(String search, String text) {
        int searchIndex = 0, textIndex = 0;

        while (textIndex < text.length() && searchIndex < search.length()) {
            if (search.charAt(searchIndex) == text.charAt(textIndex)) {
                searchIndex++;
            }
            textIndex++;
        }

        return searchIndex == search.length(); // Kaikki haetut kirjaimet löytyivät järjestyksessä
    }
}

