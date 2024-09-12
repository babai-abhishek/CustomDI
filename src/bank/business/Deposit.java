package bank.business;

import java.text.SimpleDateFormat;

import bank.dao.AccountMasterDao;
import bank.dao.TransactionDao;
import bank.entity.AccountMaster;
import bank.entity.Transaction;
import bank.exception.InvalidAccountException;

public class Deposit implements IExecuteBusiness.ExecuteDeposit{
	
	private AccountMasterDao accountMasterDao;
	private TransactionDao transactionDao;
	
	public void setAccountMasterDao(AccountMasterDao accountMasterDao){
		this.accountMasterDao = accountMasterDao;
	}
	public void setTransactionDao(TransactionDao transactionDao){
		this.transactionDao = transactionDao;
	}
	
	public void Execute(int accno,double amount) throws InvalidAccountException{
		
		java.util.Date date = new java.util.Date();
		String type = "Deposit";

		AccountMaster accountMaster=accountMasterDao.retrieve(accno);
		double CurrentBalance = accountMaster.getBalance();
				
		double NewBalance=CurrentBalance+amount;
		
		accountMaster.setAccno(accno);
		accountMaster.setBalance(NewBalance);
		accountMasterDao.update(accountMaster);


		Transaction transaction=new Transaction();
		transaction.setAccno(accno);
		transaction.setAmount(amount);
		transaction.setBalance(NewBalance);
		transaction.setDate(new SimpleDateFormat("yyyy/MM/dd").format(date));
		transaction.setType(type);
		transactionDao.insert(transaction);
		 
		
	}

}
