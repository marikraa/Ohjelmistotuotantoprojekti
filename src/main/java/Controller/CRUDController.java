package Controller;

import Model.Note;
import Model.User;
import DataSource.UserDAO;

import javafx.scene.image.Image;
import java.util.*;
import java.sql.*;

public class CRUDController {
    static CRUDController crudController;
    UserDAO userDAO;

    private CRUDController() {
        userDAO = new UserDAO();
    }

    public static CRUDController getInstance() {
        if (crudController == null) {
            crudController = new CRUDController();
        }
        return crudController;
    }

    private User currentUser;
    List<String> notes;

    public User login(String username, String password) {

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public boolean logOut(String username) {
        if (currentUser != null && currentUser.getUsername().equals(username)) {
            currentUser = null;
            // tässä sit pitäs vaihtaa login fxml sivuun
            return true;
        }
        return false;
    }

    /*
    public boolean signup() {
        // kuvan uppaus servulle??
    public boolean signUp(String username, String password, Image profileImage) {
        //tähän post pyyntö
        Gson gson = new Gson();
        String json = gson.toJson(user);

        // Luodaan HTTP POST -pyyntö
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.com/api/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        boolean success = false;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            userDAO.createUser(user);
            success = true;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }

        // Lähetetään pyyntö ja saadaan vastaus
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Tulostetaan palvelimen vastaus
        System.out.println("Response: " + response.body());

        // toisessa päässä gson.fromJson(json, User.class); jolloin muuttaa jsonin User olioksi
        User user = new User("username", "password", null);
        return success;
    }
    */

    public List<Note> addNote(Note note) {
        // lisää uuden noten userilla ja palauttaa listan kaikista noteista
        // pitää jotenkin tarkastaa että menee oikeelle userille notet ja palauttaa oikeen userin notet
        // tähän post pyyntö
        // palauttaa listan kaikista noteista
        if (currentUser != null) {
            currentUser.addNote(note);
            return currentUser.getNotes();
        }
        return Collections.emptyList();
    }

    public User signup(String username, String password, Image image) {
        // backEndCreateUser(username, password, image); tämä palauttaa user olion

        // testiä varten tämä
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
}