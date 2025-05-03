package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.*;

/**
 * Represents a note entity in the database.
 * Each note is associated with a user and contains details such as title, body, image URL, and notification settings.
 */
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime dateTime;

    @Column(name = "notification_time")
    private LocalDateTime notificationTime;

    @Column(name = "notification_shown")
    private Boolean notificationShown;

    /**
     * Default constructor for JPA.
     */
    public Note() {
    }

    /**
     * Constructs a new note with the specified details.
     *
     * @param title             The title of the note.
     * @param body              The body content of the note.
     * @param imageUrl          The URL of the associated image.
     * @param notificationTime  The time for the notification to be triggered.
     */
    public Note(String title, String body, String imageUrl, LocalDateTime notificationTime) {
        this.title = title;
        this.dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        this.imageUrl = imageUrl;
        this.notificationTime = notificationTime;
        this.body = body;
        this.notificationShown = false;
    }

    /**
     * Gets the ID of the note.
     *
     * @return The ID of the note.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the note.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user associated with the note.
     *
     * @return The associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the note.
     *
     * @param user The user to associate.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the title of the note.
     *
     * @return The title of the note.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the note.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the body content of the note.
     *
     * @return The body content of the note.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body content of the note.
     *
     * @param body The body content to set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the URL of the associated image.
     *
     * @return The image URL.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the associated image.
     *
     * @param imageUrl The image URL to set.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the creation date and time of the note.
     *
     * @return The creation date and time.
     */
    public LocalDateTime getDate() {
        return dateTime;
    }

    /**
     * Sets the creation date and time of the note.
     *
     * @param dateTime The date and time to set.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the notification time for the note.
     *
     * @return The notification time.
     */
    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    /**
     * Sets the notification time for the note.
     *
     * @param notificationTime The notification time to set.
     */
    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    /**
     * Gets the notification shown status.
     *
     * @return `true` if the notification has been shown, `false` otherwise.
     */
    public Boolean notificationShownProperty() {
        return notificationShown;
    }

    /**
     * Sets the notification shown status.
     *
     * @param notificationShown The status to set.
     */
    public void setNotificationShown(Boolean notificationShown) {
        this.notificationShown = notificationShown;
    }
}