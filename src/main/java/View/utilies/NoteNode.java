package View.utilies;

import Model.Note;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NoteNode {
    private Note note;
    private String title;
    private String content;
    private String date;
    private String time;
    private LocalDateTime notificationDate;
    private Image noteImage;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    HBox notificationHbox;
    private BooleanProperty notificationShown;
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

    }

    private void startNotificationChecker() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            if (notificationDate != null && now.equals(notificationDate.truncatedTo(ChronoUnit.MINUTES))) {
                Platform.runLater(() -> {
                    notificationShown.set(true);
                });
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    public VBox createNoteNode() {
        // Luo VBox ja HBox rakenteet
        VBox noteVbox = new VBox();


        // Labelit (date, time, title)
        Label noteDate = new Label(date);
        noteDate.setFont(new Font(28));
        noteDate.setPadding(new Insets(3, 0, 3, 0));
        noteDate.getStyleClass().addAll("bigtext", "pink");

        Label noteTime = new Label(time);
        Label notificationTime = new Label("Notification: " + notificationDate);
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
        notificationBell.getStyleClass().addAll("hidden");//hide notification bell
        notificationBell.setFitHeight(20);
        notificationBell.setFitWidth(20);

        //if notifcation allready shown show the bell when applicastion is restarted
        if(notificationShown.get()){
            notificationBell.getStyleClass().remove("hidden");
            System.out.println("notification shown already");
        }

        //notification bell listener if value changes show the bell
        notificationShown.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                //notificationHbox.getStyleClass().remove("hidden");
                notificationBell.getStyleClass().remove("hidden");
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

        // Lisää Labelit ja HBox VBoxiin
        noteVbox.getChildren().addAll(noteDate, noteTime, notificationTime, noteTitle, hbox);

        noteVbox.getStyleClass().add("note");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(notificationBell,noteVbox);


        // Luo Button ja lisää siihen VBox
        return vbox;
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

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public void setImage(Image image) {
        this.noteImage = image;
    }
}
