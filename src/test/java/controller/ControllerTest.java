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

    private static final String TEST_USER = "testUser";
    private static final String UNKNOWN_USER = "unknownUser";
    private static final String PASSWORD = "password";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String IMAGE_PATH = "/images/defaultProfilePic.png";
    private static final String TEST_IO_EXCEPTION = "Test IOException";
    private static final String IMAGE_URL = "http://example.com/image.jpg";

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
        User mockUser = new User(TEST_USER, PASSWORD, null, "en");
        when(userDAO.getUserByUsername(TEST_USER)).thenReturn(mockUser);

        User user = controller.login(TEST_USER, PASSWORD);
        assertNotNull(user);
        assertEquals(TEST_USER, user.getUsername());

        User invalidUser = controller.login(UNKNOWN_USER, PASSWORD);
        assertNull(invalidUser);
    }

    @Test
    void addNote() {
        User mockUser = new User(TEST_USER, PASSWORD, null, "en");
        when(userDAO.getUserByUsername(TEST_USER)).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote(TEST_USER, TITLE, CONTENT, null, notificationTime);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals(TITLE, notes.get(0).getTitle());
    }

    @Test
    void addNoteWithImage() throws IOException {
        User mockUser = new User(TEST_USER, PASSWORD, null, "en");
        when(userDAO.getUserByUsername(TEST_USER)).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        Image image = new Image(getClass().getResourceAsStream(IMAGE_PATH));
        when(imageHandling.uploadImage(image)).thenReturn("{\"url\":\"" + IMAGE_URL + "\"}");
        when(imageHandling.parseImageUrl("{\"url\":\"" + IMAGE_URL + "\"}")).thenReturn(IMAGE_URL);
        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote(TEST_USER, TITLE, CONTENT, image, notificationTime);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals(TITLE, notes.get(0).getTitle());
        assertEquals(IMAGE_URL, notes.get(0).getImageUrl());
    }

    @Test
    void addNoteWithImageIOException() throws IOException {
        User mockUser = new User(TEST_USER, PASSWORD, null, "en");
        when(userDAO.getUserByUsername(TEST_USER)).thenReturn(mockUser);
        when(userDAO.updateUser(mockUser)).thenReturn(true);

        Image image = new Image(getClass().getResourceAsStream(IMAGE_PATH));
        when(imageHandling.uploadImage(image)).thenThrow(new IOException(TEST_IO_EXCEPTION));
        LocalDateTime notificationTime = LocalDateTime.now();
        List<Note> notes = controller.addNote(TEST_USER, TITLE, CONTENT, image, notificationTime);
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
    }

    @Test
    void signupUserAlreadyExists() {
        User existingUser = new User(TEST_USER, PASSWORD, null, "en");
        when(userDAO.getUserByUsername(TEST_USER)).thenReturn(existingUser);

        User user = controller.signup(TEST_USER, PASSWORD, null, "en");

        assertNull(user);
    }


    @Test
    void updateUserNotFound() {
        when(userDAO.getUserByUsername(UNKNOWN_USER)).thenReturn(null);

        boolean success = controller.updateUser(UNKNOWN_USER, "newUsername", "newPassword", null, "en");

        assertFalse(success);
    }

    @Test
    void deleteUserException() {
        User user = new User(TEST_USER, PASSWORD, null, "en");
        doThrow(new RuntimeException()).when(userDAO).deleteUser(user.getId());

        boolean success = controller.deleteUser(user);

        assertFalse(success);
    }

    @Test
    void updateNoteException() {
        Note mockNote = new Note();
        mockNote.setId(1);
        when(noteDAO.getNoteById(1)).thenThrow(new RuntimeException());

        boolean success = controller.updateNote(mockNote);

        assertFalse(success);
    }

    @Test
    void deleteNoteException() {
        Note mockNote = new Note();
        mockNote.setId(1);
        doThrow(new RuntimeException()).when(noteDAO).deleteNote(1);

        boolean success = controller.deleteNote(mockNote);

        assertFalse(success);
    }
}