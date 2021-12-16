import java.io.Serializable;
import java.util.ArrayList;


public class Topic implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final int id;
    private String title;
    private String description;
    private int id_publisher;
    private String keyword;
    private ArrayList<String> mensagens = new ArrayList<>();

    public Topic(int id, String title, int id_publisher, String keyword)
    {
        this.id = id;
        this.title = title;
        this.id_publisher = id_publisher;
        this.keyword = keyword;
    }

    public void addMessage(String message)
    {
        this.mensagens.add(message);
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId_publisher(int id_publisher) {
        this.id_publisher = id_publisher;
    }

    public int getId_publisher() {
        return id_publisher;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getKeyword()
    {
        return this.keyword;
    }

    @Override
    public String toString() {
        return  "Id: " + this.id + "\nTitle: " + this.title + "\nKeyword: " + this.keyword;
    }
}
