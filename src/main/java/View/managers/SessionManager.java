package View.managers;

import Model.User;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SessionManager {
    static String languageString = "EN";
    static Locale locale;
    public static User currentUser = null;
    static List<String> languages = List.of("EN", "FI", "JA", "AR");
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
        switch (language) {
            case "EN":
               locale = new Locale("en", "US");
                break;
            case "FI":
                locale = new Locale("fi", "FI");
                break;
            case "JA":
                locale = new Locale("ja", "JP");
                break;
            case "AR":
               locale = new Locale("ar", "IQ");
                break;
        }
    }


    public static Locale getLocale() {
        return locale;
    }

    public static String getLanguageString() {
        return languageString;

    }
    public static List<String> getLanguages() {
        return languages;
    }





}
