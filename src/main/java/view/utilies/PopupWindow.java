package view.utilies;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Utility class for displaying popup windows in the application.
 * Provides methods for showing error messages, confirmation dialogs, and image previews.
 */
public class PopupWindow {

    /**
     * Displays an error popup with the specified title and message.
     *
     * @param title   The title of the error popup.
     * @param message The error message to display.
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PopupWindow() {
        // Prevent instantiation
    }

    /**
     * Displays a confirmation dialog with the specified title and message.
     *
     * @param title   The title of the confirmation dialog.
     * @param message The message to display in the confirmation dialog.
     * @return true if the user confirms, false otherwise.
     */
    public static Boolean askForConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.isPresent() && buttonType.get().equals(ButtonType.OK);
    }

    /**
     * Displays an image in a new popup window.
     *
     * @param image The image to display.
     */
    public static void showImage(Image image) {
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