package DataSource;

import Model.User;

import jakarta.persistence.*;

public class UserDAO {
    public void createUser(User user) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            return em.find(User.class, id);
        } catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUsername(String username) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            return em.find(User.class, username);
        } catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}