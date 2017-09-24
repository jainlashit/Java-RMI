import java.util.Date;

public class Transaction{
	public int id;
	public int amount;
	public int balance;
	public String type;
	public Date timeStamp;
	/* Can get both date and time using timestamp
	String time = new java.text.SimpleDateFormat("h:mm:ss a").format(timeStamp);
	String date = new java.text.SimpleDateFormat("MM/dd/yyyy").format(timeStamp);
	*/

	public void setType(Boolean flag)
	{
		// True => Deposit
		// False => Withdraw
		if(flag)
			type = "Deposit";
		else
			type = "Withdraw";
	}
}
