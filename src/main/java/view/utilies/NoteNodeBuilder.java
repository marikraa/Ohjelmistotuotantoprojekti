package view.utilies;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Note;
import view.managers.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class NoteNodeBuilder {

    public List<Button> build(List<Note> notes) {
        List<Button> noteNodes = new ArrayList<>();
        for(Note note : notes){
            Button noteButton = new Button();
            NoteNode noteNode = new NoteNode(note);
            VBox noteVBox = noteNode.createNoteNode();
            noteButton.getStyleClass().addAll("note");
            noteButton.setGraphic(noteVBox);
            noteButton.setPrefSize(200, 200);
            noteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openNoteView(note));
            noteNodes.add(noteButton);
        }

        return noteNodes;

    }

    public void openNoteView(Note note) {
        SceneManager.openModal("EditNote.fxml", note);

    }


}
