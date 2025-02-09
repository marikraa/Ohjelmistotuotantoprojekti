package Controller;

import Model.Note;
import Model.User;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class CRUDController {
    static CRUDController crudController;
 private CRUDController() {}


    public static CRUDController getInstance(){
     if(crudController == null){
            crudController = new CRUDController();
     }
        return crudController;
    }

    List<String> notes;
    //Backend backend = new Backend();


    public User login(String username, String password) {
          /*
          TODO: requesti palauttaaa User olio
        }*/

        //gson.fromJson(json, User.class); jolloin muuttaa jsonin User olioksi ja returnaa sen

        //testi data
        Note note = new Note("title", "asddsa");
        User user = new User(username, password, null);
        user.addNote(note);
        return user ;
    }

    public boolean logOut(String username) {
        //tähän post pyyntö
        boolean success = true;

        return success;
    }
/*
    public boolean signup() {

        // kuvan uppaus servulle??
        //tähän post pyyntö
 Gson gson = new Gson();
        String json = gson.toJson(user);

        // Luodaan HTTP POST -pyyntö
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.com/api/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        // Lähetetään pyyntö ja saadaan vastaus
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Tulostetaan palvelimen vastaus
        System.out.println("Response: " + response.body());

        // toisessa päässä gson.fromJson(json, User.class); jolloin muuttaa jsonin User olioksi
        User user = new User("username", "password", null);
        boolean success = true;
        return success;
    }*/

    public List<Note> addNote(String title , String content) {
        //tähän post pyyntö
        //lisää uuden noten userilla ja palauttaa listan kaikista noteista
        // pitää jotenkin tarkastaa että menee oikeelle userille notet ja palauttaa oikeen userin notet
        //tähän post pyyntö
        // palauttaa listan kaikista noteista
       // notes = backend.createNote(title, content);
        Note note = new Note(title, content);
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        return notes;


    }

    public User signup(String username, String password, Image image) {
        //backend.CreateUser(username, password, image); tämä palauttaa user olion

        //testiä varten tämä
        User user = new User(username, password, image);
        return user;
    }
}
