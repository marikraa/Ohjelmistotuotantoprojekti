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

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {

                //TODO current user poistetaan sit ku on koodi valmis ei tarvii
                currentUser = user;
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<Note> addNote(String username,String title, String content) {
        // lisää uuden noten userille tietokantaan ja palauttaa listan kaikista noteista tietokannasta
        // pitää jotenkin tarkastaa että menee oikeelle userille notet ja palauttaa oikeen userin note
        // palauttaa listan kaikista noteista
        //tee uusi note userille jonka username on username
        //TODO tähän pitäs lisätä noten lisäys tietokantaan tämä on tällä hetkellä testi
        Note note = new Note(title, content);
            currentUser.addNote(note);
            return currentUser.getNotes();

        //return Collections.emptyList();
    }
    @Override
    public User signup(String username, String password, Image image) {

        //TODO TÄHÄN CHECK ETTÄ JOS USER ON KÄYTÖSSÄ PALAUTA NULL. NYT PÄÄSTÄÄ LÄPI SAMALLA NIMELLÄ
        User user = new User(username, password, image);
        try {
            userDAO.createUser(user);
            currentUser = user;
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//Updates user information to database
    @Override
    public Boolean updateUser(String oldUsername,String newUsername, String password, Image profilePicture) {
        Boolean success = true;

        //TODO päivitä uuseri tietokantaan

        return success;
    }

    @Override
    public Boolean deleteUser(User user) {
        //TODO poista käyttäjä tietokannasta
        Boolean success = true;
        return success;
    }
}