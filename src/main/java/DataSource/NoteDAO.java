package DataSource;

import Model.Note;

import Model.User;
import jakarta.persistence.*;

public class NoteDAO {
    public boolean createNote(Note note) {
        EntityManager em = DatabaseConnection.getConnection();
        try {
            em.getTransaction().begin();
            em.persist(note);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
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
        } catch(Exception e) {
            e.printStackTrace();
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
                    em.merge(user);}
                //em.remove(note);
                em.flush();
                em.getTransaction().commit();
                System.out.println("Note deleted."+ note.getId());
                return true;
            } else {
                System.out.println("Note not found.");
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