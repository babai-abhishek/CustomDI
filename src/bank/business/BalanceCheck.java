package bank.business;

import bank.dao.AccountMasterDao;
import bank.entity.AccountMaster;
import bank.business.IExecuteBusiness.ExecuteBalanceCheck;
import bank.exception.InvalidAccountException;

public class BalanceCheck implements ExecuteBalanceCheck{
	
	private AccountMasterDao iAccountMasterDao;

	//DEPENDENCY INJECTED VIA set METHOD
	public void setAccountMasterDao(AccountMasterDao accountMasterDao){
		this.iAccountMasterDao = accountMasterDao;
	}

	@Override
	public double Execute(int accno) throws InvalidAccountException {
		
		System.out.println("accno "+accno);
		AccountMaster accountMaster = null;
		accountMaster = iAccountMasterDao.retrieve(accno);	
		double balance = accountMaster.getBalance();
		System.out.println("balance found "+balance);
		
		return balance;
	}
	
	
	


}
