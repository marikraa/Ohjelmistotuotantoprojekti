package view.managers;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
}