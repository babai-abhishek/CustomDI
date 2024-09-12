# Custom Dependency Injection Framework

This project demonstrates a custom dependency injection (DI) framework using XML configuration and Java Reflection. The framework allows for flexible management of dependencies and business class instantiation and is integrated into a web application using servlets.

## Overview

The custom DI framework includes the following components:

1. **XML Configuration File** (`Conf.xml`): Defines business operations, their corresponding business classes, and the helper classes required by them.
2. **XML Parser** (`XmlParser.java`): Parses the XML file to extract business class information and helper classes.
3. **Dependency Injection Factory** (`DiFactory.java`): Instantiates business objects and injects their dependencies as defined in the XML configuration.
4. **Servlet Class** (`BalanceCheckServlet.java`): Demonstrates how to use the DI framework in a web application context.

## XML Configuration

The XML configuration file (`Conf.xml`) specifies the business scenarios and their dependencies. Here’s the structure:

```xml
<?xml version="1.0"?>
<request>
    <request type="BalanceCheck" business-class="bank.business.BalanceCheck">
        <helper-class type="bank.dao.AccountMasterDao">bank.dao.AccountMasterDaoImpl</helper-class>
    </request>
    <request type="Deposit" business-class="bank.business.Deposit">
        <helper-class type="bank.dao.AccountMasterDao">bank.dao.AccountMasterDaoImpl</helper-class>
        <helper-class type="bank.dao.TransactionDao">bank.dao.TransactionDaoImpl</helper-class>
    </request>
    <request type="BankStatement" business-class="bank.business.BankStatement">
        <helper-class type="bank.dao.TransactionDao">bank.dao.TransactionDaoImpl</helper-class>
    </request>
    <request type="Withdraw" business-class="bank.business.Withdraw">
        <helper-class type="bank.dao.AccountMasterDao">bank.dao.AccountMasterDaoImpl</helper-class>
        <helper-class type="bank.dao.TransactionDao">bank.dao.TransactionDaoImpl</helper-class>
    </request>
    <request type="NewAccount" business-class="bank.business.NewAccount">
        <helper-class type="bank.dao.AccountMasterDao">bank.dao.AccountMasterDaoImpl</helper-class>
    </request>
</request>

```
Each `<request>` element contains:

- `type`: A unique identifier for the request.
- `business-class`: Fully qualified name of the business class.
- `<helper-class>`: Specifies the type (interface) and implementation (class) of the helper class.

## Components

### XmlParser

**Purpose**: Reads and processes the XML configuration to extract relevant business class and dependency information.

**Key Method**:

- **`getBusinessScenario(String tag)`**: Returns a `BusinessScenario` object containing:
    - **`businessClassName`**: Fully qualified name of the business class.
    - **`helperClassNames`**: List of fully qualified names of helper classes.
    - **`helperClassInterfaceType`**: List of interfaces or abstract classes implemented by the helper classes.

```java
package bank.di;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import bank.entity.BusinessSceinario;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser {
    String businessClassName = null;

    public BusinessSceinario getBusinessSceinario(String tag) throws ParserConfigurationException, SAXException {
        final ArrayList<String> helperClassName = new ArrayList<>();
        final ArrayList<String> helperClassInterfaceType = new ArrayList<>();
        String requestType = tag;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        final SAXParser saxParser = factory.newSAXParser();

        final DefaultHandler defaultHandler = new DefaultHandler() {
            boolean businessClass = false;
            boolean helperClass = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equalsIgnoreCase("request") && requestType.equalsIgnoreCase(attributes.getValue("type"))) {
                    businessClassName = attributes.getValue("business-class");
                    businessClass = true;
                } else if (qName.equalsIgnoreCase("helper-class")) {
                    helperClass = true;
                    if (businessClass) {
                        helperClassInterfaceType.add(attributes.getValue("type"));
                    }
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (helperClass && businessClass) {
                    helperClassName.add(new String(ch, start, length));
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (qName.equalsIgnoreCase("helper-class")) {
                    helperClass = false;
                } else if (qName.equalsIgnoreCase("request")) {
                    businessClass = false;
                }
            }
        };

        try {
            URL confFileLocationUrl = getClass().getResource("Conf.xml");
            saxParser.parse(confFileLocationUrl.toString(), defaultHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BusinessSceinario businessSceinario = new BusinessSceinario();
        businessSceinario.setBusinessClass(businessClassName);
        businessSceinario.setHelperClasses(helperClassName);
        businessSceinario.setHelperClassTypes(helperClassInterfaceType);
        return businessSceinario;
    }
}
```
## DiFactory

**Purpose**: Provides business class objects with dependencies injected as per the XML configuration.

**Key Method**:

- **`getBusinessObject(String type)`**: Instantiates the business class and injects its dependencies.

