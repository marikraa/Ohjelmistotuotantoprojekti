package Controller;

import FrontEnd.Note;
import FrontEnd.User;
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



    public User login(String username, String password) {
          /*
          TODO: requesti palauttaaa User olio
        }*/

        //gson.fromJson(json, User.class); jolloin muuttaa jsonin User olioksi ja returnaa sen

        //testi data
        Note note = new Note("title", "asddsa");
        List<Note> notes = new ArrayList<>() ;
        notes.add(note);
        User user = new User(username, password, notes);
        return user ;
    }

    public boolean logOut(String username) {
        //tähän post pyyntö
        boolean success = true;

        return success;
    }

    public boolean signUp(User user) {
        //tähän post pyyntö
/* Gson gson = new Gson();
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
        System.out.println("Response: " + response.body());*/

        // toisessa päässä gson.fromJson(json, User.class); jolloin muuttaa jsonin User olioksi

        boolean success = true;
        return success;
    }
}
