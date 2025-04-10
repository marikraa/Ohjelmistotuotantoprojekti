package datasource;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageHandlingTest {

    private ImageHandling imageHandling;
    private Image mockImage;

    @BeforeEach
    void setUp() {
        imageHandling = new ImageHandling();
        mockImage = mock(Image.class);
    }

    @Test
    void testParseImageUrl() {
        String jsonResponse = "{\"data\":{\"url\":\"http://example.com/image.jpg\"}}";
        String imageUrl = imageHandling.parseImageUrl(jsonResponse);
        assertEquals("http://example.com/image.jpg", imageUrl);
    }

    @Test
    void testParseImageUrlInvalidResponse() {
        String jsonResponse = "{\"data\":{}}";
        String imageUrl = imageHandling.parseImageUrl(jsonResponse);
        assertNull(imageUrl);
    }

    @Test
    void testParseImageUrlNullDataObject() {
        String jsonResponse = "{\"data\":null}";
        String imageUrl = imageHandling.parseImageUrl(jsonResponse);
        assertNull(imageUrl);
    }

    @Test
    void testParseImageUrlException() {
        String invalidJsonResponse = "invalid json";
        String imageUrl = imageHandling.parseImageUrl(invalidJsonResponse);
        assertNull(imageUrl);
    }

    @Test
    void testUploadImage() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        try (MockedStatic<SwingFXUtils> swingFXUtilsMockedStatic = Mockito.mockStatic(SwingFXUtils.class)) {
            swingFXUtilsMockedStatic.when(() -> SwingFXUtils.fromFXImage(mockImage, null)).thenReturn(bufferedImage);

            String response = imageHandling.uploadImage(mockImage);
            assertNotNull(response);
        }
    }

    @Test
    void testUploadImageNull() throws IOException {
        String response = imageHandling.uploadImage(null);
        assertNull(response);
    }
}