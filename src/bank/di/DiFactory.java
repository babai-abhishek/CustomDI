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
