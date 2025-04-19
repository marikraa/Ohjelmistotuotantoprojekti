package view.managers;

import model.User;
import java.util.List;
import java.util.Locale;

/**
 * This acts as Session storage for global saved values. Languages and all user info.
 */
public class SessionManager {
    private static String languageString = "EN";
    private static Locale locale;
    private static User currentUser = null;
    private static final List<String> LANGUAGES = List.of("EN", "FI", "JA", "AR");
    private SessionManager(){
        //private constructor
    }


    /**
     * Gets current logged in user
     * @return User object
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * This sets user for session storage when logged in.
     * @param user is logged-in user.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * This is called when user log out
     */
    public static void clearUser() {
        currentUser = null;
    }

    /**
     * This is function to set global language for session storage
     * @param language is string for language recognising.
     */
    public static void setLanguage(String language) {
        languageString = language;
        locale = switch (language) {
            case "FI" -> Locale.of("fi", "FI");
            case "JA" -> Locale.of("ja", "JP");
            case "AR" -> Locale.of("ar", "IQ");
            default   -> Locale.of("en", "US");
        };
        }

    /**
     * Get locale if needed
     * @return Locale object for ui usage
     */
    public static Locale getLocale() {
        return locale;
    }

    /**
     * Set language string, which is used ofr language recognising.
     * @return
     */
    public static String getLanguageString() {
        return languageString;

    }

    /**
     * This is used to get all languages for language selection.
     * @return list of languages.
     */
    public static List<String> getLanguages() {
        return LANGUAGES;
    }


}
