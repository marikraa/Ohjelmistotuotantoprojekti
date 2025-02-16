package View;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GUITest extends ApplicationTest {

    private GUI gui;

    @Override
    public void start(Stage stage) throws Exception {
        gui = new GUI();
        gui.start(stage);
    }

    @Test
    void start() {
        assertNotNull(gui);
    }
}