package view.utilies;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Utility class for adding images to the application.
 * Provides functionality to open a file chooser and select an image file.
 */
public class ImageAdder {
    private Image image;

    /**
     * Opens a file chooser dialog to allow the user to select an image file.
     * Supported file formats are PNG, JPG, and JPEG.
     *
     * @return The selected image as an {@link Image} object, or null if no file is selected.
     */
    public Image addPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            image = new Image(selectedFile.toURI().toString());
        }
        return image;
    }

    /**
     * Protected getter for the image field.
     *
     * @return The current image.
     */
    protected Image getImage() {
        return image;
    }
}