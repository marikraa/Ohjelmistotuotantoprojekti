package Model;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;



    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    public User() {
        this.notes = new ArrayList<>();
    }

    public User(String username, String password, String imageurl) {
        this.username = username;
        this.password = password;
        this.profilePictureUrl = imageurl;
        this.notes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Image getProfilePicture() {
        Image profilePicture = new Image(profilePictureUrl);
        return profilePicture;
    }

    public void setProfilePicture(Image image) {
        this.profilePictureUrl = image.getUrl();
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
        note.setUser(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setUser(null);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
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
}