import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.*;
import java.nio.charset.Charset;

public class Client {

    private Client() {}
    
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            
            Registry registry = LocateRegistry.getRegistry(host);
            Number stub = (Number) registry.lookup("Number");


            // Diffie Hellman Key Exchange
            int base = 3;
            int prime = 7;
            int self_exp = 5;
            int self_cipher = ((int)Math.pow(base, self_exp))%prime;

            int server_cipher = stub.getKey(base, prime, self_cipher);
            int global_key = ((int)Math.pow(server_cipher, self_exp))%prime;
            byte[] key = Integer.toString(global_key).getBytes();

            String msg;
            byte[] output;
            Crypt c = new Crypt();

            // for (int i = 1; i < 100; i++) {
            //     output = stub.primeTest(c.encrypt(Integer.toString(i).getBytes(UTF_8), key));
            //     msg = new String(c.decrypt(output, key), UTF_8);
            //     System.out.println(msg);
            // }

            msg = "Jalaj";
            output = stub.palindrome(c.encrypt(msg.getBytes(UTF_8), key));
            msg = new String(c.decrypt(output, key), UTF_8);
            System.out.println(msg);

            msg = "lASHIT jAIN";
            output = stub.caseConvert(c.encrypt(msg.getBytes(UTF_8), key));
            msg = new String(c.decrypt(output, key), UTF_8);
            System.out.println(msg);


            output = stub.fibonacci(c.encrypt(Integer.toString(10).getBytes(UTF_8), key));
            msg = new String(c.decrypt(output, key), UTF_8);
            System.out.println(msg);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
