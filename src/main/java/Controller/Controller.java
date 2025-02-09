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
    //TODO tätä currentUser ei pakosti tarvita. Frontissa on tieto kirjautuneesta käyttäjästä
    // tänne tulee vaan pyynnöt. Helpottaa jatkoa jos haluaa tehdä HTTP tyyliin
    private User currentUser;
    List<String> notes;

    @Override
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
    //TODO tämä hoidetaan jo frontissa. Eli Session manageri tyhjentää current userin tiedot jolloin näyttö palautuu alku tilaan
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
    @Override
    public List<Note> addNote(String username,String title, String content) {
        // lisää uuden noten userilla ja palauttaa listan kaikista noteista
        // pitää jotenkin tarkastaa että menee oikeelle userille notet ja palauttaa oikeen userin notet
        // tähän post pyyntö
        // palauttaa listan kaikista noteista
        //tee uusi note userille jonka username on username

        if (currentUser != null) {
            Note note = new Note(title, content);
            currentUser.addNote(note);
            return currentUser.getNotes();
        }
        return Collections.emptyList();
    }
    @Override
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