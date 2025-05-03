package datasource;

import model.User;
import jakarta.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing `User` entities in the database.
 * Provides methods for creating, updating, deleting, and retrieving users.
 */
public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    /**
     * Persists a new user in the database.
     *
     * @param user The `User` object to be created.
     * @return `true` if the user was successfully created, `false` otherwise.
     */
    public boolean createUser(User user) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating user", e);
            return false;
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The `User` object with updated data.
     * @return `true` if the user was successfully updated, `false` otherwise.
     */
    public boolean updateUser(User user) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating user", e);
            return false;
        }
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @return `true` if the user was successfully deleted, `false` otherwise.
     */
    public boolean deleteUser(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
            return false;
        }
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The `User` object if found, or `null` if no user with the given ID exists.
     */
    public User getUserById(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            return em.find(User.class, id);
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "User not found with id: " + id, e);
            return null;
        }
    }

    /**
     * Retrieves a user from the database by their username.
     *
     * @param username The username of the user to be retrieved.
     * @return The `User` object if found, or `null` if no user with the given username exists.
     */
    public User getUserByUsername(String username) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "User not found with username: " + username, e);
            return null;
        }
    }
}