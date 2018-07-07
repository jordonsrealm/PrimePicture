package configuration;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigurationGetter {

	public static final Logger logger = LogManager.getLogger(Logger.class.getName());
	private Map<String, String> properties;
	private static final String FORM_WIDTH = "FORM_WIDTH";
	private static final String FORM_HEIGHT = "FORM_HEIGHT";
	private static final String TEXT_FIELD_LENGTH = "TEXT_FIELD_LENGTH";
	private static final String ICON_FILE_LOCATION = "ICON_FILE_LOCATION";
	
	
	public ConfigurationGetter() throws IOException {
		setPropertyValues();
	}
	
	public void setPropertyValues() throws IOException {
		ConfigurationGetPropertyValues propertiesValue = new ConfigurationGetPropertyValues();
		this.properties = propertiesValue.getPropValues();
	}
	
	public int getTextWidth() {
		return Integer.parseInt(properties.get(FORM_WIDTH));
	}
	
	public int getTextHeight() {
		return Integer.parseInt(properties.get(FORM_HEIGHT));
	}

	public int getTextFieldLength() {
		return Integer.parseInt(properties.get(TEXT_FIELD_LENGTH));
	}
	
	public String getIconFileLocation() {
		return properties.get(ICON_FILE_LOCATION);
	}
}
