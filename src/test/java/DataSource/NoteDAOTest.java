package DataSource;

import Model.Note;
import Model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteDAOTest {

    private NoteDAO noteDAO;
    private EntityManager mockEntityManager;
    private EntityTransaction mockTransaction;
    private MockedStatic<DatabaseConnection> mockedStatic;

    @BeforeEach
    void setUp() {
        noteDAO = new NoteDAO();
        mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);

        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        mockedStatic = Mockito.mockStatic(DatabaseConnection.class);
        mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockEntityManager);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    void testCreateNote() {
        Note note = new Note();
        noteDAO.createNote(note);

        verify(mockEntityManager).persist(note);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testUpdateNote() {
        Note note = new Note();
        noteDAO.updateNote(note);

        verify(mockEntityManager).merge(note);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testDeleteNote() {
        Note note = new Note();
        User user = new User();
        note.setUser(user);
        when(mockEntityManager.find(Note.class, 1)).thenReturn(note);

        boolean result = noteDAO.deleteNote(1);

        assertTrue(result);
        verify(mockEntityManager).remove(note);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testDeleteNoteNotFound() {
        when(mockEntityManager.find(Note.class, 1)).thenReturn(null);

        boolean result = noteDAO.deleteNote(1);

        assertFalse(result);
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
    }

    @Test
    void testGetNoteById() {
        Note note = new Note();
        when(mockEntityManager.find(Note.class, 1)).thenReturn(note);

        Note result = noteDAO.getNoteById(1);

        assertEquals(note, result);
    }
}