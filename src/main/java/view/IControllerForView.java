package view;

import javafx.scene.image.Image;
import model.Note;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface defining the contract for the controller to interact with the view.
 * Provides methods for user authentication, user management, and note management.
 */
public interface IControllerForView {

    /**
     * Logs in a user by verifying their username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if login is successful, or null if login fails.
     */
    User login(String username, String password);

    /**
     * Adds a new note for a user.
     *
     * @param username         The username of the user.
     * @param title            The title of the note.
     * @param content          The content of the note.
     * @param image            The image associated with the note.
     * @param notificationTime The notification time for the note.
     * @return A list of the user's notes after adding the new note.
     */
    List<Note> addNote(String username, String title, String content, Image image, LocalDateTime notificationTime);

    /**
     * Signs up a new user with the provided details.
     *
     * @param username     The username of the new user.
     * @param password     The password of the new user.
     * @param image        The profile picture of the new user.
     * @param languageCode The preferred language code of the new user.
     * @return The created User object, or null if signup fails.
     */
    User signup(String username, String password, Image image, String languageCode);

    /**
     * Updates a user's details.
     *
     * @param oldUsername  The current username of the user.
     * @param newUsername  The new username of the user.
     * @param password     The new password of the user.
     * @param profilePicture The new profile picture of the user.
     * @param languageCode The new preferred language code of the user.
     * @return true if the update is successful, false otherwise.
     */
    boolean updateUser(String oldUsername, String newUsername, String password, Image profilePicture, String languageCode);

    /**
     * Deletes a user from the system.
     *
     * @param user The User object to be deleted.
     * @return true if the deletion is successful, false otherwise.
     */
    boolean deleteUser(User user);

    /**
     * Updates an existing note.
     *
     * @param currentNote The Note object with updated details.
     * @return true if the update is successful, false otherwise.
     */
    boolean updateNote(Note currentNote);

    /**
     * Deletes a note from the system.
     *
     * @param note The Note object to be deleted.
     * @return true if the deletion is successful, false otherwise.
     */
    boolean deleteNote(Note note);
}