package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import primepicture.impl.Configurations;


public class ConfigurationGetter implements Configurations{

	public static final Logger logger = LogManager.getLogger(ConfigurationGetter.class.getName());
	private Map<String, String> properties;
	
	private static final String FORM_WIDTH = "FORM_WIDTH";
	private static final String FORM_HEIGHT = "FORM_HEIGHT";
	private static final String TEXT_FIELD_LENGTH = "TEXT_FIELD_LENGTH";
	private static final String DESKTOP_FILE_LOCATION = "DESKTOP_FILE_LOCATION";
	private static final String MAX_DIMENSION = "MAX_DIMENSION";
	private static final String ICON_FILE_LOCATION = "ICON_FILE_LOCATION";
	
	
	public ConfigurationGetter() throws IOException {
		Properties prop = new Properties();
		String propFileName = "config.properties";
		
		HashMap<String, String> newHash = null;
		
		try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {

			prop.load(inputStream);
 
			Date time = new Date(System.currentTimeMillis());
 
			newHash = new HashMap<>();
			
			String[] propertiesValues = ConfigurationGetPropertyValues.getProperties();
			
			String propertyValue = null;
			
			for(int ind = 0; ind < propertiesValues.length; ind++) {
				String propertyKey = propertiesValues[ind];
				propertyValue = prop.getProperty(propertyKey);
				newHash.put(propertyKey, propertyValue);
			}
			
			logger.info(propertyValue + "\nProgram Ran on " + time + " with configurations =" + propertyValue);
		} catch (Exception e) {
			logger.error("Exception caught when reading configuration file" , e);
		}

		this.properties = newHash;
	}
	@Override
	public int getFormWidth() {
		return Integer.parseInt(properties.get(FORM_WIDTH));
	}
	
	@Override
	public int getFormHeight() {
		return Integer.parseInt(properties.get(FORM_HEIGHT));
	}

	@Override
	public int getTextFieldLength() {
		return Integer.parseInt(properties.get(TEXT_FIELD_LENGTH));
	}

	@Override
	public String getIconFileLocation() {
		return properties.get(ICON_FILE_LOCATION);
	}
	
	@Override
	public String getDesktopFileLocation() {
		return System.getProperty("user.home") + properties.get(DESKTOP_FILE_LOCATION);
	}
	
	@Override
	public int getMaxDimension() {
		logger.debug("MaxDimension be", properties.get(MAX_DIMENSION));
		return Integer.parseInt(properties.get(MAX_DIMENSION));
	}
}
