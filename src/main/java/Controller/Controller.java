package Controller;

import Model.Note;
import Model.User;
import DataSource.UserDAO;

import View.IControllerForGUI;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.sql.*;

public class Controller implements IControllerForGUI {
    //static Controller controller;
    UserDAO userDAO;
    public Controller() {
        userDAO = new UserDAO();

    }

    @Override
    public User login(String username, String password) {

        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println(user.getUsername());
            return user;
        }
        return null;
    }

    @Override
    public List<Note> addNote(String username, String title, String content, Image image, LocalDateTime notificationTime) {
        //TODO: imagen uppaaminen johonkin palvelimelle ja sen urlin tallentaminen tietokantaan alempi on testiä varten että saa locaalisti toimimaan
        String imageUrl = "";
        if(image!=null){
            imageUrl = image.getUrl();
        }


        try {
            User user = userDAO.getUserByUsername(username);
                if (user != null) {
                    Note note = new Note(title, content, imageUrl, notificationTime);
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

    @Override
    public boolean updateNote(Note currentNote) {
        //TODO: implement note update päivittä
       // jos update onnistuu palauta true, muuten false
        return false;
    }
}