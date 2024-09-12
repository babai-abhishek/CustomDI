package bank.dao;

import java.util.ArrayList;

import bank.entity.Transaction;

public interface TransactionDao {
	
public void insert(Transaction transaction);		
	
	public void update(Transaction transaction);
	
	public ArrayList<Transaction> retrieve(int accno);

	public void delete(int accno);
	

}
