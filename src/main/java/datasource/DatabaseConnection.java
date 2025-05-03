package datasource;

import jakarta.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the database connection using JPA's EntityManager and EntityManagerFactory.
 * It provides a singleton-like access to the EntityManager for database operations.
 */
public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    /**
     * Provides a singleton-like access to the EntityManager.
     * If the EntityManager or EntityManagerFactory is not initialized, it initializes them.
     *
     * @return the EntityManager instance for database operations
     * @throws IllegalStateException if the EntityManagerFactory could not be created
     */
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

    /**
     * Resets the EntityManager and EntityManagerFactory.
     * This method is primarily used for testing purposes to ensure a clean state.
     */
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