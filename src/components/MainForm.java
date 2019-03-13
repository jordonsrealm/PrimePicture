package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import configuration.ConfigurationGetter;
import containers.ComponentManager;
import listeners.ColorChooserListener;
import listeners.MyMouseListener;
import runners.DrawingRunner;
import runners.PrimeListThread;


public class MainForm extends JPanel {
	private static final Logger logger = LogManager.getLogger(MainForm.class.getName());
	
	private static final long serialVersionUID = 1L;
	
	private String primePicsDefaultLocation;
	private Color backGroundColor = null;

	transient MyMouseListener myMouseListener  = null;
	transient ColorChooserListener myColorChooserListener = null;
	
	private transient DrawingRunner myDrawingRunner;
	private transient PrimeListThread primeListThread = null;
	private final transient  ConfigurationGetter configGetter;
	
	
	public MainForm(PrimeListThread rThread, ConfigurationGetter configGetter) {
		this.primeListThread = rThread;
		this.configGetter = configGetter;
		this.myColorChooserListener = getComponentManager().getColorChooserListener();
		this.primePicsDefaultLocation = configGetter.getDesktopFileLocation();

		Path path = Paths.get(primePicsDefaultLocation);
		
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories( path );
            } catch (IOException e) {
                logger.error("Unable to create directories");
            }
        }

		this.setPreferredSize(new Dimension(configGetter.getFormWidth(),configGetter.getFormHeight()));
		this.setLayout(new BorderLayout());
		this.add( getMiddlepanel(), BorderLayout.CENTER );
		this.add( getBottomPanel(), BorderLayout.SOUTH );
	}

	private Component getMiddlepanel() {
		return new MyMiddlePanel(getComponentManager()).getMiddlePanel();
	}
	
	private Component getBottomPanel() {
		return new MyBottomPanel(this).getBottomPanel();
	}
	
	public DrawingRunner getMyDrawingRunner() {
		return myDrawingRunner;
	}
	
	public void setMyDrawingRunner(DrawingRunner runner) {
		this.myDrawingRunner = runner;
	}
	
	public void setReadingThread(PrimeListThread readThread) {
		this.primeListThread = readThread;
	}
	
	public PrimeListThread getReadingThread() {
		return this.primeListThread;
	}

	public Color getBackGroundColor() {
		return backGroundColor;
	}

	public void setBackGroundColor(Color backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public ConfigurationGetter getConfigGetter() {
		return configGetter;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrimePicsDefaultLocation() {
		return primePicsDefaultLocation;
	}

	public MyMouseListener getMyMouseListener() {
		return myMouseListener;
	}

	public ColorChooserListener getColorChooserListener() {
		return myColorChooserListener;
	}

	public ComponentManager getComponentManager() {
		return ComponentManager.getInstance();
	}

	public PrimeListThread getPrimeListThread() {
		return primeListThread;
	}
	
	public void disablePictureGeneration() {
		getComponentManager().getGeneratePic().setEnabled(false);
	}
	
	public void enablePictureGeneration() {
		getComponentManager().getGeneratePic().setEnabled(true);
	}
	
	public void setProgressString(String progress) {
		getComponentManager().getProgressBar().setString(progress);
	}
}
