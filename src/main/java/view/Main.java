package view;

import javafx.application.Application;

/**
 * The entry point of the application.
 * This class launches the JavaFX GUI.
 */
public class Main {
    /**
     * The main method that starts the application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }
}