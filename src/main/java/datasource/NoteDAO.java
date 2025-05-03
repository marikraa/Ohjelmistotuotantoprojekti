package datasource;

import model.Note;
import model.User;
import jakarta.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for managing `Note` entities in the database.
 * Provides methods for creating, updating, deleting, and retrieving notes.
 */
public class NoteDAO {
    private static final Logger LOGGER = Logger.getLogger(NoteDAO.class.getName());

    /**
     * Persists a new note in the database.
     *
     * @param note The `Note` object to be created.
     * @return `true` if the note was successfully created, `false` otherwise.
     */
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

    /**
     * Updates an existing note in the database.
     *
     * @param note The `Note` object with updated data.
     * @return `true` if the note was successfully updated, `false` otherwise.
     */
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

    /**
     * Deletes a note from the database by its ID.
     *
     * @param id The ID of the note to be deleted.
     * @return `true` if the note was successfully deleted, `false` otherwise.
     */
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

    /**
     * Retrieves a note from the database by its ID.
     *
     * @param id The ID of the note to be retrieved.
     * @return The `Note` object if found, or `null` if no note with the given ID exists.
     */
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