package bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import bank.entity.AccountMaster;
import bank.entity.Transaction;
import bank.exception.InvalidAccountException;;

public class AccountMasterDaoImpl extends AbstractDao implements AccountMasterDao
{

	public void insert(AccountMaster accountMaster)
	{
		String name = accountMaster.getName();
		String date= accountMaster.getDate();
		String pan = accountMaster.getPan_id();
		String phone = accountMaster.getPhone();
		double amount = accountMaster.getBalance();
		int accno = 0;
		String type="Deposit";
		System.out.println(accountMaster.getName()+"  : "+accountMaster.getBalance()+"  : "+accountMaster.getPan_id());
		Connection con = this.getConnection();
		String query = "insert into AccountMaster(name,Phone_no,PAN_ID,balance,date) values(?,?,?,?,?)";
		
		String qry = "SELECT accno from AccountMaster where accno=(select MAX(accno) from AccountMaster)";
		
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setString(3, pan);
			pstmt.setDouble(4, amount);
			pstmt.setString(5, date);
			
			pstmt.execute();
			
			java.sql.Statement myStmt=con.createStatement();
			ResultSet rs = myStmt.executeQuery(qry);
			
			while(rs.next())
			{
				accno = rs.getInt("accno");
			}
			
			Transaction transaction=new Transaction();
			transaction.setAccno(accno);
			transaction.setAmount(amount);
			transaction.setBalance(amount);
			transaction.setDate(date.toString());
			transaction.setType(type);
			new TransactionDaoImpl().insert(transaction);

		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		
	}

	public void update(AccountMaster accountMaster)
	{
		Connection con = this.getConnection();
		int accno = accountMaster.getAccno();
		double currentBalance=accountMaster.getBalance();		
		String qry = "UPDATE AccountMaster set balance="+currentBalance+" WHERE accno="+accno+"";
				
		try {
			java.sql.Statement mystmt=con.createStatement();
			mystmt.executeUpdate(qry);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	
	public void delete(int accno)
	{
		
	}

	@Override
	public AccountMaster retrieve(int accno) throws InvalidAccountException {
		
		System.out.println("accno "+accno);
		AccountMaster accountMaster=null;
		Connection con = this.getConnection();
		System.out.println(accno);
		String sql = "select * from AccountMaster where Accno = "+accno+"";
		
		try {
			java.sql.Statement myStmt=con.createStatement();
			ResultSet rs = myStmt.executeQuery(sql);
			//System.out.println("here i am "+rs);
			if(!rs.isBeforeFirst()){
				System.out.println("no data found ");
				throw new InvalidAccountException("Invalid account no. ");
			}
			while(rs.next()){
				
				accountMaster=new AccountMaster();
				
				String Name = rs.getString("Name");
				String phone= rs.getString("Phone_no");
				String pan = rs.getString("PAN_ID");
				double balance = rs.getDouble("Balance");
				System.out.println(balance);
				String date = rs.getString("Date");
				
				accountMaster.setName(Name);
				accountMaster.setBalance(balance);
				accountMaster.setDate(date);
				accountMaster.setPan_id(pan);
				accountMaster.setPhone(phone);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
			return accountMaster;
		
	}
}
