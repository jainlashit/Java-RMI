import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.text.SimpleDateFormat;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Banking stub = (Banking) registry.lookup("Banking");
            String msg;
            // call func() like stub.func() Enjoy :D
            msg = stub.deposit("0000000002", 4000);           
            System.out.println(msg);
            msg = stub.withdraw("0000000002", 3000);           
            System.out.println(msg);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = sdf.parse("21/12/1990");
            Date d2 = sdf.parse("1/12/2017");
            msg = stub.transaction_details("0000000002", d1, d2);
            System.out.println(msg);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
