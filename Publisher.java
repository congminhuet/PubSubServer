import java.io.Serializable;

public class Publisher implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final int id;
    private String Name;

    public Publisher(int id, String Name)
    {
        this.id = id;
        this.Name = Name;
    }

    public int getId() {
        return id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString()
    {
        return "Name: " + this.Name;
    }
}
