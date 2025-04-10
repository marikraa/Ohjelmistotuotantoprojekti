package datasource;

import model.User;

import jakarta.persistence.*;

public class UserDAO {
    public boolean createUser(User user) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
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
        } catch(Exception e) {
            e.printStackTrace();
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
        } catch(Exception e) {
            e.printStackTrace();
            return false;
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
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user =  query.getSingleResult();
            System.out.println("Käyttäjä löytyi: " + user.getUsername()); // Varmista, että käyttäjä löytyi
            return user;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}