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
import runners.ReadingThread;


public class Starter {
	
	private static final Logger logger = LogManager.getLogger(Starter.class.getName());
	
	private JFrame frame;
	private ReadingThread myReader = new ReadingThread();
	private MainForm form;
	private static Starter starter;
	private ConfigurationGetter configGetter;
	
	
	// Starter Constructor
	public Starter() throws IOException {
		configGetter = new ConfigurationGetter();
		frame = new JFrame( configGetter.getTitle() );
		addIconImage( configGetter.getIconFileLocation() );
		form = new MainForm(myReader, configGetter);
		frame.setJMenuBar( new MyMenuBar(this.form) );
		addFormatting( frame );
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
			frame.setIconImage(image);
		} catch (IOException e) {
			logger.error("Error when reading image when setting the frame's icon", e);
		}
	}
	
	// Loads the prime numbers
	private void loadPrimes() {
		starter.getForm().disableGeneration();
		starter.getForm().setProgressString("Loading Primes...");
		
		myReader.startReadingPrimes();
		
		starter.getForm().setProgressString("Loaded Primes!");
		starter.getForm().setReadingThread(myReader);
		starter.getForm().enableGeneration();
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
	
	// Gets the Reader object that has the thread used for processing the text file containing the list of prime numbers
	public ReadingThread getMyReader() {
		return myReader;
	}

	public void setMyReader(ReadingThread myReader) {
		this.myReader = myReader;
	}

	public MainForm getForm() {
		return form;
	}

	public void setForm(MainForm form) {
		this.form = form;
	}
}
