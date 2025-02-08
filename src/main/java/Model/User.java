package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String username;
    private String password;
    private List<Note> notes;
    private Image profilePicture;
    private String profilePictureUrl;
    private Timestamp createdAt;

    public User() {
        this.notes = new ArrayList<>();
    }

    public User(String username, String password, Image image) {
        this.username = username;
        this.password = password;
        this.profilePicture = image;
        this.notes = new ArrayList<>();
    }

    public User(Long id, String username, String password, String profilePictureUrl, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
        this.createdAt = createdAt;
        this.notes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> sortNotes(String title) {
        List<Note> sortedNotes = new ArrayList<>();
        for (Note note : notes) {
            if (note.getTitle().equals(title)) {
                sortedNotes.add(note);
            }
        }
        return sortedNotes;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image image) {
        this.profilePicture = image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}