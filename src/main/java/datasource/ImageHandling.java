package datasource;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ImageHandling {
    private String apiKey = "b9f3ecbc93a44b77a11d12ede92dd507";

    // parse JSON for the URL of the uploaded image
    public String parseImageUrl(String response) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);
            JSONObject dataObject = (JSONObject) jsonObject.get("data");
            if (dataObject != null) {
                return (String) dataObject.get("url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // returns the JSON response from the API after an upload
    public String uploadImage(Image image) throws IOException {
        if (image == null) return null;

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);

        byte[] imageData = outputStream.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(imageData);

        URI uri = URI.create("https://api.imgbb.com/1/upload?key=" + apiKey);
        HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        String encodedData = "image=" + URLEncoder.encode(encodedImage, StandardCharsets.UTF_8);
        try (OutputStream os = con.getOutputStream()) {
            os.write(encodedData.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}