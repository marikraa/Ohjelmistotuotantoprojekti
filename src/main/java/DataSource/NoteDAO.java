package DataSource;

import Model.Note;

import jakarta.persistence.*;

public class NoteDAO {
    public void createNote(Note note) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNote(Note note) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.merge(note);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteNote(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            Note note = em.find(Note.class, id);
            if (note != null) {
                em.remove(note);
                em.flush(); // Ensure changes are flushed to the database
                em.getTransaction().commit();
                System.out.println("Note with ID " + id + " deleted successfully.");
                return true;
            } else {
                System.out.println("Note with ID " + id + " not found.");
                em.getTransaction().rollback();
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        }
    }

    public Note getNoteById(int id) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            return em.find(Note.class, id);
        } catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}