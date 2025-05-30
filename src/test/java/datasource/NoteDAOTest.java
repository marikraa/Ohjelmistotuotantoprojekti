package datasource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Note;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteDAOTest {

    private static final int NOTE_ID = 1;

    private NoteDAO noteDAO;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        noteDAO = new NoteDAO();
        entityManager = mock(EntityManager.class);
    }

    @Test
    void testCreateNote() {
        Note note = new Note();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));

            boolean result = noteDAO.createNote(note);

            assertTrue(result);
            verify(entityManager).persist(note);
            verify(entityManager.getTransaction()).begin();
            verify(entityManager.getTransaction()).commit();
        }
    }

    @Test
    void testUpdateNote() {
        Note note = new Note();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));

            boolean result = noteDAO.updateNote(note);

            assertTrue(result);
            verify(entityManager).merge(note);
            verify(entityManager.getTransaction()).begin();
            verify(entityManager.getTransaction()).commit();
        }
    }

    @Test
    void testDeleteNote() {
        Note note = new Note();
        User user = new User();
        note.setUser(user);
        user.addNote(note);

        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
            when(entityManager.find(Note.class, NOTE_ID)).thenReturn(note);

            boolean result = noteDAO.deleteNote(NOTE_ID);

            assertTrue(result);
            verify(entityManager).find(Note.class, NOTE_ID);
            verify(entityManager).merge(user);
            verify(entityManager.getTransaction()).commit();
        }
    }

    @Test
    void testGetNoteById() {
        Note note = new Note();
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(entityManager);

            when(entityManager.find(Note.class, NOTE_ID)).thenReturn(note);

            Note result = noteDAO.getNoteById(NOTE_ID);

            assertNotNull(result);
            assertEquals(note, result);
            verify(entityManager).find(Note.class, NOTE_ID);
        }
    }
}