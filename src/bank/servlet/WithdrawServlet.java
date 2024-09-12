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
import bank.business.IExecuteBusiness.ExecuteWithdraw;

/**
 * Servlet implementation class WithdrawServlet
 */
@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			process(request,response);
		} catch (InvalidAccountException e) {
			
			e.printStackTrace();
		}
		catch (InsufficientFundException e) {
			
			System.out.println(e);
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (BadTypeException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			process(request,response);
		} catch (InvalidAccountException e) {
			
			e.printStackTrace();
		} catch (InsufficientFundException e) {
			
			System.out.println(e);
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (BadTypeException e) {
			
			e.printStackTrace();
		}
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws InvalidAccountException, InsufficientFundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, BadTypeException {
		
		
		String accnoStr = request.getParameter("Accno");
		int accno = Integer.parseInt(accnoStr);
		
		String amountStr = request.getParameter("Amount");
		double amount = Double.parseDouble(amountStr);
		
		DiFactory diFactory = new DiFactory();
		Object obj = null;
		IExecuteBusiness.ExecuteWithdraw execute = null;
		try {	
			execute = (ExecuteWithdraw) diFactory.getBusinessObject("Withdraw");
		} catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| ParserConfigurationException | SAXException e1) {
			
			e1.printStackTrace();
		}
		execute.Execute(accno, amount);		
	}
}
