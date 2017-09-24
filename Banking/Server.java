import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
import java.io.IOException;
import java.util.jar.Attributes.Name;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Server implements Banking {
        
    public List<Account> account = new ArrayList<Account>();
    public HashMap accountHash = new HashMap();
    public int currTransactionID = 6538;
 
    public Server() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("bank_details.xml");
            NodeList accountList = doc.getElementsByTagName("account");
            for(int i = 0; i < accountList.getLength(); i++)
            {
                        
                Account tempAccount = new Account();
                Node accountNode = accountList.item(i);
                if(accountNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element accountElement = (Element) accountNode;
                    NodeList infoList = accountElement.getChildNodes();
                    
                    for (int j = 0; j < infoList.getLength(); j++) {
                        
                        Node infoNode = (Node) infoList.item(j);
                        if(infoNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element infoElement = (Element) infoNode;
                            if(infoElement.getTagName() == "name")
                            {
                                tempAccount.name = infoElement.getTextContent();
                            }
                            else if(infoElement.getTagName() == "account_type")
                                tempAccount.account_type = infoElement.getTextContent();
                            else if(infoElement.getTagName() == "contact_info")
                                tempAccount.contact_info = infoElement.getTextContent();
                            else if(infoElement.getTagName() == "account_balance")
                                tempAccount.account_balance = Integer.parseInt(infoElement.getTextContent());
                            else if(infoElement.getTagName() == "account_number")
                            {
                                tempAccount.account_number = infoElement.getTextContent();
                                accountHash.put(tempAccount.account_number, new Integer(i));
                            }
                            else if(infoElement.getTagName() == "transaction_history")
                            {
                                // Current Assumption: Nothing is present in XML 
                                tempAccount.transaction_history = new ArrayList<Transaction>();
                            }                                
                        }
                    }
                    account.add(tempAccount);
                }
                
            }
        } 
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String deposit(String acc_number, int amount)
    {
        int id = (Integer) accountHash.get(acc_number);
        account.get(id).account_balance += amount;
        
        Transaction tempTransaction = new Transaction();
        currTransactionID += 1;
        tempTransaction.id = currTransactionID;
        tempTransaction.amount = amount;
        tempTransaction.balance = account.get(id).account_balance;
        tempTransaction.setType(true);
        tempTransaction.timeStamp = new Date();
        account.get(id).transaction_history.add(tempTransaction);

        Message msg = new Message();
        return msg.updateMessage(currTransactionID, account.get(id).account_balance);
    }

    public String withdraw(String acc_number, int amount)
    {
        int id = (Integer) accountHash.get(acc_number);
        account.get(id).account_balance -= amount;
        
        Transaction tempTransaction = new Transaction();
        currTransactionID += 1;
        tempTransaction.id = currTransactionID;
        tempTransaction.amount = amount;
        tempTransaction.balance = account.get(id).account_balance;
        tempTransaction.setType(false); 
        tempTransaction.timeStamp = new Date();
        account.get(id).transaction_history.add(tempTransaction);
        
        Message msg = new Message();
        return msg.updateMessage(currTransactionID, account.get(id).account_balance);
    }

    public String balance(String acc_number)
    {
        int id = (Integer) accountHash.get(acc_number);
        return Integer.toString(account.get(id).account_balance);
    }

    public String transaction_details(String acc_number, Date start_date, Date end_date)
    {
        int id = (Integer) accountHash.get(acc_number);
        
        Message msg = new Message();   
        return msg.transactionMessage(account.get(id).transaction_history, start_date, end_date);
    }
        
    public static void main(String args[]) {
        
        try {
            Server obj = new Server();
            Banking stub = (Banking) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Banking", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
