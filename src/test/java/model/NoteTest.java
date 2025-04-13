package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    private Note note;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password", null, "en"); // Added "en" as the languageCode
        note = new Note("title", "body", "http://example.com/image.jpg", LocalDateTime.now());
        note.setUser(user);
        note.setId(1);
    }

    @AfterEach
    void tearDown() {
        note = null;
        user = null;
    }

    @Test
    void testDefaultConstructor() {
        Note defaultNote = new Note();
        assertNotNull(defaultNote);
    }

    @Test
    void getId() {
        assertEquals(1, note.getId());
    }

    @Test
    void setId() {
        note.setId(2);
        assertEquals(2, note.getId());
    }

    @Test
    void getUser() {
        assertEquals(user, note.getUser());
    }

    @Test
    void setUser() {
        User newUser = new User("newUser", "newPassword", null, "en"); // Added "en" as the languageCode
        note.setUser(newUser);
        assertEquals(newUser, note.getUser());
    }

    @Test
    void getTitle() {
        assertEquals("title", note.getTitle());
    }

    @Test
    void setTitle() {
        note.setTitle("newTitle");
        assertEquals("newTitle", note.getTitle());
    }

    @Test
    void getBody() {
        assertEquals("body", note.getBody());
    }

    @Test
    void setBody() {
        note.setBody("newBody");
        assertEquals("newBody", note.getBody());
    }

    @Test
    void getImageUrl() {
        assertEquals("http://example.com/image.jpg", note.getImageUrl());
    }

    @Test
    void setImageUrl() {
        note.setImageUrl("http://newexample.com/image.jpg");
        assertEquals("http://newexample.com/image.jpg", note.getImageUrl());
    }

    @Test
    void getDateTime() {
        assertNotNull(note.getDate());
    }

    @Test
    void setDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        note.setDateTime(dateTime);
        assertEquals(dateTime, note.getDate());
    }

    @Test
    void getNotificationTime() {
        assertNotNull(note.getNotificationTime());
    }

    @Test
    void setNotificationTime() {
        LocalDateTime notificationTime = LocalDateTime.now();
        note.setNotificationTime(notificationTime);
        assertEquals(notificationTime, note.getNotificationTime());
    }

    @Test
    void notificationShownProperty() {
        assertFalse(note.notificationShownProperty());
        note.setNotificationShown(true);
        assertTrue(note.notificationShownProperty());
    }
}