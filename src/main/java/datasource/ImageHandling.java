package datasource;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * This class handles image-related operations, including uploading images to an external API (ImgBB)
 * and parsing the response to retrieve the image URL.
 */
public class ImageHandling {
    private static final Logger LOGGER = Logger.getLogger(ImageHandling.class.getName());
    private String apiKey;

    /**
     * Constructor that initializes the API key from the .env file.
     */
    public ImageHandling() {
        Dotenv dotenv = Dotenv.load(); // Load the .env file
        this.apiKey = dotenv.get("IMGBB_API_KEY"); // Retrieve the API key from the .env file
    }

    /**
     * Parses the JSON response from the ImgBB API to extract the URL of the uploaded image.
     *
     * @param response The JSON response from the API as a string.
     * @return The URL of the uploaded image, or null if parsing fails.
     */
    public String parseImageUrl(String response) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);
            JSONObject dataObject = (JSONObject) jsonObject.get("data");
            if (dataObject != null) {
                return (String) dataObject.get("url");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing image URL from response", e);
        }
        return null;
    }

    /**
     * Uploads an image to the ImgBB API and returns the JSON response.
     *
     * @param image The image to be uploaded.
     * @return The JSON response from the API as a string, or null if the image is null.
     * @throws IOException If an error occurs during the upload process.
     */
    public String uploadImage(Image image) throws IOException {
        if (image == null) return null;

        // Convert the JavaFX Image to a BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);

        // Encode the image as a Base64 string
        byte[] imageData = outputStream.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(imageData);

        // Prepare the API request
        URI uri = URI.create("https://api.imgbb.com/1/upload?key=" + apiKey);
        HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        // Send the encoded image data
        String encodedData = "image=" + URLEncoder.encode(encodedImage, StandardCharsets.UTF_8);
        try (OutputStream os = con.getOutputStream()) {
            os.write(encodedData.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        // Read the API response
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