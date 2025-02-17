package Model;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.LocalTime;

public class Note {

    private final String time;
    private String title;
    private String content;
    private final String date;
    private final Image image;
    private String dueDate;
    private static int nextId = 0;
    private final int id;

    public Note(String title, String content, Image image, String dueDate) {
        this.title = title;
        this.content = content;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().toString();
        this.image = image;
        this.dueDate = dueDate;
        this.id = nextId;
        nextId++;
    }


    public String getTitle() {
        return title;
    }

    public Image getImage() {
        return image;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }
}
