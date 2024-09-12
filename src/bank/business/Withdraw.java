package bank.business;

import java.text.SimpleDateFormat;

import bank.dao.AccountMasterDao;
import bank.dao.TransactionDao;
import bank.entity.AccountMaster;
import bank.entity.Transaction;
import bank.exception.InsufficientFundException;
import bank.exception.InvalidAccountException;

public class Withdraw implements IExecuteBusiness.ExecuteWithdraw{
	
	private AccountMasterDao iAccountMasterDao;
	private TransactionDao iTransactionDao;
	
	public Withdraw() {
		
		
		/*accountMasterDao=new AccountMasterDaoImpl();
		transactionDao=new TransactionDaoImpl();*/
	}
	
	public void setAccountMasterDao(AccountMasterDao accountMasterDao){
		this.iAccountMasterDao = accountMasterDao;
	}
	
	public void setTransactionDao(TransactionDao transactionDao){
		this.iTransactionDao = transactionDao;
	}
	
	public void Execute(int accno,double amount) throws InvalidAccountException, InsufficientFundException{
		
		java.util.Date date = new java.util.Date();
		String type = "Withdraw";

		AccountMaster accountMaster=iAccountMasterDao.retrieve(accno);
		double CurrentBalance = accountMaster.getBalance();
		
		if(CurrentBalance < amount){
			throw new InsufficientFundException("You don't have sufficient balance to withdraw ");
		}
				
		double NewBalance=CurrentBalance-amount;
		
		accountMaster.setAccno(accno);
		accountMaster.setBalance(NewBalance);
		iAccountMasterDao.update(accountMaster);
		
		Transaction transaction=new Transaction();
		transaction.setAccno(accno);
		transaction.setAmount(amount);
		transaction.setBalance(NewBalance);
		transaction.setDate(new SimpleDateFormat("yyyy/MM/dd").format(date));
		transaction.setType(type);
		
		iTransactionDao.insert(transaction);
		
	}

}

