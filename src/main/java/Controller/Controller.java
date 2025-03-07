package Controller;

import DataSource.NoteDAO;
import Model.Note;
import Model.User;
import DataSource.UserDAO;

import View.IControllerForGUI;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.*;

public class Controller implements IControllerForGUI {
    //static Controller controller;
    UserDAO userDAO;
    NoteDAO noteDAO;

    public Controller() {
        userDAO = new UserDAO();
        noteDAO = new NoteDAO();
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
    public boolean updateUser(String oldUsername, String newUsername, String password, Image profilePicture) {
        try {
            User user = userDAO.getUserByUsername(oldUsername);
            if (user != null) {
                user.setUsername(newUsername);
                user.setPassword(password);
                user.setProfilePicture(profilePicture);
                userDAO.updateUser(user);
                return true;
            } else {
                System.out.println("User not found: " + oldUsername);
                 return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
             return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
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
        try {
            Note existingNote = noteDAO.getNoteById(currentNote.getId());
            if (existingNote != null) {
                existingNote.setTitle(currentNote.getTitle());
                existingNote.setBody(currentNote.getBody());
                existingNote.setImageUrl(currentNote.getImageUrl());
                existingNote.setNotificationTime(currentNote.getNotificationTime());
                noteDAO.updateNote(existingNote);
                return true;
            } else {
                System.out.println("Note not found: " + currentNote.getId());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteNote(Note note) {
        try {
            return noteDAO.deleteNote(note.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}