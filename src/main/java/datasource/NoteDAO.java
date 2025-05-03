package datasource;

import model.Note;
import model.User;
import jakarta.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteDAO {
    private static final Logger LOGGER = Logger.getLogger(NoteDAO.class.getName());

    public boolean createNote(Note note) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating note", e);
            return false;
        }
    }

    public boolean updateNote(Note note) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.merge(note);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating note", e);
            return false;
        }
    }

    public boolean deleteNote(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            Note note = em.find(Note.class, id);
            if (note != null) {
                User user = note.getUser();
                if (user != null) {
                    user.getNotes().remove(note);
                    em.merge(user);
                }
                em.flush();
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting note", e);
            em.getTransaction().rollback();
            return false;
        }
    }

    public Note getNoteById(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            return em.find(Note.class, id);
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "Note not found with id: " + id, e);
            return null;
        }
    }
}