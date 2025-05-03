package model;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user entity in the database.
 * Each user can have multiple associated notes and contains details such as username, password, profile picture, and language preference.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "language_code")
    private String languageCode;

    /**
     * Default constructor for JPA.
     * Initializes the notes list.
     */
    public User() {
        this.notes = new ArrayList<>();
    }

    /**
     * Constructs a new user with the specified details.
     *
     * @param username        The username of the user.
     * @param password        The password of the user.
     * @param imageurl        The URL of the user's profile picture.
     * @param languageCode    The preferred language code of the user.
     */
    public User(String username, String password, String imageurl, String languageCode) {
        this.username = username;
        this.password = password;
        this.profilePictureUrl = imageurl;
        this.languageCode = languageCode;
        this.notes = new ArrayList<>();
    }

    /**
     * Gets the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the profile picture of the user as a JavaFX Image.
     *
     * @return The profile picture of the user.
     */
    public Image getProfilePicture() {
        return new Image(profilePictureUrl);
    }

    /**
     * Sets the profile picture of the user using a JavaFX Image.
     *
     * @param image The profile picture to set.
     */
    public void setProfilePicture(Image image) {
        this.profilePictureUrl = image.getUrl();
    }

    /**
     * Gets the URL of the user's profile picture.
     *
     * @return The profile picture URL.
     */
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    /**
     * Sets the URL of the user's profile picture.
     *
     * @param profilePictureUrl The profile picture URL to set.
     */
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    /**
     * Gets the preferred language code of the user.
     *
     * @return The language code.
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the preferred language code of the user.
     *
     * @param languageCode The language code to set.
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Gets the list of notes associated with the user.
     *
     * @return The list of notes.
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Adds a note to the user's list of notes.
     *
     * @param note The note to add.
     */
    public void addNote(Note note) {
        notes.add(note);
        note.setUser(this);
    }

    /**
     * Removes a note from the user's list of notes.
     *
     * @param note The note to remove.
     */
    public void removeNote(Note note) {
        notes.remove(note);
        note.setUser(null);
    }

    /**
     * Sets the list of notes associated with the user.
     *
     * @param notes The list of notes to set.
     */
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    /**
     * Filters and sorts the user's notes by title.
     *
     * @param title The title to filter notes by.
     * @return A list of notes with the specified title.
     */
    public List<Note> sortNotes(String title) {
        return notes.stream()
                .filter(note -> note.getTitle().equalsIgnoreCase(title))
                .toList();
    }
}