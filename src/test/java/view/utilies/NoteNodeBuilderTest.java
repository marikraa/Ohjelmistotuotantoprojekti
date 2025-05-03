package view.utilies;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.managers.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteNodeBuilderTest {

    @BeforeAll
    static void setupMocks() {
        // Mock SessionManager to return a default Locale
        mockStatic(SessionManager.class);
        when(SessionManager.getLocale()).thenReturn(Locale.US);

        // Mock ResourceBundle to avoid loading actual files
        mockStatic(ResourceBundle.class);
        ResourceBundle mockBundle = mock(ResourceBundle.class);
        when(ResourceBundle.getBundle("language", Locale.US)).thenReturn(mockBundle);
    }

    @Test
    void testBuildWithEmptyList() {
        NoteNodeBuilder builder = new NoteNodeBuilder();
        List<NoteNode> result = builder.build(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}