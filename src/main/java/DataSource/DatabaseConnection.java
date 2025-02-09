package DataSource;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/notes_app_db";
    private static final String USER = "appuser";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}