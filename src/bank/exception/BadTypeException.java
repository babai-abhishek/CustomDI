package bank.exception;

public class BadTypeException extends Exception {
	
	String msg;
	
	public BadTypeException(String msg) {
		
		this.msg = msg;
	}
	
	public String toString(){
		return msg;
		
	}

}
