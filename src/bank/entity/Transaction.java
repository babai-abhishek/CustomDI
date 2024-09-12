package bank.entity;

public class Transaction {

	private int tid;
	private int accno;
	private String date;
	private double amount;
	private double balance;
	private String type;
	/*public Transaction(int tid, int accno, String date, double amount, double balance, String type) {
		super();
		this.tid = tid;
		this.accno = accno;
		this.date = date;
		this.amount = amount;
		this.balance = balance;
		this.type = type;
	}*/
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTid() {
		return tid;
	}
	public int getAccno() {
		return accno;
	}
	public String getDate() {
		return date;
	}
	public double getAmount() {
		return amount;
	}
	public double getBalance() {
		return balance;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
