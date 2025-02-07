package Model;
import java.time.LocalDate;
public class Note {

    private String title;
    private String content;
    private String date;


    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = LocalDate.now().toString();
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
