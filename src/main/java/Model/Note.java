package Model;

import java.sql.Timestamp;



import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime notificationTime;
    @Column(name = "body")
    private String body;
    @Column(name = "image_url")
    private String imageUrl=null;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    private LocalDateTime dateTime;
    public Note(){

    }
    public Note(String title, String body, String imageUrl, LocalDateTime notificationTime) {
        this.title = title;
        this.dateTime =  LocalDateTime.of(LocalDate.now(), LocalTime.now());
        this.imageUrl = imageUrl;
        this.notificationTime = notificationTime;
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

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public String getContent() {
        return body;
    }

    public LocalDateTime getDate() {
        return dateTime;
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


