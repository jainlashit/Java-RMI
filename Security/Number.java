import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Number extends Remote {
	int getKey(int base, int prime, int cipher) throws RemoteException;
    byte[] primeTest(byte[] input) throws RemoteException;
    byte[] palindrome(byte[] input) throws RemoteException;
    byte[] fibonacci(byte[] input) throws RemoteException;
    byte[] caseConvert(byte[] input) throws RemoteException;
}