package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigurationGetPropertyValues {
	
	public static final Logger logger = LogManager.getLogger(Logger.class.getName());

	String formWidth;
	String formHeight;
	String textFieldLength;
	String iconFileLocation;
	
	private static final String FORM_WIDTH = "FORM_WIDTH";
	private static final String FORM_HEIGHT = "FORM_HEIGHT";
	private static final String TEXT_FIELD_LENGTH = "TEXT_FIELD_LENGTH";
	private static final String ICON_FILE_LOCATION = "ICON_FILE_LOCATION";
 
	public Map<String, String> getPropValues() throws IOException {
		Properties prop = new Properties();
		String propFileName = "config.properties";
		
		HashMap<String, String> newHash = null;
		
		try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {

			prop.load(inputStream);
 
			Date time = new Date(System.currentTimeMillis());
 
			newHash = new HashMap<>();
			
			formWidth = prop.getProperty(FORM_WIDTH);
			newHash.put(FORM_WIDTH, formWidth);
			
			formHeight = prop.getProperty(FORM_HEIGHT);
			newHash.put(FORM_HEIGHT, formHeight);
			
			textFieldLength = prop.getProperty(TEXT_FIELD_LENGTH);
			newHash.put(TEXT_FIELD_LENGTH, textFieldLength);
			
			iconFileLocation = prop.getProperty(ICON_FILE_LOCATION);
			newHash.put(ICON_FILE_LOCATION, iconFileLocation);
			
			logger.info(formWidth + "\nProgram Ran on " + time + " with configurations =" + formWidth);
		} catch (Exception e) {
			logger.error("Exception caught when reading configuration file" , e);
		}
		
		return newHash;
	}
}
