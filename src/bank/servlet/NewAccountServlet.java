package bank.servlet;

import java.io.BufferedReader;
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

import com.google.gson.Gson;

import bank.exception.BadTypeException;
import bank.di.DiFactory;
import bank.entity.AccountMaster;
import bank.business.IExecuteBusiness.ExecuteNewAccount;

/**
 * Servlet implementation class NewAccountServlet
 */
@WebServlet("/NewAccountServlet")
public class NewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		process(request,response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		

		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		Gson g = new Gson();
		AccountMaster accountMaster = g.fromJson(buffer.toString(), AccountMaster.class);

		DiFactory diFactory = new DiFactory();
		IExecuteBusiness.ExecuteNewAccount execute = null;
		try {	
			execute = (ExecuteNewAccount) diFactory.getBusinessObject("NewAccount");
		} catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (BadTypeException e) {
			
			e.printStackTrace();
		}
		try {
			execute.Execute(accountMaster);
		} catch (InvalidAccountException e) {
			
			e.printStackTrace();
		} catch (InsufficientFundException e) {
			
			e.printStackTrace();
		}

	}
}
