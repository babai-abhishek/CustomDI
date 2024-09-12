package bank.dao;

import bank.entity.AccountMaster;
import bank.exception.InvalidAccountException;

public interface AccountMasterDao {
	
	public void insert(AccountMaster accountMaster);		
	
	public void update(AccountMaster accountMaster);
	
	public AccountMaster retrieve(int accno) throws InvalidAccountException;

	public void delete(int accno);
	

}
