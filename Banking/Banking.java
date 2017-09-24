import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface Banking extends Remote {
    String deposit(String acc_number, int amount) throws RemoteException;
    String withdraw(String acc_number, int amount) throws RemoteException;
    String balance(String acc_number) throws RemoteException;
    String transaction_details(String acc_number, Date start_date, Date end_date) throws RemoteException;
}