package FrontEnd;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    List<Note> notes;
    Image profilePicture;

    public User(String username, String password, List<Note> notes) {
        this.username = username;
        this.password = password;
        this.notes = notes;
        profilePicture = new Image(Objects.requireNonNull(getClass().getResource("images/defaultProfilePic.png")).toExternalForm());

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

    public void setProfilePic(Image image) {
        profilePicture = image;
    }
    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setUsername(String text) {
        username = text;
    }

    public void setPassword(String text) {
        password = text;
    }
}
