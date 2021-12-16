import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dodger extends Remote
{
    public void listMessages(Subscriber remetente) throws RemoteException;
}