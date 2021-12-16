import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class ClientSubs {

    private ClientSubs() {}

    public static void registerSubscriber(PubSubInterface stub)
    {
        try 
        {
            Scanner s = new Scanner(System.in);

            System.out.println("\nType your e-mail: ");
            String email = s.nextLine();

            System.out.println("enter your id: ");
            int id = s.nextInt();

            System.out.println("\n" + stub.registerSubscriber(id, email));   
        }
        catch(Exception e)
        {
            System.out.println("Register subscriber exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void addTopic(PubSubInterface stub, String host)
    {
        try 
        {
            Scanner s = new Scanner(System.in);

            System.out.println("enter your id:\n");
            int id_subscriber = s.nextInt();

            Thread t1 = new Thread(new ClientThread(host, "topics"));
            t1.start(); 

            System.out.println("Enter the id of the topic to be added:\n");
            int id_topic = s.nextInt();

            System.out.println(stub.addTopic(id_subscriber, id_topic) + "\n");
        }
        catch(Exception e)
        {
            System.out.println("Add exception topic: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void removerTopic(PubSubInterface stub, String host)
    {
        try 
        {
            Scanner s = new Scanner(System.in);

            System.out.println("\nenter your id:");
            int id_subscriber = s.nextInt();

            Thread t1 = new Thread(new ClientThread(host, "topics"));
            t1.start(); 

            System.out.println("\nEnter the id of the topic to be removed:");
            int id_topic = s.nextInt();

            String response = stub.removerTopic(id_subscriber, id_topic);
            System.out.println("\nresponse: " + response);           
        }
        catch(Exception e)
        {
            System.out.println("Remove exception topic: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void printMyTopics(PubSubInterface stub)
    {
        try 
        {
            Scanner s = new Scanner(System.in);

            System.out.println("\nenter your id: ");
            int id = s.nextInt();

            String response = stub.listTopicsPerId(id);
            System.out.println("\nresponse: " + response);
        }
        catch(Exception e)
        {
            System.out.println("List topics by id exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void listMessages(PubSubInterface stub)
    {
        try
        {
            Scanner s = new Scanner(System.in);

            System.out.println("\nenter your id: ");
            int id = s.nextInt();
            
            System.out.println(stub.listMessages(id));
        }
        catch (Exception e)
        {
            System.out.println("List exception messages: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];

        try 
        {
            Registry registry = LocateRegistry.getRegistry(host);
            PubSubInterface stub = (PubSubInterface) registry.lookup("PubSubInterface");

            Thread message = new Thread(new ClientThread(host, "Message"));
            message.start();

            int op = 0;

            do 
            {
                System.out.println("\nEnter the desired option:\n");
                System.out.println("1 - Register a new subscriber");
                System.out.println("2 - Enroll in a topic");
                System.out.println("3 - remove a topic");
                System.out.println("4 - List all my topics");
                System.out.println("5 - view inbox");
                System.out.println("6 - Quit");

                Scanner s = new Scanner(System.in);
                op = s.nextInt();

                switch(op)
                {
                    case 1: 
                        registerSubscriber(stub);
                        break;

                    case 2:
                        addTopic(stub, host);
                        break;

                    case 3:
                        removerTopic(stub, host);
                        break;

                    case 4:
                        printMyTopics(stub);
                        break;

                    case 5:
                        listMessages(stub);
                        break;

                    default:
                        break;
                }

            } while (op != 6);
            
        }
        catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}