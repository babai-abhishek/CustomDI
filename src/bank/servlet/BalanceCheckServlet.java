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
import bank.exception.InvalidAccountException;
import org.xml.sax.SAXException;

import bank.exception.BadTypeException;
import bank.di.DiFactory;
import bank.business.IExecuteBusiness.ExecuteBalanceCheck;


@WebServlet("/BalanceCheckServlet")
public class BalanceCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws 
	ServletException, IOException {
		
		try {
			process(request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (InvalidAccountException e) {
			
			System.out.println("in servlet "+e);
		}
		catch (BadTypeException e){
			System.out.println(e);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			process(request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (InvalidAccountException e) {
			
			System.out.println("in servlet post "+e);
		} catch (BadTypeException e) {
			
			System.out.println(e);
		}
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException,
	InvalidAccountException, BadTypeException {

		// Get the user Data
		String accnoStr=request.getParameter("Accno");
		int accno=Integer.parseInt(accnoStr);
		
		DiFactory diFactory = new DiFactory();
		IExecuteBusiness.ExecuteBalanceCheck execute = null;
		try {	
			execute = (ExecuteBalanceCheck) diFactory.getBusinessObject("BalanceCheck");
		} catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		}
		double balance = execute.Execute(accno);
		System.out.println(balance);

	}


}
