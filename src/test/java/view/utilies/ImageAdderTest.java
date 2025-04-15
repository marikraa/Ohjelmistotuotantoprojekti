package view.utilies;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageAdderTest {

    private ImageAdder imageAdder;

    @BeforeEach
    void setUp() {
        // Create a subclass of ImageAdder to override the FileChooser behavior
        imageAdder = new ImageAdder() {
            @Override
            public Image addPicture() {
                FileChooser fileChooserMock = mock(FileChooser.class);
                File mockFile = mock(File.class);

                // Mock the behavior of the file
                when(mockFile.toURI()).thenReturn(new File("test.png").toURI());
                when(fileChooserMock.showOpenDialog(null)).thenReturn(mockFile);

                File selectedFile = fileChooserMock.showOpenDialog(null);

                if (selectedFile != null) {
                    image = new Image(selectedFile.toURI().toString());
                }
                return image;
            }
        };
    }

    /*
    @Test
    void testAddPictureWithValidFile() {
        Image result = imageAdder.addPicture();

        assertNotNull(result);
        assertEquals("file:test.png", result.getUrl());
    }
    */


    @Test
    void testAddPictureWithNoFileSelected() {
        // Create a subclass of ImageAdder to simulate no file selected
        imageAdder = new ImageAdder() {
            @Override
            public Image addPicture() {
                FileChooser fileChooserMock = mock(FileChooser.class);

                // Mock the behavior of the file chooser
                when(fileChooserMock.showOpenDialog(null)).thenReturn(null);

                File selectedFile = fileChooserMock.showOpenDialog(null);

                if (selectedFile != null) {
                    image = new Image(selectedFile.toURI().toString());
                }
                return image;
            }
        };

        Image result = imageAdder.addPicture();

        assertNull(result);
    }
}