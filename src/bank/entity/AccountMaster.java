package bank.entity;

public class AccountMaster {
	
	private int accno;
	private String name;
	private String phone;
	private String pan_id;
	private double balance;
	private String Date;
	
	public int getAccno() {
		return accno;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getPan_id() {
		return pan_id;
	}
	public double getBalance() {
		return balance;
	}
	public String getDate() {
		return Date;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setPan_id(String pan_id) {
		this.pan_id = pan_id;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setDate(String date) {
		Date = date;
	}
	
	
	

}
