package view.utilies;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Note;
import view.managers.SceneManager;
import view.managers.SessionManager;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a UI component for displaying a note in the application.
 * Handles the creation of a note button with its content and notification logic.
 */
public class NoteNode {
    private final LocalDateTime notificationDate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final BooleanProperty notificationShown;
    private final Locale locale = SessionManager.getLocale();
    private final ResourceBundle rb = ResourceBundle.getBundle("language", locale);
    private final Note note;
    private final String hidden = "hidden";
    private final Button noteButtonElement; // Button displayed on the main screen
    private final String title;
    private final String content;
    private final String date;
    private final String time;
    private final Image noteImage;

    /**
     * Constructs a NoteNode object for the given note.
     * Initializes the note's content, notification logic, and UI button.
     *
     * @param note The note to be represented by this NoteNode.
     */
    public NoteNode(Note note) {
        this.note = note;
        this.title = note.getTitle();
        this.content = note.getBody();
        this.date = note.getDate().getDayOfMonth() + "." + note.getDate().getMonthValue() + "." + note.getDate().getYear();
        this.time = String.format("%02d:%02d", note.getDate().getHour(), note.getDate().getMinute());
        this.notificationDate = note.getNotificationTime();
        this.noteImage = (note.getImageUrl() == null ? new Image("") : new Image(note.getImageUrl()));
        this.notificationShown = new SimpleBooleanProperty(note.notificationShownProperty());
        startNotificationChecker();
        this.noteButtonElement = createNoteNode();
    }

    /**
     * Starts a scheduled task to check for notifications at regular intervals.
     */
    private void startNotificationChecker() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            if (notificationDate != null && !notificationShown.get() && !now.isBefore(notificationDate.truncatedTo(ChronoUnit.MINUTES))) {
                Platform.runLater(() -> notificationShown.set(true));
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    /**
     * Creates a button representing the note with its content and notification logic.
     *
     * @return The created Button object.
     */
    public Button createNoteNode() {
        VBox noteVbox = new VBox();

        Label noteDate = new Label(date);
        noteDate.setFont(new Font(28));
        noteDate.setPadding(new Insets(3, 0, 3, 0));
        noteDate.getStyleClass().addAll("bigtext", "pink");

        Label noteTime = new Label(time);
        String notification = MessageFormat.format(rb.getString("notificationLabel"), notificationDate);
        Label notificationTime = new Label(notification);
        notificationTime.getStyleClass().addAll("extrasmalltext", "orange");
        noteTime.setFont(new Font(18));
        noteTime.getStyleClass().addAll("normaltext", "pink");

        Label noteTitle = new Label(title);
        noteTitle.getStyleClass().addAll("smalltext");

        HBox hbox = new HBox();
        hbox.setPrefHeight(170.0);
        hbox.setPrefWidth(170.0);

        ImageView notificationBell = new ImageView(new Image("images/notificationBell.png"));
        notificationBell.getStyleClass().addAll(hidden);
        notificationBell.setFitHeight(20);
        notificationBell.setFitWidth(20);

        if (notificationShown.get()) {
            notificationBell.getStyleClass().remove(hidden);
        }

        notificationShown.addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                notificationBell.getStyleClass().remove(hidden);
            }
        });

        Label noteContent = new Label(content);
        noteContent.setPrefHeight(97.0);
        noteContent.setPrefWidth(109.0);
        noteContent.wrapTextProperty().setValue(true);
        noteContent.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        noteContent.getStyleClass().add("extrasmalltext");

        if (noteContent.getText().length() > 50) {
            TextFlow textFlow = new TextFlow();
            Text truncatedText = new Text(noteContent.getText().substring(0, 50));
            textFlow.getChildren().add(truncatedText);
            truncatedText.setFill(Color.rgb(164, 164, 164));

            Text showMoreText = new Text("... Show more");
            textFlow.getChildren().add(showMoreText);
            showMoreText.setFill(Color.rgb(107, 106, 106));

            noteContent.setText("");
            noteContent.setGraphic(textFlow);
        }

        ImageView noteImageView = new ImageView();
        noteImageView.setPreserveRatio(true);
        noteImageView.setFitHeight(50.0);
        noteImageView.setFitWidth(50.0);
        noteImageView.setPickOnBounds(true);
        if (noteImage != null) {
            noteImageView.setImage(noteImage);
        }

        hbox.getChildren().addAll(noteContent, noteImageView);
        noteVbox.getChildren().addAll(noteDate, noteTime, notificationTime, noteTitle, hbox);
        noteVbox.getStyleClass().add("note");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(notificationBell, noteVbox);

        Button button = new Button();
        button.getStyleClass().addAll("note");
        button.setGraphic(vbox);
        button.setPrefSize(200, 200);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openFullNoteView(note));
        return button;
    }

    /**
     * Opens the full view of the note when the note button is clicked.
     *
     * @param note The note to be displayed in full view.
     */
    public void openFullNoteView(Note note) {
        SceneManager.openModal("EditNote.fxml", note);
    }

    /**
     * Gets the button element representing the note.
     *
     * @return The Button object.
     */
    public Button getNoteButtonElement() {
        return noteButtonElement;
    }

    /**
     * Gets the title of the note.
     *
     * @return The title of the note.
     */
    public String getTitle() {
        return title;
    }
}