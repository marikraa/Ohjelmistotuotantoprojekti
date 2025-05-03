package model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final String TEST_USER = "testUser";
    private static final String PASSWORD = "password";
    private static final String LANGUAGE_CODE = "en";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String IMAGE_URL = "http://example.com/image.jpg";
    private static final String NEW_USER = "newUser";

    private User user;
    private Note note;

    @BeforeEach
    void setUp() {
        user = new User(TEST_USER, PASSWORD, null, LANGUAGE_CODE);
        note = new Note(TITLE, BODY, IMAGE_URL, LocalDateTime.now());
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
        assertEquals(TEST_USER, user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername(NEW_USER);
        assertEquals(NEW_USER, user.getUsername());
    }

    @Test
    void getNotes() {
        List<Note> notes = user.getNotes();
        assertNotNull(notes);
        assertFalse(notes.isEmpty());
        assertEquals(note, notes.get(0));
    }
    @Test
    void setUsernameNull() {
        user.setUsername(null);
        assertNull(user.getUsername());
    }

    @Test
    void setPasswordNull() {
        user.setPassword(null);
        assertNull(user.getPassword());
    }

    @Test
    void setProfilePictureUrlNull() {
        user.setProfilePictureUrl(null);
        assertNull(user.getProfilePictureUrl());
    }

    @Test
    void setLanguageCodeNull() {
        user.setLanguageCode(null);
        assertNull(user.getLanguageCode());
    }

    @Test
    void setNotesNull() {
        user.setNotes(null);
        assertNull(user.getNotes());
    }
}