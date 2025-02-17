package View;

import Model.Note;
import Model.User;
import javafx.scene.image.Image;

import java.util.List;

public interface IControllerForGUI {
    User login(String username, String password);

    List<Note> addNote(String username, String title, String content,Image image, String dueDate);

    User signup(String username, String password, Image image);

    Boolean updateUser(String oldUsername,String newUsername, String password, Image profilePicture);

    Boolean deleteUser(User user);
}
