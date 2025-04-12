package view.utilies;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.util.Optional;

public class PopupWindow {
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private PopupWindow(){
    // constructor
    }


    public static Boolean askForConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.isPresent() && buttonType.get().equals(ButtonType.OK);
    }

    public static void showImage(Image image) {
    // this shows the image in a new window
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        Stage imageStage = new Stage();
        imageStage.setTitle("Image Preview");
        ScrollPane root = new ScrollPane(view);
        root.setFitToWidth(true);
        root.setFitToHeight(true);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Scene scene = new Scene(root, 800, 800);
        imageStage.setScene(scene);
        imageStage.setResizable(true);
        imageStage.show();
    }
}