**Injection Logic**:
- Uses Reflection to create instances of the business and helper classes.
- Injects helper class instances into the business class using setter methods.

**Exception Handling**:
- **`BadTypeException`**: Thrown if a helper class does not implement the required interface.

**Code Example**:

```java
package bank.di;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import bank.entity.BusinessSceinario;
import bank.exception.BadTypeException;
import org.xml.sax.SAXException;

public class DiFactory {

	// return the business class object against the tag
	public Object getBusinessObject(String type) throws ParserConfigurationException, SAXException,
			InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, BadTypeException {

		XmlParser xmlParser = new XmlParser();
		BusinessSceinario obj = xmlParser.getBusinessSceinario(type);

		String businessClass = obj.getBusinessClass();
		ArrayList<String> helperClassNames = obj.getHelperClasses();
		ArrayList<String> helperClassTypes = obj.getHelperClassTypes();

		Class<?> bClass = Class.forName(businessClass);
		Object businessClassObject = bClass.newInstance();

		for (int i = 0; i < helperClassNames.size(); i++) {
			Class<?> helperClass = Class.forName(helperClassNames.get(i));
			Class<?> helperInterface = Class.forName(helperClassTypes.get(i));

			// check the helper-class has implemented the specified type
			if (!helperInterface.isAssignableFrom(helperClass)) 
			{
				// if check fails , throw BadTypeException
				throw new BadTypeException("Bad interface type "+helperInterface.getName());
			}
			Method setMethod = businessClassObject.getClass().getDeclaredMethod("set" + helperInterface.getSimpleName(),
					helperInterface);
			setMethod.invoke(businessClassObject, helperClass.newInstance());
			
		}
		return businessClassObject;

	}
}
```

## BalanceCheckServlet

**Purpose**: Demonstrates the use of the custom DI framework within a servlet-based web application.

**Key Methods**:

- **`doGet(HttpServletRequest request, HttpServletResponse response)`**: Handles HTTP GET requests. It processes the request by invoking the `process` method.
- **`doPost(HttpServletRequest request, HttpServletResponse response)`**: Handles HTTP POST requests. It also invokes the `process` method.

**`process` Method**:

**Purpose**: Retrieves account information from the request, uses `DiFactory` to get an instance of the `BalanceCheck` business class, and executes the balance check.

**Steps**:
1. Reads the account number from the HTTP request.
2. Uses `DiFactory` to instantiate the `BalanceCheck` business class.
3. Calls the `execute` method on the business class to get the balance.
4. Prints the balance to the console.

## Interaction with DiFactory

The servlet interacts with `DiFactory` to obtain and configure an instance of the business class (in this case, `BalanceCheck`). Below is a detailed description of this interaction:

1. **Retrieves Business Object**:
    - The `getBusinessObject` method in `DiFactory` is called with the request type `"BalanceCheck"`.
    - This method is responsible for locating and instantiating the required business class based on the type provided.

2. **Instantiates Business Class**:
    - `DiFactory` uses the XML configuration to determine the fully qualified name of the `BalanceCheck` class.
    - Using Reflection, `DiFactory` creates an instance of the `BalanceCheck` class dynamically.

3. **Injects Dependencies**:
    - After creating the `BalanceCheck` instance, `DiFactory` resolves and injects any required dependencies (e.g., `AccountMasterDao`).
    - Dependencies are injected by invoking the appropriate setter methods on the `BalanceCheck` instance.

4. **Result**:
    - The servlet receives a fully configured instance of `BalanceCheck`.
    - The servlet then uses this instance to perform the balance check operation, leveraging the injected dependencies to execute its business logic.


**Code Example**:

```java
package bank.servlet;

import bank.di.DiFactory;
import bank.entity.BusinessSceinario;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BalanceCheckServlet extends HttpServlet {

    private DiFactory diFactory = new DiFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Read account number from request
        String accountNumber = request.getParameter("accountNumber");

        // Instantiate BalanceCheck business class using DiFactory
        try {
            BalanceCheck balanceCheck = (BalanceCheck) diFactory.getBusinessObject("BalanceCheck");

            // Execute the balance check
            double balance = balanceCheck.execute(accountNumber);

            // Print the balance to the console
            System.out.println("Account Balance: " + balance);
            
            // Optionally, write the balance to the response
            response.setContentType("text/plain");
            response.getWriter().write("Account Balance: " + balance);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }
}
```
## Benefits

- **Decoupling**: Separates business logic from dependency management, promoting a clean separation of concerns and easier maintenance.
- **Flexibility**: Easily change dependencies by updating the XML configuration without modifying the codebase, allowing for more adaptable and scalable applications.
- **Integration**: Seamlessly integrates with servlet-based web applications, enabling effective request handling and business logic management within a web context.
