import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
import java.nio.charset.Charset;

public class Server implements Number {

    private byte[] key;
    private static final Charset UTF_8 = Charset.forName("UTF-8");


    public Server() {
    }

    public int getKey(int base, int prime, int cipher)
    {
        // Diffie Hellman Key Exchange
        int self_exp = 3;
        int self_cipher = ((int)Math.pow(base, self_exp))%prime;
        int global_key = ((int)Math.pow(cipher, self_exp))%prime;

        key = Integer.toString(global_key).getBytes();

        return self_cipher;
    }

    public byte[] primeTest(byte[] input){

        Crypt c = new Crypt();
        String inputMsg = new String(c.decrypt(input, key), UTF_8);
        int number = Integer.parseInt(inputMsg);

        Boolean isPrime = true; 
        if (number == 1)
            isPrime = false;
        
        for(int i = 2; i <= number/2; i++)
        {
            if(number%i == 0)
                isPrime = false;
        }

        String outputMsg;

        if(isPrime)
            outputMsg = Integer.valueOf(number) + " is a prime number.";
        else
            outputMsg = Integer.valueOf(number) + " is not a prime number.";

        byte[] output = c.encrypt(outputMsg.getBytes(UTF_8), key);
        return output;

    }

    public byte[] palindrome(byte[] input){
        
        Crypt c = new Crypt();
        String string = new String(c.decrypt(input, key), UTF_8);

        Boolean isPalindrome = true;
        String lowerString = string.toLowerCase();

        for(int i = 0; i < lowerString.length(); i++)
        {
            if(lowerString.charAt(i) != lowerString.charAt(lowerString.length() - i - 1))
            {
                isPalindrome = false;
                break;
            }
        }        
        
        String outputMsg;
        if(isPalindrome)
            outputMsg = string + " is a palindrome.";
        else
            outputMsg = string + " is not a palindrome.";

        byte[] output = c.encrypt(outputMsg.getBytes(UTF_8), key);
        return output;
    }

    public byte[] fibonacci(byte[] input){


        Crypt c = new Crypt();
        String inputMsg = new String(c.decrypt(input, key), UTF_8);
        int index = Integer.parseInt(inputMsg);
        
        int number, prev = 0;
        if(index == 1)
            number = 0;
        else if (index == 2)
            number = 1;
        else{
            number = 1;
            for (int i = 3; i <= index; i++) {
                int temp;
                temp = number;
                number += prev;
                prev = temp;
            }
        }
        String outputMsg;
        outputMsg =  Integer.toString(number);

        byte[] output = c.encrypt(outputMsg.getBytes(UTF_8), key);
        return output;
    }
    
    public byte[] caseConvert(byte[] input){

        Crypt c = new Crypt();
        String string = new String(c.decrypt(input, key), UTF_8);

        String lowerString = string.toLowerCase();
        String upperString = string.toUpperCase();
        char[] reverseCase = new char[string.length()];

        for (int i = 0; i < string.length() ; i++) {
            reverseCase[i] = (char)(string.charAt(i) ^ lowerString.charAt(i) ^ upperString.charAt(i));
        }

        String outputMsg = new String(reverseCase);

        byte[] output = c.encrypt(outputMsg.getBytes(UTF_8), key);
        return output;
    }
        
    public static void main(String args[]) {
        
        try {
            Server obj = new Server();
            Number stub = (Number) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Number", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
