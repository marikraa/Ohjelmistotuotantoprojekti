package View;

import Model.Note;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NoteNode {
    private Note note;
    private String title;
    private String content;
    private String date;
    private String time;
    private String dueDate;
    private Image noteImage;

    public NoteNode(Note note) {
        this.note = note;
        this.title = note.getTitle();
        this.content = note.getContent();
        this.date = note.getDate();
        this.time = note.getTime();
        this.dueDate = note.getDueDate();
        this.noteImage = note.getImage();

    }

    public VBox createNoteNode() {
        // Luo VBox ja HBox rakenteet
        VBox vbox = new VBox();
        vbox.setPrefHeight(201.0);
        vbox.setPrefWidth(201.0);

        // Labelit (date, time, title)
        Label noteDate = new Label(date);
        noteDate.setFont(new Font(28));
        noteDate.setPadding(new Insets(3, 0, 3, 0));

        Label noteTime = new Label(time);
        noteTime.setFont(new Font(18));
        noteTime.setPadding(new Insets(3, 0, 10, 0));

        Label noteTitle = new Label(title);

        // HBox sisällä oleva Label ja ImageView
        HBox hbox = new HBox();
        hbox.setPrefHeight(185.0);
        hbox.setPrefWidth(186.0);

        Label noteContent = new Label(content);
        noteContent.setPrefHeight(97.0);
        noteContent.setPrefWidth(109.0);
        noteContent.setAlignment(javafx.geometry.Pos.TOP_LEFT);

        ImageView noteImageView = new ImageView();
        noteImageView.setFitHeight(96.0);
        noteImageView.setFitWidth(77.0);
        noteImageView.setPickOnBounds(true);
        noteImageView.setPreserveRatio(true);
        noteImageView.setImage(noteImage);

        hbox.getChildren().addAll(noteContent, noteImageView);

        // Lisää Labelit ja HBox VBoxiin
        vbox.getChildren().addAll(noteDate, noteTime, noteTitle, hbox);
        vbox.getStyleClass().add("widget");

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

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setImage(Image image) {
        this.noteImage = image;
    }
}
