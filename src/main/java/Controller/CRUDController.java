package Controller;

import java.awt.*;
import java.util.List;

public class CRUDController {
    List<String> notes;
    public String login(String username, String password) {
        //tähän post pyyntö
        //pitää palauttaa tällänen json
        /*{
          "username": "jokuKäyttäjä",
         "password": "salasana",
          "notes": "muistiinpanoja List<String> muodossa"
          muistiinpanolla pitää olla otsikko päivämäärä ja sitte itse muistiinpano
        }*/

        return "{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"notes\": \"" + notes + "\"}";
    }

    public boolean logOut(TextField username) {

        boolean success = true;


        return success;
    }
}
