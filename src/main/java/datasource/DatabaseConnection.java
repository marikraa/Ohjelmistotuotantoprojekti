package datasource;

import jakarta.persistence.*;

public class DatabaseConnection {
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    private DatabaseConnection() {
    }

    public static EntityManager getConnection() {
        if (em == null) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("DBunit");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (emf != null) {
                em = emf.createEntityManager();
            } else {
                throw new IllegalStateException("EntityManagerFactory is null");
            }
        }
        return em;
    }
}