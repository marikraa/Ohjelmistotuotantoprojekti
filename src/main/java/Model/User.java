package Model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String username;
    private String password;
    List<Note> notes;
    Image profilePicture;
    private Timestamp createdAt;

    public User() {}

    public User(String username, String password, Image image) {
        this.username = username;
        this.password = password;
        profilePicture = image;
        notes = new ArrayList<>();
    }

    public User(Long id, String username, String password, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
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

    public List<Note> getNotes() {
        return notes;

public void addNote (Note note){
        notes.add(note);}


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

    public void setProfilePic(Image image) {
        profilePicture = image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        }

}
