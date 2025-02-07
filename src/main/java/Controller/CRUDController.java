package Controller;

import Model.User;
import DataSource.UserDAO;

import javafx.scene.image.Image;
import java.io.*;
import java.sql.*;
import java.util.List;

public class CRUDController {
    UserDAO userDAO;

    private User currentUser;
    List<String> notes;

    public CRUDController() {
        userDAO = new UserDAO();
    }

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

    public boolean logOut(String username) {
        if (currentUser != null && currentUser.getUsername().equals(username)) {
            currentUser = null;

            // tässä sit pitäs vaihtaa login fxml sivuun
            
            return true;
        }
        return false;
    }

    public boolean signUp(String username, String password, Image profileImage) {
        //tähän post pyyntö
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

        //tällein kait sais ladattuu kuvan sitten palvelimelle
        /*
        public class ImageUploader {
        public static String uploadImage(File file) {
        try {
            String uploadUrl = "https://yourserver.com/upload"; // <-- MUUTA TÄMÄ
            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****");

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            // Saadaan palvelimen vastaus
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString(); // Palauttaa URL:n tallennettuun kuvaan
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}*/
        return success;
    }
}
