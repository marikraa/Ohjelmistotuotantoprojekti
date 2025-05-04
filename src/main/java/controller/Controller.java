package controller;

import datasource.ImageHandling;
import datasource.NoteDAO;
import datasource.UserDAO;
import model.Note;
import model.User;
import javafx.scene.image.Image;
import view.IControllerForView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class that implements the IControllerForView interface.
 * This class acts as a bridge between the view and the data access layer, handling user and note operations.
 */
public class Controller implements IControllerForView {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private UserDAO userDAO;
    private NoteDAO noteDAO;
    private ImageHandling imageHandling;

    /**
     * Constructor that initializes the DAOs and image handling utility.
     */
    public Controller() {
        userDAO = new UserDAO();
        noteDAO = new NoteDAO();
        imageHandling = new ImageHandling();
    }
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setNoteDAO(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    public void setImageHandling(ImageHandling imageHandling) {
        this.imageHandling = imageHandling;
    }

    /**
     * Logs in a user by verifying their username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if login is successful, or null if login fails.
     */
    @Override
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Signs up a new user with the provided details.
     *
     * @param username     The username of the new user.
     * @param password     The password of the new user.
     * @param image        The profile picture of the new user.
     * @param languageCode The preferred language code of the new user.
     * @return The created User object, or null if signup fails.
     */
    @Override
    public User signup(String username, String password, Image image, String languageCode) {
        String imageUrl = "";
        if (image != null) {
            try {
                String imageJSON = imageHandling.uploadImage(image);
                imageUrl = imageHandling.parseImageUrl(imageJSON);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error uploading or parsing image during signup", e);
            }
        }

        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null) {
            return null;
        }
        User user = new User(username, password, imageUrl, languageCode);
        if (!userDAO.createUser(user)) {
            return null;
        }
        return user;
    }

    /**
     * Adds a new note for a user.
     *
     * @param username         The username of the user.
     * @param title            The title of the note.
     * @param content          The content of the note.
     * @param image            The image associated with the note.
     * @param notificationTime The notification time for the note.
     * @return A list of the user's notes after adding the new note, or an empty list if the operation fails.
     */
    @Override
    public List<Note> addNote(String username, String title, String content, Image image, LocalDateTime notificationTime) {
        String imageUrl = "";
        if (image != null) {
            imageUrl = image.getUrl();
            try {
                String imageJSON = imageHandling.uploadImage(image);
                imageUrl = imageHandling.parseImageUrl(imageJSON);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error uploading or parsing image during addNote", e);
            }
        }

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                Note note = new Note(title, content, imageUrl, notificationTime);
                note.setUser(user);
                user.addNote(note);
                if (!userDAO.updateUser(user)) {
                    return Collections.emptyList();
                }
                return user.getNotes();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding note", e);
            return Collections.emptyList();
        }
    }

    /**
     * Updates a user's details.
     *
     * @param oldUsername  The current username of the user.
     * @param newUsername  The new username of the user.
     * @param password     The new password of the user.
     * @param image        The new profile picture of the user.
     * @param languageCode The new preferred language code of the user.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean updateUser(String oldUsername, String newUsername, String password, Image image, String languageCode) {
        String imageUrl = "";
        if (image != null) {
            imageUrl = image.getUrl();
            try {
                String imageJSON = imageHandling.uploadImage(image);
                imageUrl = imageHandling.parseImageUrl(imageJSON);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error uploading or parsing image during updateUser", e);
            }
        }

        try {
            User user = userDAO.getUserByUsername(oldUsername);
            if (user != null) {
                user.setUsername(newUsername);
                user.setPassword(password);
                user.setProfilePictureUrl(imageUrl);
                user.setLanguageCode(languageCode);
                return userDAO.updateUser(user);
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating user", e);
            return false;
        }
    }

    /**
     * Deletes a user from the system.
     *
     * @param user The User object to be deleted.
     * @return true if the deletion is successful, false otherwise.
     */
    @Override
    public boolean deleteUser(User user) {
        try {
            return userDAO.deleteUser(user.getId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
            return false;
        }
    }

    /**
     * Updates an existing note.
     *
     * @param currentNote The Note object with updated details.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean updateNote(Note currentNote) {
        try {
            Note existingNote = noteDAO.getNoteById(currentNote.getId());
            if (existingNote != null) {
                existingNote.setTitle(currentNote.getTitle());
                existingNote.setBody(currentNote.getBody());
                existingNote.setImageUrl(currentNote.getImageUrl());
                existingNote.setNotificationTime(currentNote.getNotificationTime());
                return noteDAO.updateNote(existingNote);
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating note", e);
            return false;
        }
    }

    /**
     * Deletes a note from the system.
     *
     * @param note The Note object to be deleted.
     * @return true if the deletion is successful, false otherwise.
     */
    @Override
    public boolean deleteNote(Note note) {
        try {
            return noteDAO.deleteNote(note.getId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting note", e);
            return false;
        }
    }
}