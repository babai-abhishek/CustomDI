package bank.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import bank.business.IExecuteBusiness;
import bank.exception.InsufficientFundException;
import bank.exception.InvalidAccountException;
import org.xml.sax.SAXException;

import bank.exception.BadTypeException;
import bank.di.DiFactory;
import bank.business.IExecuteBusiness.ExecuteDeposit;

/**
 * Servlet implementation class DepositServlet
 */
@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			process(request,response);
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			process(request,response);
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		

		String accnoStr = request.getParameter("Accno");
		int accno = Integer.parseInt(accnoStr);

		String amountStr = request.getParameter("Amount");
		double amount = Double.parseDouble(amountStr);

		/*Deposit deposit=new Deposit();
		deposit.Execute(accno, amount);*/

		DiFactory diFactory = new DiFactory();
		IExecuteBusiness.ExecuteDeposit execute = null;
		try {	
			try {
				execute = (ExecuteDeposit) diFactory.getBusinessObject("Deposit");
			} catch (BadTypeException e) {
				
				e.printStackTrace();
			}			
		} catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		}
		try {
			execute.Execute(accno, amount);
		} catch (InvalidAccountException e) {
			
			e.printStackTrace();
		} catch (InsufficientFundException e) {
			
			e.printStackTrace();
		}

	}

}
