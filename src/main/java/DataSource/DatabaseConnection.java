package DataSource;

import jakarta.persistence.*;

public class DatabaseConnection {
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public static EntityManager getConnection() {
        if (em == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("DBunit");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            em = emf.createEntityManager();
        }
        return em;
    }
}