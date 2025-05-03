package datasource;

import jakarta.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
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
                    LOGGER.log(Level.SEVERE, "Failed to create EntityManagerFactory", e);
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

    // Reset method for testing purposes
    public static void reset() {
        if (em != null) {
            em.close();
            em = null;
        }
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }
}