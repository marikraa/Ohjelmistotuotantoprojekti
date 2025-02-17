package Model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Note note;

    @BeforeAll
    static void initJFX() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
        });
        latch.await(1, TimeUnit.SECONDS);
    }

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password", null);
        note = new Note("title", "body");
        user.addNote(note);
    }

    @AfterEach
    void tearDown() {
        user = null;
        note = null;
    }
    @Test
    void testDefaultConstructor() {
        User defaultUser = new User();
        assertNotNull(defaultUser);
        assertNotNull(defaultUser.getNotes());
        assertTrue(defaultUser.getNotes().isEmpty());
    }
    @Test
    void getId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void setId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    void getUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void getProfilePicture() {
        Image image = new Image("http://example.com/image.jpg");
        user.setProfilePicture(image);
        assertEquals(image, user.getProfilePicture());
    }

    @Test
    void setProfilePicture() {
        Image image = new Image("http://newexample.com/image.jpg");
        user.setProfilePicture(image);
        assertEquals(image, user.getProfilePicture());
    }

    @Test
    void getProfilePictureUrl() {
        user.setProfilePictureUrl("http://example.com/image.jpg");
        assertEquals("http://example.com/image.jpg", user.getProfilePictureUrl());
    }

    @Test
    void setProfilePictureUrl() {
        user.setProfilePictureUrl("http://newexample.com/image.jpg");
        assertEquals("http://newexample.com/image.jpg", user.getProfilePictureUrl());
    }

    @Test
    void getNotes() {
        List<Note> notes = user.getNotes();
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals(note, notes.get(0));
    }

    @Test
    void addNote() {
        Note newNote = new Note("newTitle", "newBody");
        user.addNote(newNote);
        assertTrue(user.getNotes().contains(newNote));
    }

    @Test
    void removeNote() {
        user.removeNote(note);
        assertFalse(user.getNotes().contains(note));
    }

    @Test
    void sortNotes() {
        Note newNote = new Note("title", "newBody");
        user.addNote(newNote);
        List<Note> sortedNotes = user.sortNotes("title");
        assertEquals(2, sortedNotes.size());
        assertTrue(sortedNotes.contains(note));
        assertTrue(sortedNotes.contains(newNote));
    }

    @Test
    void sortNotesTitleNotMatch() {
        Note newNote = new Note("differentTitle", "newBody");
        user.addNote(newNote);
        List<Note> sortedNotes = user.sortNotes("title");
        assertEquals(1, sortedNotes.size());
        assertTrue(sortedNotes.contains(note));
        assertFalse(sortedNotes.contains(newNote));
    }
}