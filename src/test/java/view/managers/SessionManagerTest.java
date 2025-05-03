package view.managers;

import model.User;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    @Test
    void testSetAndGetCurrentUser() {
        User mockUser = new User();
        SessionManager.setCurrentUser(mockUser);
        assertEquals(mockUser, SessionManager.getCurrentUser());

        SessionManager.clearUser();
        assertNull(SessionManager.getCurrentUser());
    }

    @Test
    void testSetAndGetLanguage() {
        SessionManager.setLanguage("FI");
        assertEquals("FI", SessionManager.getLanguageString());
        assertEquals(Locale.of("fi", "FI"), SessionManager.getLocale());

        SessionManager.setLanguage("EN");
        assertEquals("EN", SessionManager.getLanguageString());
        assertEquals(Locale.of("en", "US"), SessionManager.getLocale());
    }

    @Test
    void testGetLanguages() {
        List<String> languages = SessionManager.getLanguages();
        assertNotNull(languages);
        assertTrue(languages.contains("EN"));
        assertTrue(languages.contains("FI"));
    }
}