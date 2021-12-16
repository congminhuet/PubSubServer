import java.io.Serializable;
import java.util.HashMap;

public class Subscriber implements Serializable
{
    private final int id;
    private String email;
    private HashMap<Integer, Topic> topics = new HashMap<>();
    private HashMap<Integer, String> inbox = new HashMap<>();
    private static final long serialVersionUID = 1L;

    public Subscriber(int id, String email)
    {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, String> getInbox()
    {
        return this.inbox;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void addTopic(Topic topic)
    {
        this.topics.put(topic.getId(), topic);
    }

    public void removerTopic(int id)
    {
        this.topics.remove(id);
    }

    public HashMap<Integer, Topic> getTopics()
    {
        return this.topics;
    }

    public void addMessage(int id_topic, String message)
    {
        this.inbox.put(id_topic, message);
    }

    public void printTopics()
    {
        this.topics.values().toString();
    }

    @Override
    public String toString()
    {
        return "Id: " + this.id + "\nEmail: " + this.email;
    }
}