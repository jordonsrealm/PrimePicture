package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import components.MyMenuBar;
import configuration.ConfigurationGetter;
import frames.MainForm;
import runners.ReadingThread;


public class Starter {
	
	private static final Logger logger = LogManager.getLogger(Starter.class.getName());
	private JFrame frame = new JFrame("PrimePicture");
	private ReadingThread myReader = new ReadingThread();
	private MainForm form;
	private static Starter starter;
	private ConfigurationGetter configGetter;
	
	
	// Starter Constructor
	public Starter() throws IOException {
		
		configGetter = new ConfigurationGetter();
		
		// Sets the icon image for the JFrame
		addIconImage(configGetter.getIconFileLocation());
		
		// Adds the main form to the JFrame
		form = new MainForm(myReader, configGetter);
		
		// Add JMenuBar
		addMenuBar();
		
		// Add special formatting for JFrame and add image to the frame's icon
		addFormatting(frame);
	}


	// Main Method
	public static void main(String[] args) {
		
		setUpLookAndFeel();
		
		try {
			starter = new Starter();
			starter.loadPrimes();
		} catch (IOException e) {
			logger.error("Error when creating new Starter object", e);
		}
	}
	
	private void addMenuBar() {
		JMenuBar menuBar = new MyMenuBar(this.form);
		frame.setJMenuBar(menuBar);
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
	private void addIconImage(String iconImageLocation) {
		URL resource = getClass().getResource(iconImageLocation);
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
		myReader.startReadingPrimes();
		
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