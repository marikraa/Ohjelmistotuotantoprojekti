package View.utilies;

import Model.Note;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDateTime;

public class NoteNode {
    private Note note;
    private String title;
    private String content;
    private String date;
    private String time;
    private LocalDateTime notificationDate;
    private Image noteImage;

    public NoteNode(Note note) {
        this.note = note;
        this.title = note.getTitle();
        this.content = note.getContent();
        this.date = note.getDate().getDayOfMonth()+"."+note.getDate().getMonthValue()+"."+note.getDate().getYear();
        this.time = note.getDate().getHour()+":"+note.getDate().getMinute();
        this.notificationDate = note.getNotificationTime();
        this.noteImage = (note.getImageUrl() == null ? new Image("") : new Image(note.getImageUrl()));

    }

    public VBox createNoteNode() {
        // Luo VBox ja HBox rakenteet
        VBox vbox = new VBox();



        // Labelit (date, time, title)
        Label noteDate = new Label(date);
        noteDate.setFont(new Font(28));
        noteDate.setPadding(new Insets(3, 0, 3, 0));
        noteDate.getStyleClass().addAll("bigtext","pink");

        Label noteTime = new Label(time);
        noteTime.setFont(new Font(18));

        noteTime.getStyleClass().addAll("normaltext", "pink");

        Label noteTitle = new Label(title);
        noteTitle.getStyleClass().addAll("bigtext");

        // HBox sisällä oleva Label ja ImageView
        HBox hbox = new HBox();
        hbox.setPrefHeight(170.0);
        hbox.setPrefWidth(170.0);


        Label noteContent = new Label(content);
        noteContent.setPrefHeight(97.0);
        noteContent.setPrefWidth(109.0);

        noteContent.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        noteContent.getStyleClass().add("smalltext");

        ImageView noteImageView = new ImageView();
        noteImageView.setPreserveRatio(true);
        noteImageView.setFitHeight(96.0);
        noteImageView.setFitWidth(77.0);
        noteImageView.setPickOnBounds(true);
        if(noteImage!=null){
            noteImageView.setImage(noteImage);
        }


        hbox.getChildren().addAll(noteContent, noteImageView);

        // Lisää Labelit ja HBox VBoxiin
        vbox.getChildren().addAll(noteDate, noteTime, noteTitle, hbox);

        vbox.getStyleClass().add("note");


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
