package View.managers;

import Model.User;

public class SessionManager {
    public static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void clearUser() {
        currentUser = null;
    }
}
