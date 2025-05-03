package view.utilies;

import model.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for building a list of NoteNode objects from a list of Note objects.
 */
public class NoteNodeBuilder {

    /**
     * Builds a list of NoteNode objects from the given list of Note objects.
     * The resulting list is reversed to maintain the desired order.
     *
     * @param notes The list of Note objects to convert into NoteNode objects.
     * @return A reversed list of NoteNode objects.
     */
    public List<NoteNode> build(List<Note> notes) {
        List<NoteNode> noteNodes = new ArrayList<>();
        for (Note note : notes) {
            NoteNode noteNode = new NoteNode(note);
            noteNodes.add(noteNode);
        }
        Collections.reverse(noteNodes);

        return noteNodes;
    }
}