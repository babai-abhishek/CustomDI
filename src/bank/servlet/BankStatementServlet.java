package bank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import bank.business.IExecuteBusiness;
import bank.exception.InsufficientFundException;
import bank.exception.InvalidAccountException;
import com.google.gson.Gson;
import org.xml.sax.SAXException;

import bank.exception.BadTypeException;
import bank.di.DiFactory;
import bank.entity.Transaction;
import bank.business.IExecuteBusiness.ExecuteBankStatement;

/**
 * Servlet implementation class StatementServlet
 */
@WebServlet("/StatementServlet")
public class BankStatementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			process(request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | BadTypeException e) {
			
			e.printStackTrace();
		}	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			process(request,response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | BadTypeException e) {
			
			e.printStackTrace();
		}	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, BadTypeException {
		

		String accnoStr=request.getParameter("Accno");
		int accno=Integer.parseInt(accnoStr);

		DiFactory diFactory = new DiFactory();
		IExecuteBusiness.ExecuteBankStatement execute = null;
		try {	
			execute = (ExecuteBankStatement) diFactory.getBusinessObject("BankStatement");
		} catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		}

		ArrayList<Transaction> transaction = null;
		try {
			transaction = execute.Execute(accno);
		} catch (InvalidAccountException e) {
			
			e.printStackTrace();
		} catch (InsufficientFundException e) {
			
			e.printStackTrace();
		}

		Gson gson = new Gson();
		String res = gson.toJson(transaction);		
		PrintWriter printWriter = response.getWriter();
		printWriter.println(res);

	}

}
