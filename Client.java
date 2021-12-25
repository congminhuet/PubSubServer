import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private Client() {}
    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
       
        try 
        {
            Registry registry = LocateRegistry.getRegistry(host);
            PubSubInterface stub = (PubSubInterface) registry.lookup("PubSubInterface");
            
            int ip = 0;
            Scanner s = new Scanner(System.in);

            do 
            {
                System.out.println("\nEnter the desired option: \n");
                System.out.println("1 - register a publisher;");
                System.out.println("2 - register a topic;");
                System.out.println("3 - See all publishers;");
                System.out.println("4 - View All topics;");
                System.out.println("5 - send message to a topic;");
                System.out.println("6 - Quit");

                ip = s.nextInt();
                
                switch(ip)
                {
                    case 1:
                        System.out.println(registerPublisher(stub));
                        break;

                    case 2:
                        System.out.println(registerTopic(stub));
                        break;

                    case 3:
                        Thread t1 = new Thread(new ClientThread(host, "Publishers"));
                        t1.start();                        
                        break;

                    case 4:
                        Thread t2 = new Thread(new ClientThread(host, "topics"));
                        t2.start();
                        break;

                    case 5:
                        SendMessage(stub);
                        break;

                    default:
                        break;
                }
            
            } while (ip != 6);
        
        }
        catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void SendMessage(PubSubInterface stub)
    {
        try {
            Scanner s = new Scanner(System.in);

            System.out.println("\nenter the topic id: ");
            int id_topic = s.nextInt();

            Scanner x = new Scanner(System.in);
            System.out.println("enter message: ");
            
            String message = x.nextLine();

            System.out.println(stub.post(id_topic, message));
        }
        catch (Exception e)
        {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static String registerPublisher(PubSubInterface stub)
    {
        try {
            Scanner s = new Scanner(System.in);

            System.out.println("\nType your name: ");
            String name = s.nextLine();

            System.out.println("enter your id: ");
            int id = s.nextInt();

            return stub.registerPublisher(id, name);
        } 
        catch (Exception e)
        {
            System.out.println("Publisher Exception: " + e.toString());
            e.printStackTrace();
        }
        
        return "registerPublisher";
    }

    public static String registerTopic(PubSubInterface stub)
    {
        try {
            Scanner c = new Scanner(System.in);

            System.out.println("\nEnter the topic title: ");
            String title = c.nextLine();

            System.out.println("enter the keyword: ");
            String key = c.nextLine();

            System.out.println("enter the topic id: ");
            int id = c.nextInt();            

            System.out.println("Enter the topic publisher id: ");
            int id_publisher = c.nextInt();

            return stub.registerTopic(id, title, id_publisher, key);
        }
        catch (Exception e)
        {
            System.out.println("Exception topic: " + e.toString());
            e.printStackTrace();
        }
        
        return "registerTopic";
    }
}
