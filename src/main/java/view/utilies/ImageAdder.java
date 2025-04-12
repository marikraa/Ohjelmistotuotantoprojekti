package view.utilies;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageAdder {
    Image image;

    public Image addPicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            image = new Image(selectedFile.toURI().toString());
        }
        return image;

    }

}
