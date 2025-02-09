package View;

import Model.Note;
import Model.User;
import javafx.scene.image.Image;

import java.util.List;

public interface IControllerForGUI {
    User login(String username, String password);

    List<Note> addNote(String username, String title, String content);

    User signup(String username, String password, Image image);
}
