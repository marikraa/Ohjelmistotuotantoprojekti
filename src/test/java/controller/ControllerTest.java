
package controller;

import datasource.NoteDAO;
import datasource.UserDAO;
import datasource.ImageHandling;
import model.Note;
import model.User;
import javafx.scene.image.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    private Controller controller;
    private UserDAO userDAO;
    private NoteDAO noteDAO;
    private ImageHandling imageHandling;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        noteDAO = mock(NoteDAO.class);
        imageHandling = mock(ImageHandling.class);
        controller = new Controller();
        controller.userDAO = userDAO;
        controller.noteDAO = noteDAO;
        controller.imageHandling = imageHandling;
    }

    @AfterEach
    void tearDown() {
        controller = null;
    }

    @Test
    void login() {
        User mockUser = new User("testUser", "password", null);
        when(userDAO.getUserByUsername("testUser")).thenReturn(mockUser);

        User user = controller.login("testUser", "password");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());

        User invalidUser = controller.login("invalidUser", "password");
        assertNull(invalidUser);
    }

    @Test
    void addNote() {
        User mockUser = new User("testUser", "password", null);
        when(userDAO.getUserByUsername("testUser")).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote("testUser", "title", "content", null, notificationTime);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals("title", notes.get(0).getTitle());
    }

    @Test
    void addNoteWithImage() throws IOException {
        User mockUser = new User("testUser", "password", null);
        when(userDAO.getUserByUsername("testUser")).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        Image image = new Image(getClass().getResourceAsStream("/images/defaultProfilePic.png"));
        when(imageHandling.uploadImage(image)).thenReturn("{\"url\":\"http://example.com/image.jpg\"}");
        when(imageHandling.parseImageUrl("{\"url\":\"http://example.com/image.jpg\"}")).thenReturn("http://example.com/image.jpg");
        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote("testUser", "title", "content", image, notificationTime);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals("title", notes.get(0).getTitle());
        assertEquals("http://example.com/image.jpg", notes.get(0).getImageUrl());
    }

    @Test
    void addNoteWithImageIOException() throws IOException {
        User mockUser = new User("testUser", "password", null);
        when(userDAO.getUserByUsername("testUser")).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        Image image = new Image(getClass().getResourceAsStream("/images/defaultProfilePic.png"));
        when(imageHandling.uploadImage(image)).thenThrow(new IOException("Test IOException"));
        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote("testUser", "title", "content", image, notificationTime);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
    }

    @Test
    void addNoteUserNotFound() {
        when(userDAO.getUserByUsername("unknownUser")).thenReturn(null);

        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote("unknownUser", "title", "content", null, notificationTime);
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void signup() {
        when(userDAO.getUserByUsername("newUser")).thenReturn(null);
        when(userDAO.createUser(any(User.class))).thenReturn(true);

        User user = controller.signup("newUser", "password", null);
        assertNotNull(user);
        assertEquals("newUser", user.getUsername());

        when(userDAO.getUserByUsername("newUser")).thenReturn(user);
        User duplicateUser = controller.signup("newUser", "password", null);
        assertNull(duplicateUser);
    }

    @Test
    void signupWithImageIOException() throws IOException {
        Image image = new Image(getClass().getResourceAsStream("/images/defaultProfilePic.png"));
        when(imageHandling.uploadImage(image)).thenThrow(new IOException("Test IOException"));
        when(userDAO.getUserByUsername("newUser")).thenReturn(null);
        when(userDAO.createUser(any(User.class))).thenReturn(true);

        User user = controller.signup("newUser", "password", image);

        assertNotNull(user);
        assertEquals("newUser", user.getUsername());
        verify(imageHandling).uploadImage(image); // Verify that the method was called
    }

    @Test
    void updateUser() {
        User mockUser = new User("oldUser", "password", null);
        when(userDAO.getUserByUsername("oldUser")).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        Boolean success = controller.updateUser("oldUser", "newUser", "newPassword", null);
        assertTrue(success);
    }

    @Test
    void updateUserWithImageIOException() throws IOException {
        User mockUser = new User("oldUser", "password", null);
        when(userDAO.getUserByUsername("oldUser")).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        Image image = new Image(getClass().getResourceAsStream("/images/defaultProfilePic.png"));
        when(imageHandling.uploadImage(image)).thenThrow(new IOException("Test IOException"));

        Boolean success = controller.updateUser("oldUser", "newUser", "newPassword", image);

        assertTrue(success);
        verify(imageHandling).uploadImage(image); // Verify that the method was called
    }

    @Test
    void updateUserNotFound() {
        when(userDAO.getUserByUsername("unknownUser")).thenReturn(null);

        Boolean success = controller.updateUser("unknownUser", "newUser", "newPassword", null);
        assertFalse(success);
    }

    @Test
    void deleteUser() {
        User user = new User("deleteUser", "password", null);
        when(userDAO.deleteUser(user.getId())).thenReturn(true);

        Boolean success = controller.deleteUser(user);
        assertTrue(success);
    }

    @Test
    void deleteUserException() {
        User user = new User("deleteUser", "password", null);
        doThrow(new RuntimeException()).when(userDAO).deleteUser(user.getId());

        Boolean success = controller.deleteUser(user);
        assertFalse(success);
    }

    @Test
    void addNoteException() {
        User mockUser = new User("testUser", "password", null);
        when(userDAO.getUserByUsername("testUser")).thenReturn(mockUser);
        doThrow(new RuntimeException()).when(userDAO).updateUser(any(User.class));

        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote("testUser", "title", "content", null, notificationTime);
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void updateUserException() {
        User mockUser = new User("oldUser", "password", null);
        when(userDAO.getUserByUsername("oldUser")).thenReturn(mockUser);
        doThrow(new RuntimeException()).when(userDAO).updateUser(any(User.class));

        Boolean success = controller.updateUser("oldUser", "newUser", "newPassword", null);
        assertFalse(success);
    }

    @Test
    void loginInvalidPassword() {
        User mockUser = new User("testUser", "password", null);
        when(userDAO.getUserByUsername("testUser")).thenReturn(mockUser);

        User user = controller.login("testUser", "wrongPassword");
        assertNull(user);
    }

    @Test
    void deleteNote() {
        Note mockNote = new Note();
        mockNote.setId(1);
        when(noteDAO.deleteNote(1)).thenReturn(true);

        boolean success = controller.deleteNote(mockNote);
        assertTrue(success);
        verify(noteDAO, times(1)).deleteNote(1);
    }

    @Test
    void deleteNoteException() {
        Note mockNote = new Note();
        mockNote.setId(1);
        doThrow(new RuntimeException()).when(noteDAO).deleteNote(1);

        boolean success = controller.deleteNote(mockNote);
        assertFalse(success);
    }

    @Test
    void updateNote() {
        Note mockNote = new Note();
        mockNote.setId(1);
        when(noteDAO.getNoteById(1)).thenReturn(mockNote);
        when(noteDAO.updateNote(mockNote)).thenReturn(true);

        Boolean success = controller.updateNote(mockNote);
        assertTrue(success);
    }

    @Test
    void updateNoteNotFound() {
        Note mockNote = new Note();
        mockNote.setId(1);
        when(noteDAO.getNoteById(1)).thenReturn(null);

        Boolean success = controller.updateNote(mockNote);
        assertFalse(success);
    }

    @Test
    void updateNoteException() {
        Note mockNote = new Note();
        mockNote.setId(1);
        when(noteDAO.getNoteById(1)).thenThrow(new RuntimeException());

        Boolean success = controller.updateNote(mockNote);
        assertFalse(success);
    }
}