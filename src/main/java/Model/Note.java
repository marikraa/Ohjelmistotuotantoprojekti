package Model;

import java.sql.Timestamp;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //private final String time;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "title")
    private String title;

    //private final String date;
    //private final Image image;
    private String dueDate;
    @Column(name = "body")
    private String body;
    @Column(name = "image_url")
    private String imageUrl=null;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    private String date;
    private String time;
    public Note(){

    }
    public Note(String title, String body, String imageUrl, String dueDate) {
        this.title = title;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().toString();
        this.imageUrl = imageUrl;
        this.dueDate = dueDate;
        this.body = body;
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

    public String getImage() {
        return imageUrl;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getContent() {
        return body;
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


