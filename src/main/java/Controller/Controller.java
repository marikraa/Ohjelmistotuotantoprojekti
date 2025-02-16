package Controller;

import Model.Note;
import Model.User;
import DataSource.UserDAO;

import View.IControllerForGUI;
import javafx.scene.image.Image;
import java.util.*;
import java.sql.*;

public class Controller implements IControllerForGUI {
    static Controller controller;
    UserDAO userDAO;

    private Controller() {
        userDAO = new UserDAO();

    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }
    //TODO nämä 2 poistetaan ku on koodi valmis
    private User currentUser;
    List<String> notes;

    @Override
    public User login(String username, String password) {

        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {

            //TODO current user poistetaan sit ku on koodi valmis ei tarvii???? Mitä ei tarvii t. Liukkari
            currentUser = user;
            return user;
        }


        return null;
    }

    @Override
    public List<Note> addNote(String username, String title, String content) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                Note note = new Note(title, content);
                note.setUser(user);
                user.addNote(note);
                userDAO.updateUser(user);
                return user.getNotes();
            } else {
                System.out.println("User not found: " + username);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public User signup(String username, String password, Image image) {
        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null) {
            return null;
        }
        User user = new User(username, password, image);
        userDAO.createUser(user);
        currentUser = user;
        return user;
    }
//Updates user information to database
    @Override
    public Boolean updateUser(String oldUsername, String newUsername, String password, Image profilePicture) {
        Boolean success = true;
        try {
            User user = userDAO.getUserByUsername(oldUsername);
            if (user != null) {
                user.setUsername(newUsername);
                user.setPassword(password);
                user.setProfilePicture(profilePicture);
                userDAO.updateUser(user);
            } else {
                System.out.println("User not found: " + oldUsername);
                success = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    public Boolean deleteUser(User user) {
        try {
            userDAO.deleteUser(user.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}