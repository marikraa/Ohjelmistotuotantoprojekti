package View;

import javafx.scene.control.Alert;

public class ErrorPopup {
    public static void showError(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

