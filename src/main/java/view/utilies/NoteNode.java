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

public class NoteNode {
    private final LocalDateTime notificationDate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final BooleanProperty notificationShown;
    Locale locale = SessionManager.getLocale();
    ResourceBundle rb = ResourceBundle.getBundle("language", locale);
    Note note;
    String hidden = "hidden";
    Button noteButton;//this is the button that is shown in the main screen
    private String title;
    private String content;
    private String date;
    private String time;
    private Image noteImage;


    public NoteNode(Note note) {
        this.note = note;
        this.title = note.getTitle();
        this.content = note.getContent();
        this.date = note.getDate().getDayOfMonth() + "." + note.getDate().getMonthValue() + "." + note.getDate().getYear();
        this.time = String.format("%02d:%02d", note.getDate().getHour(), note.getDate().getMinute());
        this.notificationDate = note.getNotificationTime();
        this.noteImage = (note.getImageUrl() == null ? new Image("") : new Image(note.getImageUrl()));
        this.notificationShown = new SimpleBooleanProperty(note.notificationShownProperty());//notification shown observer
        startNotificationChecker();
        noteButton = createNoteNode(); //note button is element that is shown in the main screen

    }

    private void startNotificationChecker() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            if (notificationDate != null && now.equals(notificationDate.truncatedTo(ChronoUnit.MINUTES))) {
                Platform.runLater(() -> notificationShown.set(true));
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public Button createNoteNode() {
        // Luo VBox ja HBox rakenteet
        // noteVbox is content of note
        VBox noteVbox = new VBox();
        // Labelit (date, time, title)
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

        // HBox sisällä oleva Label ja ImageView
        HBox hbox = new HBox();
        hbox.setPrefHeight(170.0);
        hbox.setPrefWidth(170.0);
        ImageView notificationBell = new ImageView(new Image("images/notificationBell.png"));//notification bell image
        notificationBell.getStyleClass().addAll(hidden);//hide notification bell
        notificationBell.setFitHeight(20);
        notificationBell.setFitWidth(20);

        //if notification already shown show the bell when application is restarted.
        if (notificationShown.get()) {
            notificationBell.getStyleClass().remove(hidden);
        }

        //notification bell listener if value changes show the bell
        notificationShown.addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                notificationBell.getStyleClass().remove(hidden);
            }
        });

        //note content preview
        Label noteContent = new Label(content);
        noteContent.setPrefHeight(97.0);
        noteContent.setPrefWidth(109.0);
        noteContent.wrapTextProperty().setValue(true);
        noteContent.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        noteContent.getStyleClass().add("extrasmalltext");

        // if note content is long show only first 50 characters and add "Show more" text
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
        //note image
        ImageView noteImageView = new ImageView();
        noteImageView.setPreserveRatio(true);
        noteImageView.setFitHeight(50.0);
        noteImageView.setFitWidth(50.0);
        noteImageView.setPickOnBounds(true);
        if (noteImage != null) {
            noteImageView.setImage(noteImage);
        }


        hbox.getChildren().addAll(noteContent, noteImageView);
        // Lisää Labelit ja HBox VBoxiin
        noteVbox.getChildren().addAll(noteDate, noteTime, notificationTime, noteTitle, hbox);
        noteVbox.getStyleClass().add("note");

        //vbox is the main container and content of the note node button
        VBox vbox = new VBox();
        vbox.getChildren().addAll(notificationBell, noteVbox);
        Button button = new Button();
        button.getStyleClass().addAll("note");
        button.setGraphic(vbox);
        button.setPrefSize(200, 200);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openFullNoteView(note));
        return button;
    }

    //this is called when noteNode is clicked
    public void openFullNoteView(Note note) {
        SceneManager.openModal("EditNote.fxml", note);

    }

    public Button getNoteButton() {
        return noteButton;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public void setImage(Image image) {
        this.noteImage = image;
    }
}
