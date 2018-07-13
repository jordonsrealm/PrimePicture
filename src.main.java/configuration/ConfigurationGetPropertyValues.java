package configuration;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigurationGetPropertyValues {
	
	public static final Logger logger = LogManager.getLogger(ConfigurationGetPropertyValues.class.getName());
	
	private static final String FORM_WIDTH = "FORM_WIDTH";
	private static final String FORM_HEIGHT = "FORM_HEIGHT";
	private static final String TEXT_FIELD_LENGTH = "TEXT_FIELD_LENGTH";
	private static final String DESKTOP_FILE_LOCATION = "DESKTOP_FILE_LOCATION";
	private static final String MAX_DIMENSION = "MAX_DIMENSION";
	private static final String ICON_FILE_LOCATION = "ICON_FILE_LOCATION";
	
	private ConfigurationGetPropertyValues() {
		/// 
	}
 
	public static String[] getProperties(){
		ArrayList<String> properties = new ArrayList<>();
		
		properties.add(FORM_WIDTH);
		properties.add(FORM_HEIGHT);
		properties.add(TEXT_FIELD_LENGTH);
		properties.add(DESKTOP_FILE_LOCATION);
		properties.add(MAX_DIMENSION);
		properties.add(ICON_FILE_LOCATION);
		
		return properties.toArray(new String[0]);
	}
}
