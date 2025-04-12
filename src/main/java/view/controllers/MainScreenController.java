package view.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Note;
import model.User;
import view.IControllerForGUI;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.ImageAdder;
import view.utilies.NoteNode;
import view.utilies.NoteNodeBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainScreenController implements UiInterface {
    @FXML
    public Button logoutButton;
    @FXML
    public TextField searchField;
    @FXML
    public ImageView profilePic;
    @FXML
    public Label noteCounterLabel;
    @FXML
    public ScrollPane noteArea;
    @FXML
    public Label usernameLabel;
    Stage stage;
    User user = SessionManager.getCurrentUser();
    ImageAdder imageAdder = new ImageAdder();
    List<Note> filteredNotes = new ArrayList<>();
    List<Note> notes = new ArrayList<>();
    List<Button> noteButtons = new ArrayList<>();
    Locale locale;
    ResourceBundle rb;
    @FXML
    GridPane noteGrid;
    NoteNodeBuilder noteNodeBuilder = new NoteNodeBuilder();


    @Override
    public void setController(IControllerForGUI controller) {
        //not used in this class
    }

    public void initialize() {
        notes=user.getNotes();
        noteButtons = noteNodeBuilder.build(notes);

        SessionManager.setLanguage(user.getLanguageCode());//set language from user settings stored in the database
        locale = SessionManager.getLocale(); //get language from local storage
        rb = ResourceBundle.getBundle("language", locale);//set bundle for current language
        searchField.setPromptText(rb.getString("searchNote"));
        logoutButton.setText(rb.getString("logout"));
        int noteCount = user.getNotes().size();
        String username = user.getUsername();
        usernameLabel.setText(username);
        String noteLabel = MessageFormat.format(rb.getString("notes"), noteCount);
        noteCounterLabel.setText(noteLabel);
        profilePic.setImage(new Image(user.getProfilePictureUrl()));
        drawNotes();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
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
        }));


    }


    @FXML
    public void logout() {
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
    public void editUser() {
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

        int i = 1;
        int j = 0;
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
            noteGrid.getChildren().clear();
            noteGrid.getChildren().add(notFound);
            return;
        }

        int i = 0;
        int j = 0;
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
        SceneManager.openModal("EditNote.fxml", note);

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


    @Override
    public void setNoteToEdit(Note note) {
        //not used in this class
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private boolean isSubsequence(String search, String text) {
        int searchIndex = 0;
        int textIndex = 0;

        while (textIndex < text.length() && searchIndex < search.length()) {
            if (search.charAt(searchIndex) == text.charAt(textIndex)) {
                searchIndex++;
            }
            textIndex++;
        }

        return searchIndex == search.length(); // Kaikki haetut kirjaimet löytyivät järjestyksessä
    }
}

