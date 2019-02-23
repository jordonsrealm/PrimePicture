package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import configuration.ConfigurationGetter;
import containers.ComponentManager;
import listeners.ColorChooserListener;
import listeners.MyMouseListener;
import runners.DrawingRunner;
import runners.PrimeListThread;


public class MainForm extends JPanel implements ActionListener{

	static final Logger logger = LogManager.getLogger(MainForm.class.getName());
	private static final long serialVersionUID = 1L;
	
	private String primePicsDefaultLocation;
	private Color backGroundColor = null;

	transient MyMouseListener adapter  = null;
	transient ColorChooserListener listener = null;
	
	private transient DrawingRunner myDrawingRunner;
	private transient PrimeListThread primeListThread = null;
	private final transient  ConfigurationGetter configGetter;
	
	
	public MainForm(PrimeListThread rThread, ConfigurationGetter configGetter) {
		this.listener = getComponentManager().getListener();
		this.primeListThread = rThread;
		this.configGetter = configGetter;
		primePicsDefaultLocation = configGetter.getDesktopFileLocation();
		
		makeForm();
		
		this.setPreferredSize(new Dimension(configGetter.getFormWidth(),configGetter.getFormHeight()));
	}
	
	private MainForm makeForm() {
		
		this.setLayout(new BorderLayout());
		
		createDirectoryForPictures();
		
		getComponentManager().getGeneratePic().addActionListener(this);
		getComponentManager().getProgressBar().setStringPainted(true);
		getComponentManager().getProgressBar().setMaximum(100);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1,2));
		bottomPanel.add(getComponentManager().getGeneratePic());
		bottomPanel.add(getComponentManager().getProgressBar());
		
		this.add( getMiddlepanel(), BorderLayout.CENTER );
		this.add( bottomPanel, BorderLayout.SOUTH );
		
		return this;
	}
	
	private void createDirectoryForPictures() {
		Path path = Paths.get(primePicsDefaultLocation);
		
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories( path );
            } catch (IOException e) {
                logger.error("Unable to create directories");
            }
        }
	}

	private Component getMiddlepanel() {
        EmptyBorder border = new EmptyBorder(0,10,0,0);		//top,left,bottom,right
		MyMouseListener myAdapter = new MyMouseListener(this);
		getComponentManager().getWidthField().addMouseListener( myAdapter );
		getComponentManager().getWidthField().setBorder( border );
		getComponentManager().getHeightField().addMouseListener( myAdapter );
		getComponentManager().getHeightField().setBorder( border );
		
		JLabel hLabel = new JLabel("Height");
		JLabel wLabel = new JLabel("Width");
		hLabel.setBorder( border );
		wLabel.setBorder( border );
		
		JPanel middlePanel = new JPanel(new GridLayout(3,4));
		middlePanel.add(wLabel);
		middlePanel.add(getComponentManager().getWidthField());
		middlePanel.add(hLabel);
		middlePanel.add(getComponentManager().getHeightField());
		middlePanel.add(getComponentManager().getChooser1());
		middlePanel.add(getComponentManager().getChoose1Label());
		middlePanel.add(getComponentManager().getChooser3());
		middlePanel.add(getComponentManager().getChoose3Label());
		middlePanel.add(getComponentManager().getChooser7());
		middlePanel.add(getComponentManager().getChoose7Label());
		middlePanel.add(getComponentManager().getChooser9());
		middlePanel.add(getComponentManager().getChoose9Label());
		
		return middlePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String hTextFieldText = getComponentManager().getHeightField().getText();
		String wTextFieldText = getComponentManager().getWidthField().getText();
		
		// Checks for non numberic characters
		if( onlyNumbers(hTextFieldText) && onlyNumbers(wTextFieldText) ) {
			int heightSize = Integer.parseInt(hTextFieldText);
			int widthSize  = Integer.parseInt(wTextFieldText);
			
			BufferedImage buffImage = new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_4BYTE_ABGR);
			ImageHolder imageHolder = new ImageHolder( buffImage, listener.getPrimePalette(), primePicsDefaultLocation, 0);
			
			DrawingRunnableParameter parameter = new DrawingRunnableParameter(imageHolder, primeListThread.getPrimes(), this);
			myDrawingRunner = new DrawingRunner(parameter);
			myDrawingRunner.startDrawingPicture();
		}
	}
	
	
	private boolean onlyNumbers(String text) {
		// Empty string check
		if(text.length() == 0) {
			logger.debug("No input value for text field");
			return false;
		}
		if( !text.matches(".*\\D.*") && Integer.parseInt(text) > configGetter.getMaxDimension()) {
			logger.debug("Input value is too large to process");
			return false;
		} else {
			return true;
		}
	}
	
	public DrawingRunner getMyDrawingRunner() {
		return myDrawingRunner;
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

	public MyMouseListener getAdapter() {
		return adapter;
	}

	public ColorChooserListener getListener() {
		return listener;
	}

	public ComponentManager getComponentManager() {
		return ComponentManager.getInstance();
	}

	public PrimeListThread getPrimeListThread() {
		return primeListThread;
	}
	
	public void disableGeneration() {
		getComponentManager().getGeneratePic().setEnabled(false);
	}
	
	public void enableGeneration() {
		getComponentManager().getGeneratePic().setEnabled(true);
	}
	
	public void setProgressString(String progress) {
		getComponentManager().getProgressBar().setString(progress);
	}
}
