import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientThread implements Runnable
{
    private String host;
    private String method;
    
    public ClientThread(String host, String method)
    {
        this.host = host;
        this.method = method;
    }

    @Override
    public void run()
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry(this.host);
            PubSubInterface stub = (PubSubInterface) registry.lookup("PubSubInterface");

            if (this.method == "Publishers")
                System.out.println(stub.printPublishers());
            
            if (this.method == "topics")
                System.out.println(stub.printTopics());
        }
        catch (Exception e)
        {
            System.err.println("ClientThread exception: " + e.toString());
            e.printStackTrace();
        }
    }
}