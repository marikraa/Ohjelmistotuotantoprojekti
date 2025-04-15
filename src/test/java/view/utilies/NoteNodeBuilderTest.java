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

    /*
    @Test
    void testBuildWithNotes() {
        // Mock Note objects
        Note note1 = mock(Note.class);
        Note note2 = mock(Note.class);

        // Create a list of notes
        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);

        // Test the build method
        NoteNodeBuilder builder = new NoteNodeBuilder();
        List<NoteNode> result = builder.build(notes);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof NoteNode);
        assertTrue(result.get(1) instanceof NoteNode);
    }
     */
}