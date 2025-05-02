package view.utilies;
import model.Note;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteNodeBuilder {

    public List<NoteNode> build(List<Note> notes) {
        List<NoteNode> noteNodes = new ArrayList<>();
        for(Note note : notes){
            NoteNode noteNode = new NoteNode(note);
            noteNodes.add(noteNode);
        }
        Collections.reverse(noteNodes);

        return noteNodes;

    }



}
