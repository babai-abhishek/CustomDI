package bank.exception;

public class InvalidAccountException extends Exception {

	String msg;
	
	public InvalidAccountException(String msg) {
		
		this.msg = msg;
	}
	
	public String toString(){
		return msg;
		
	}
}
