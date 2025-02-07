package Controller;

import FrontEnd.User;
import javafx.scene.image.Image;

import java.util.List;

public class CRUDController {
    List<String> notes;

    public User login(String username, String password) {
          /*
          TODO: requesti palauttaaa User olio
        }*/

        //testi data
        User user = new User(username, password, null);
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

        boolean success = true;
        return success;
    }
}
