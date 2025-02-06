package FrontEnd;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    List<Note> notes;
    Image profilePicture;


    public User(String username, String password, List<Note> notes) {
        this.username = username;
        this.password = password;
        this.notes = notes;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Note> getNotes() {
        return notes;
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

    public void setProfilePicture(Image image) {
        profilePicture = image;
    }
    public Image getProfilePicture() {
        return profilePicture;
    }
}
