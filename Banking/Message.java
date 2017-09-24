import java.util.*;

public class Message{
	
	public Message(){}

	public String updateMessage(int transaction_id, int balance)
	{
		String msg = "Transaction Successful!\n";
		msg += "Transaction ID: " + Integer.toString(transaction_id) + "\n";
		msg += "Current Balance: " + Integer.toString(balance) + "\n";
		return msg;
	}

	public String transactionMessage(List<Transaction> transactionList, Date start_date, Date end_date)
	{
		String[] heading = {"ID", "Amount", "Type", "Balance",  "Time", "Date"};
		String msg = String.format("|%5s|%10s|%10s|%10s|%15s|%15s| \r\n", heading[0], heading[1], heading[2], heading[3], heading[4], heading[5]);
		String time;
		String date;

		for (Transaction transaction : transactionList ) {
			// transaction id, amount, balance, type, timeStamp
			
			if(end_date.after(transaction.timeStamp) && start_date.before(transaction.timeStamp)) {
				time = new java.text.SimpleDateFormat("h:mm:ss a").format(transaction.timeStamp);
				date = new java.text.SimpleDateFormat("dd/MM/yyyy").format(transaction.timeStamp);
				msg += String.format("|%5d|%10d|%10s|%10d|%15s|%15s| \r\n", transaction.id, transaction.amount, transaction.type, transaction.balance, time, date);
			}
		}
		return msg;
	}
}