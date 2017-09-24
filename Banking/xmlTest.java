import java.io.IOException;
import java.util.jar.Attributes.Name;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class xmlTest{

	public static void main(String[] args) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("bank_details.xml");
			NodeList accountList = doc.getElementsByTagName("account");
			System.out.println(accountList.getLength());
			for(int i = 0; i < accountList.getLength(); i++)
			{
				Node accountNode = accountList.item(i);
				if(accountNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element account = (Element) accountNode;
					NodeList infoList = account.getChildNodes();
					
					System.out.println(infoList.getLength());
					for (int j = 0; j < infoList.getLength(); j++) {
						Node infoNode = (Node) infoList.item(j);
						if(infoNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Element info = (Element) infoNode;
							if(info.getTagName() == "name")
							{
								String name = (String) info.getTextContent();
								System.out.println("Account Holder: " + name);
							}
							else if(info.getTagName() == "transaction_history")
							{
							    NodeList transactionList = info.getChildNodes();	    
							    if(transactionList.getLength() == 0)
								{
									System.out.println("Empty!");
								}
								else
								{
									// System.out.println(transactionList.getLength());
								}
							}
							
						}
						}
					}
				}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}