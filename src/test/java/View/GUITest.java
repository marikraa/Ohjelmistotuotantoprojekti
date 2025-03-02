package View;

import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GUITest extends ApplicationTest {

    private GUI gui;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
    }

    @BeforeEach
    void setUp() {
        gui = new GUI();
        primaryStage = mock(Stage.class);
    }

    @AfterEach
    void tearDown() {
        gui = null;
        primaryStage = null;
    }

    @Test
    void start() throws IOException {
        doNothing().when(primaryStage).setOnCloseRequest(any());
        doNothing().when(primaryStage).show();

        gui.start(primaryStage);

        verify(primaryStage, times(1)).setOnCloseRequest(any());
        verify(primaryStage, times(1)).show();
    }
}