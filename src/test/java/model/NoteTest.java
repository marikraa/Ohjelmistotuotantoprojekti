package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    private static final String TEST_USER = "testUser";
    private static final String PASSWORD = "password";
    private static final String LANGUAGE_CODE = "en";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String IMAGE_URL = "http://example.com/image.jpg";
    private static final String NEW_USER = "newUser";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String NEW_TITLE = "newTitle";
    private static final String NEW_BODY = "newBody";
    private static final String NEW_IMAGE_URL = "http://newexample.com/image.jpg";

    private Note note;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(TEST_USER, PASSWORD, null, LANGUAGE_CODE);
        note = new Note(TITLE, BODY, IMAGE_URL, LocalDateTime.now());
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
        User newUser = new User(NEW_USER, NEW_PASSWORD, null, LANGUAGE_CODE);
        note.setUser(newUser);
        assertEquals(newUser, note.getUser());
    }

    @Test
    void getTitle() {
        assertEquals(TITLE, note.getTitle());
    }

    @Test
    void setTitle() {
        note.setTitle(NEW_TITLE);
        assertEquals(NEW_TITLE, note.getTitle());
    }

    @Test
    void getBody() {
        assertEquals(BODY, note.getBody());
    }

    @Test
    void setBody() {
        note.setBody(NEW_BODY);
        assertEquals(NEW_BODY, note.getBody());
    }

    @Test
    void getImageUrl() {
        assertEquals(IMAGE_URL, note.getImageUrl());
    }

    @Test
    void setImageUrl() {
        note.setImageUrl(NEW_IMAGE_URL);
        assertEquals(NEW_IMAGE_URL, note.getImageUrl());
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

    @Test
    void setUserNull() {
        note.setUser(null);
        assertNull(note.getUser());
    }

    @Test
    void setTitleNull() {
        note.setTitle(null);
        assertNull(note.getTitle());
    }

    @Test
    void setBodyNull() {
        note.setBody(null);
        assertNull(note.getBody());
    }

    @Test
    void setImageUrlNull() {
        note.setImageUrl(null);
        assertNull(note.getImageUrl());
    }

    @Test
    void setNotificationTimeNull() {
        note.setNotificationTime(null);
        assertNull(note.getNotificationTime());
    }

    @Test
    void setDateTimeNull() {
        note.setDateTime(null);
        assertNull(note.getDate());
    }

    @Test
    void setNotificationShownNull() {
        note.setNotificationShown(null);
        assertNull(note.notificationShownProperty());
    }
}