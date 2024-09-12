package bank.business;

import java.util.ArrayList;

import bank.dao.TransactionDao;
import bank.entity.Transaction;
import bank.business.IExecuteBusiness.ExecuteBankStatement;
import bank.exception.InsufficientFundException;
import bank.exception.InvalidAccountException;

public class BankStatement implements ExecuteBankStatement{

	private TransactionDao iTransactionDao;

	//DEPENDENCY INJECTED VIA set METHOD
	public void setTransactionDao(TransactionDao transactionDao){
		this.iTransactionDao = transactionDao;
	}

	@Override
	public ArrayList<Transaction> Execute(int accno) throws InvalidAccountException, InsufficientFundException {

		ArrayList<Transaction> transaction=iTransactionDao.retrieve(accno);

		return transaction;

	}


}
