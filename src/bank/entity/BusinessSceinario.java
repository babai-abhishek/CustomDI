package bank.entity;

import java.util.ArrayList;

public class BusinessSceinario {
	
	String bClass;
	ArrayList<String> hClasses;
	ArrayList<String> hClassType;
	
	public void setBusinessClass(String bClass){
		this.bClass = bClass;
	}	

	public void setHelperClassTypes(ArrayList<String> hClassType) {
		this.hClassType = hClassType;
	}

	public void setHelperClasses(ArrayList<String> hClasses){
		this.hClasses = hClasses;
	}
	
	public String getBusinessClass(){
		return bClass;
		
	}
	
	public ArrayList<String> getHelperClasses(){
		return hClasses;	
	}
	
	public ArrayList<String> getHelperClassTypes() {
		return hClassType;
	}

}
