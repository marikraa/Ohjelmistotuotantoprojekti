package view;

import javafx.scene.image.Image;
import model.Note;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IControllerForGUI {
    User login(String username, String password);

    List<Note> addNote(String username, String title, String content, Image image, LocalDateTime notificationTime);

    User signup(String username, String password, Image image, String languageCode);

    boolean updateUser(String oldUsername, String newUsername, String password, Image profilePicture, String languageCode);

    boolean deleteUser(User user);

    boolean updateNote(Note currentNote);

    boolean deleteNote(Note note);
}
