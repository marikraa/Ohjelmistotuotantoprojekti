package View;

import Model.Note;
import Model.User;
import javafx.scene.image.Image;

import java.util.List;

public abstract class IControllerForGUI {
    public abstract User login(String username, String password);

    public abstract List<Note> addNote(String username, String title, String content);

    public abstract User signup(String username, String password, Image image);
}
