package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import frames.MainForm;
import runners.ReadingThread;


public class Starter {
	
	private static final Logger logger = LogManager.getLogger(Logger.class.getName());
	
	private static final String ONE_MILLION_PRIMES = "res/OneMillionPrimes.txt";
	private static final String ICON_FILE_LOCATION = "/res/primePictureIcon.jpg";
	private JFrame frame = new JFrame("PrimePicture");
	private ReadingThread myReader = null;
	private MainForm form;
	private static Starter starter;
	
	
	// Starter Constructor
	public Starter() {
		
		// Sets the icon image for the JFrame
		addIconImage();
		
		// Adds the main form to the JFrame
		form = new MainForm(myReader);
		
		// Add special formatting for JFrame and add image to the frame's icon
		addFormatting(frame);
	}
	
	// Main Method
	public static void main(String[] args) {
		setUpLookAndFeel();
		starter = new Starter();
		starter.loadPrimes();
	}
	
	private static void setUpLookAndFeel() {
		 try {
	        // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	    } 
	    catch (UnsupportedLookAndFeelException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {
	       // handle exception
	    }
	}

	// Gets the resource for the image to set the JFrame icon as
	private void addIconImage() {
		URL resource = getClass().getResource(ICON_FILE_LOCATION);
		BufferedImage image = null;
		try {
			image = ImageIO.read(resource);
			frame.setIconImage(image);
			logger.info("Frame's icon set");
		} catch (IOException e) {
			logger.error("Error when reading image when setting the frame's icon", e);
		}
	}
	
	
	// Loads the prime numbers
	private void loadPrimes() {
		starter.form.getGeneratePicButton().setEnabled(false);
		starter.form.getProgressBar().setString("Loading Primes...");
		try {
			ClassLoader loader = Starter.class.getClassLoader();
			URL url = loader.getResource(ONE_MILLION_PRIMES);
			myReader = new ReadingThread(url.openStream());
			myReader.startReadingPrimes();
		} catch (IOException e) {
			logger.debug("Error getting text file", e);
		}
		
		starter.form.getProgressBar().setString("Loaded Primes!");
		starter.form.setReadingThread(myReader);
		starter.form.getGeneratePicButton().setEnabled(true);
	}
	
	// Adds formatting to JFrame and set Image Icon
	private void addFormatting(JFrame frame) {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(form);
		frame.pack();
	}
	
	// Gets the Reader object that has the thread used for processing 
	// the text file containing the list of prime numbers
	public ReadingThread getReader() {
		return myReader;
	}
}
