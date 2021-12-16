import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Collection;

public class Server implements PubSubInterface, Remote
{
    private HashMap<Integer, Publisher> publishers = new HashMap<>();
    private HashMap<Integer, Subscriber> subscribers = new HashMap<>();
    private HashMap<Integer, Topic> topics = new HashMap<>();
    private HashMap<Integer, String> keywords = new HashMap<>();
    private Dodger subRemote;

    public Server() {
    };

    private void alive() {
        try {
            Registry registry = LocateRegistry.getRegistry();
            this.subRemote = (Dodger) registry.lookup("Dodger");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
        }
    }

    private String parseCollection(Collection values) {

        String answer = "";
        for (Object value: values)
            answer += '\n' + value.toString();

        return answer;
    }

    @Override
    public String helloPubSubInterface(String test) throws RemoteException 
    {
        return "reported, " + test + "!";
    }

    @Override
    public String registerPublisher(int id, String name) throws RemoteException 
    {
        Publisher newPublisher = new Publisher(id, name);
        this.publishers.put(id, newPublisher);        

        return (this.publishers.get(id)).toString();
    }

    @Override
    public String registerSubscriber(int id, String email) throws RemoteException 
    {
        this.alive();

        Subscriber newSubscriber = new Subscriber(id, email);
        this.subscribers.put(id, newSubscriber);
                
        return (this.subscribers.get(id)).toString();
    }

    @Override
    public String addTopic(int id_subscriber, int id_topic) throws RemoteException
    {
        Subscriber sub = this.subscribers.get(id_subscriber);

        if (sub != null)
        {
            Topic topic = this.topics.get(id_topic);
            if (topic != null)
            {
                sub.addTopic(topic);
                this.subscribers.replace(id_subscriber, sub);

                return "topic successfully added!";
            }
            return "Unable to add topic! non-existent topic!";
        }
        return "Could not add topic! non-existent subscriber!";
    }

    @Override
    public String removerTopic(int id_subscriber, int id_topic) throws RemoteException
    {
        Subscriber sub = this.subscribers.get(id_subscriber);
        
        if (sub != null)
        {
            sub.removerTopic(id_topic);
            this.subscribers.replace(id_subscriber, sub);

            return "topic removed successfully!";
        }

        return "Unable to remove topic! non-existent subscriber!";
    }

    @Override
    public String listTopicsPerId(int id_subscriber) throws RemoteException
    {
        Subscriber sub = subscribers.get(id_subscriber);

        return parseCollection((sub.getTopics()).values());
    }

    @Override
    public String registerTopic(int id, String title, int id_publisher, String keyword) throws RemoteException
    {
        if (this.publishers.get(id_publisher) != null) 
        {
            Topic newTopic = new Topic(id, title, id_publisher, keyword);

            this.topics.put(id, newTopic);
            this.keywords.put(id, newTopic.getKeyword());

            return (this.topics.get(id)).toString();
        }

        return "It was not possible to register the topic!";
    }

    public String listMessages(int id_subscriber) throws RemoteException
    {
        Subscriber sub = this.subscribers.get(id_subscriber);

        if (sub != null)
            return parseCollection((sub.getInbox()).values());
        
        return "Sub does not exist!";
    }

    public synchronized String printPublishers() throws RemoteException
    {
        return parseCollection(publishers.values()); 
    }

    public synchronized String printTopics() throws RemoteException
    {
        return parseCollection(topics.values());
    }

    public String post(int id_topic, String message)
    {
        Topic topic = this.topics.get(id_topic);
        if (topic != null)
        {
            topic.addMessage(message);
            this.topics.replace(id_topic, topic);

            for (Subscriber sub : this.subscribers.values())
            {
                for (Topic topic2: (sub.getTopics()).values())
                {
                    if (topic2.getId() == id_topic)
                    {
                        sub.addMessage(id_topic, message);
                        try 
                        {
                            subRemote.listMessages(sub);
                        }
                        catch (Exception e)
                        {
                            System.err.println("Server exception: " + e.toString());
                        }
                    }
                }
            }

            return "Message sent successfully!";
        }
        return "non-existent topic!";
    }

    public static void main(String args[])
    {
        try {
            Server obj = new Server();
            PubSubInterface stub = (PubSubInterface) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("PubSubInterface", stub);
         
            System.err.println("Server ready");
        }
        catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
