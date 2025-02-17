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
    User currentUserTEST;
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
    private User currentUserTEST;
    List<String> notes;

    @Override
    public User login(String username, String password) {

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                currentUserTEST = user;
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Note> addNote(String username,String title, String content,Image image, String dueDate) {

        //TODO: imagen uppaaminen johonkin palvelimelle ja sen urlin tallentaminen tietokantaan alempi on testiä varten että saa locaalisti toimimaan
        String imageUrl = null;
        if(image!=null){
            imageUrl = image.getUrl();
        }
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                Note note = new Note(title, content, imageUrl, dueDate);
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
        try {
        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null) {
            return null;
        }
        User user = new User(username, password, image);

            userDAO.createUser(user);
            currentUserTEST = user;
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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