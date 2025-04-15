package view.managers;

import javafx.stage.Stage;
import model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class SceneManagerTest {

    private Stage mockStage;

    @BeforeEach
    void setUp() {
        mockStage = mock(Stage.class);
        SceneManager.setStage(mockStage);
    }

    @Test
    void testSetStage() {
        // Verify that the stage is set without exceptions
        SceneManager.setStage(mockStage);
        // No exception means the test passes
    }
    /*
    @Test
    void testSwitchScene() {
        // Mock a valid FXML file
        SceneManager.switchScene("ValidScene.fxml");
        // No exception means the test passes
    }

     */

    /*
    @Test
    void testOpenModal() {
        Note mockNote = mock(Note.class);
        SceneManager.openModal("ValidModal.fxml", mockNote);
        // No exception means the test passes
    }

     */
}