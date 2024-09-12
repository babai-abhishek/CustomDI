package bank.business;

import java.text.SimpleDateFormat;

import bank.dao.AccountMasterDao;
import bank.entity.AccountMaster;
import bank.business.IExecuteBusiness.ExecuteNewAccount;
/*import bank.dao.TransactionDao;
import bank.dao.TransactionDaoImpl;*/

public class NewAccount implements ExecuteNewAccount{
	
	private AccountMasterDao accountMasterDao;

	public void setAccountMasterDao(AccountMasterDao accountMasterDao){
		this.accountMasterDao = accountMasterDao;
	}

	public void Execute(AccountMaster account ){
		
		java.util.Date date = new java.util.Date();
		
		AccountMaster accountMaster = new AccountMaster();
		accountMaster.setBalance(account.getBalance());
		accountMaster.setDate(new SimpleDateFormat("yyyy/MM/dd").format(date));
		accountMaster.setPan_id(account.getPan_id());
		accountMaster.setPhone(account.getPhone());
		accountMaster.setName(account.getName());
		accountMasterDao.insert(accountMaster);

	}

}
