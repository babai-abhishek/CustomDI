package bank.business;

import java.util.ArrayList;

import bank.entity.AccountMaster;
import bank.entity.Transaction;
import bank.exception.InsufficientFundException;
import bank.exception.InvalidAccountException;

public interface IExecuteBusiness {
	
	public interface ExecuteBalanceCheck{
		public double Execute(int i) throws InvalidAccountException;
	}

	public interface ExecuteWithdraw{
		public void Execute(int accno,double amount) throws InvalidAccountException, InsufficientFundException;
	}
	
	public interface ExecuteBankStatement{
		public ArrayList<Transaction> Execute(int accno) throws InvalidAccountException, InsufficientFundException;
	}
	
	public interface ExecuteDeposit{
		public void Execute(int accno,double amount) throws InvalidAccountException, InsufficientFundException;
	}
	
	public interface ExecuteNewAccount{
		public void Execute(AccountMaster accountMaster) throws InvalidAccountException, InsufficientFundException;
	}
}
