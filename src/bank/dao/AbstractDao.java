package bank.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDao {
	
	
	
	public Connection getConnection(){
		
		java.sql.Connection myCon = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			myCon = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/myBank",
					"root","");
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			
			return myCon;
		}
		
		
	}
	

}
