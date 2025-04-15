package view.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Note;
import model.User;
import view.IControllerForGUI;
import view.managers.SceneManager;
import view.managers.SessionManager;
import view.utilies.NoteNode;
import view.utilies.NoteNodeBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * This class represents the main screen controller of the application.
 * It handles the user interface and user interactions in main view
 */
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
    List<NoteNode> filteredNoteNodes = new ArrayList<>();
    List<Note> notes = new ArrayList<>();
    List<NoteNode> noteNodes = new ArrayList<>();
    Locale locale;
    ResourceBundle rb;
    @FXML
    GridPane noteGrid;
    NoteNodeBuilder noteNodeBuilder = new NoteNodeBuilder();
    Button addNoteButton;


    @Override
    public void setController(IControllerForGUI controller) {
        //not used in this class
    }

    public void initialize() {
        SessionManager.setLanguage(user.getLanguageCode());//set language from user settings stored in the database
        locale = SessionManager.getLocale(); //get language from local storage
        rb = ResourceBundle.getBundle("language", locale);//set bundle for current language
        notes = user.getNotes();
        noteNodes = noteNodeBuilder.build(notes);
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
                filteredNoteNodes.clear();
                for (NoteNode note : noteNodes) {
                    String title = note.getTitle().toLowerCase();
                    if (isSubsequence(search, title)) { // Tarkistetaan järjestys
                        filteredNoteNodes.add(note);
                    }
                }

                drawFilteredNotes(filteredNoteNodes);
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

    /**
     *  This function draws notes to grid
     */
    public void drawNotes() {
        if (noteGrid == null) {
            noteGrid = new GridPane(10, 10);
            noteGrid.setPadding(new javafx.geometry.Insets(10, 0, 10, 0));
            noteArea.setContent(noteGrid);
        } else {
            noteGrid.getChildren().clear(); // Tyhjennä vanha sisältö
        }

        if(addNoteButton == null) {addNoteButton = createAddButton();

        }


        noteGrid.add(addNoteButton, 0, 0);
        int i = 1;
        int j = 0;
        for (NoteNode note : noteNodes) {
            noteGrid.add(note.getNoteButton(), i, j);
            i++;
            if (i > 1) {
                i = 0;
                j++;
            }
        }
    }

    /**
     * This filters notes by searched word nad displays them on screen
     * @param filteredNotes are filtered notes by searched name
     */

    //this method is used to draw the notes that match the search query
    private void drawFilteredNotes(List<NoteNode> filteredNotes) {
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
        for (NoteNode note : filteredNotes) {
            noteGrid.add(note.getNoteButton(), i, j);
            i++;
            if (i > 1) {
                i = 0;
                j++;
            }
        }
    }

    /** This function creates Add button for note grid
     *
     * @return button element for adding a new note
     */
    public Button createAddButton() {
        // Create button for adding a new note
        Button addButton = new Button("+");
        addButton.setId("addNoteButton");
        addButton.setPrefSize(200.0, 200.0);
        addButton.setMnemonicParsing(false);

        // add style classes to the button
        addButton.getStyleClass().addAll("note", "addNote");

        // Set button action
        addButton.setOnMouseClicked(event -> addNote());
        //add add note button to the grid
        return addButton;
    }


    @Override
    public void setNoteToEdit(Note note) {
        //not used in this class
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Checks if the given search string is a subsequence of the provided text.
     * A subsequence means all characters of the search string appear in the text in the same order,
     * but not necessarily consecutively.
     *
     * @param search the string to search for as a subsequence
     * @param text the text in which to search
     * @return true if {@code search} is a subsequence of {@code text}, false otherwise
     */
    //this method is used to check if the search string is a subsequence of the note title
    private boolean isSubsequence(String search, String text) {
        int searchIndex = 0;
        int textIndex = 0;

        while (textIndex < text.length() && searchIndex < search.length()) {
            if (search.charAt(searchIndex) == text.charAt(textIndex)) {
                searchIndex++;
            }
            textIndex++;
        }

        return searchIndex == search.length(); // Every character in search was found in text
    }
}

