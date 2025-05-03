package datasource;

import model.User;
import jakarta.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

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

    public User getUserById(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            return em.find(User.class, id);
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "User not found with id: " + id, e);
            return null;
        }
    }

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