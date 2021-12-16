import java.rmi.RemoteException;
import java.rmi.Remote;

public interface PubSubInterface extends Remote
{
    public String helloPubSubInterface(String test) throws RemoteException;

    public String registerPublisher(int id, String name) throws RemoteException;

    public String printPublishers() throws RemoteException;

    public String registerSubscriber(int id, String email) throws RemoteException;

    public String addTopic(int id_subscriber, int id_topic) throws RemoteException;

    public String removerTopic(int id_subscriber, int id_topic) throws RemoteException;

    public String listTopicsPerId(int id_subscriber) throws RemoteException;

    public String registerTopic(int id, String title, int id_publisher, String keyword) throws RemoteException;

    public String printTopics() throws RemoteException;

    public String post(int id_topic, String message) throws RemoteException;

    public String listMessages(int id_subscriber) throws RemoteException;

}
