package view.managers;

import model.User;
import view.utilies.NoteNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SessionManager {
    private static String languageString = "EN";
    private static Locale locale;
    private static User currentUser = null;
    private static final List<String> LANGUAGES = List.of("EN", "FI", "JA", "AR");
    private static List<NoteNode> notes = new ArrayList<>();
    private SessionManager(){
        //private constructor
    }



    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void clearUser() {
        currentUser = null;
    }

    public static void setLanguage(String language) {
        languageString = language;
        locale = switch (language) {
            case "FI" -> Locale.of("fi", "FI");
            case "JA" -> Locale.of("ja", "JP");
            case "AR" -> Locale.of("ar", "IQ");
            default   -> Locale.of("en", "US");
        };
        }

    public static Locale getLocale() {
        return locale;
    }
    public static String getLanguageString() {
        return languageString;

    }
    public static List<String> getLanguages() {
        return LANGUAGES;
    }


    public static List<NoteNode> getNotes() {
        return notes;
    }

    public static void setNotes(List<NoteNode> notes) {
        SessionManager.notes = notes;
    }
}
