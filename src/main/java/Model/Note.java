package Model;

import java.sql.Timestamp;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "notes")
import java.time.LocalTime;

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String time;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "title")
    private String title;
    private String content;
    private final String date;
    private final Image image;
    private String dueDate;
    @Column(name = "body")
    private String body;

    public Note(String title, String content, Image image, String dueDate) {
        @Column(name = "image_url")
        private String imageUrl;

        @Column(name = "updated_at")
        private Timestamp updatedAt;
        this.title = title;
        this.content = content;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().toString();
        this.image = image;
        this.dueDate = dueDate;


        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }
}

public void setUser(User user) {
    this.user = user;
}
}
