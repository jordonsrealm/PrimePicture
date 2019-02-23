package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TextEvaluator {

	static final Logger logger = LogManager.getLogger(TextEvaluator.class.getName());
	
	
	private TextEvaluator() {}
	
	public static boolean isTextOnly(String text) {
	    char[] chars = text.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	
	public static boolean isNumericOnly(String text) {
		try {
	        Double.parseDouble(text);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
}
