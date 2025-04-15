package model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends ApplicationTest {

    private User user;
    private Note note;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password", null, "en"); // Added "en" as the languageCode
        note = new Note("title", "body", "http://example.com/image.jpg", LocalDateTime.now());
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
        Note newNote = new Note("newTitle", "newBody", "http://example.com/image.jpg", LocalDateTime.now());
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
        Note newNote = new Note("title", "newBody", "http://example.com/image.jpg", LocalDateTime.now());
        user.addNote(newNote);
        List<Note> sortedNotes = user.sortNotes("title");
        assertEquals(2, sortedNotes.size());
        assertTrue(sortedNotes.contains(note));
        assertTrue(sortedNotes.contains(newNote));
    }

    @Test
    void sortNotesTitleNotMatch() {
        Note newNote = new Note("differentTitle", "newBody", "http://example.com/image.jpg", LocalDateTime.now());
        user.addNote(newNote);
        List<Note> sortedNotes = user.sortNotes("title");
        assertEquals(1, sortedNotes.size());
        assertTrue(sortedNotes.contains(note));
        assertFalse(sortedNotes.contains(newNote));
    }

    @Test
    void getProfilePicture() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Image profilePicture = new Image("http://example.com/image.jpg");
            user.setProfilePicture(profilePicture);
            assertEquals(profilePicture.getUrl(), user.getProfilePicture().getUrl());
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void setNotes() {
        Note newNote = new Note("newTitle", "newBody", "http://example.com/image.jpg", LocalDateTime.now());
        List<Note> notes = List.of(note, newNote);
        user.setNotes(notes);
        assertEquals(notes, user.getNotes());
    }
}