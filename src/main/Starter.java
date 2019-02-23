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

import components.MainForm;
import components.MyMenuBar;
import configuration.ConfigurationGetter;
import runners.PrimeListThread;


public class Starter {
	
	private static final Logger logger = LogManager.getLogger(Starter.class.getName());
	
	private JFrame starterFrame;
	private PrimeListThread primeListReader = new PrimeListThread();
	private MainForm starterForm;
	private static Starter starter;
	private ConfigurationGetter configGetter = new ConfigurationGetter();
	
	
	// Starter Constructor
	public Starter() throws IOException {
		starterFrame = new JFrame( configGetter.getTitle() );
		starterFrame.setJMenuBar( new MyMenuBar(starterForm) );
		
		// Main guts of the operation occurs here
		starterForm = new MainForm( primeListReader, configGetter);
		
		// Adds icon to the JFrame
		addIconImage( configGetter.getIconFileLocation() );
		addFrameFormatting( starterFrame );
	}

	// Main Method
	public static void main(String[] args) {
		
		try {
			// Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			starter = new Starter();
			starter.loadPrimes();
		} catch (IOException e) {
			logger.error("Error when creating new Starter object", e);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	    	logger.error("Unable to setLookAndFeel with current OS.");
		}
	}

	// Gets the resource for the image to set the JFrame icon as
	private void addIconImage(String iconImageLocation) {

		try {
			URL resource = getClass().getResource(iconImageLocation);
			BufferedImage image = ImageIO.read(resource);
			starterFrame.setIconImage(image);
		} catch (IOException e) {
			logger.error("Error when reading image when setting the frame's icon", e);
		}
	}
	
	// Loads the prime numbers
	private void loadPrimes() {
		starter.getMainForm().disableGeneration();
		starter.getMainForm().setProgressString("Loading Primes...");
		
		primeListReader.startReadingPrimes();
		
		starter.getMainForm().setProgressString("Loaded Primes!");
		starter.getMainForm().enableGeneration();
	}
	
	// Adds formatting to JFrame and set Image Icon
	private void addFrameFormatting(JFrame frame) {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(starterForm);
		frame.pack();
	}

	public MainForm getMainForm() {
		return starterForm;
	}
}
