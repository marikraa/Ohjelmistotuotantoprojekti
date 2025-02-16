package Controller;

import Model.Note;
import Model.User;
import DataSource.UserDAO;
import javafx.scene.image.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    private Controller controller;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        controller = Controller.getInstance();
        controller.userDAO = userDAO;
    }

    @AfterEach
    void tearDown() {
        controller = null;
    }

    @Test
    void getInstance() {
        Controller instance = Controller.getInstance();
        assertNotNull(instance);
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

        controller.signup("testUser", "password", null);
        List<Note> notes = controller.addNote("testUser", "title", "content");
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals("title", notes.get(0).getTitle());
    }

    @Test
    void addNoteUserNotFound() {
        when(userDAO.getUserByUsername("unknownUser")).thenReturn(null);

        List<Note> notes = controller.addNote("unknownUser", "title", "content");
        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }

    @Test
    void signup() {
        User user = controller.signup("newUser", "password", null);
        assertNotNull(user);
        assertEquals("newUser", user.getUsername());

        when(userDAO.getUserByUsername("newUser")).thenReturn(user);
        User duplicateUser = controller.signup("newUser", "password", null);
        assertNull(duplicateUser);
    }

    @Test
    void updateUser() {
        User mockUser = new User("oldUser", "password", null);
        when(userDAO.getUserByUsername("oldUser")).thenReturn(mockUser);

        controller.signup("oldUser", "password", null);
        Boolean success = controller.updateUser("oldUser", "newUser", "newPassword", null);
        assertTrue(success);
    }

    @Test
    void updateUserNotFound() {
        when(userDAO.getUserByUsername("unknownUser")).thenReturn(null);

        Boolean success = controller.updateUser("unknownUser", "newUser", "newPassword", null);
        assertFalse(success);
    }

    @Test
    void deleteUser() {
        User user = controller.signup("deleteUser", "password", null);
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

        List<Note> notes = controller.addNote("testUser", "title", "content");
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
}