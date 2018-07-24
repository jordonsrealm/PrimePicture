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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import configuration.ConfigurationGetter;
import drawingparameters.DrawingRunnableParameter;
import listeners.ColorChooserListener;
import listeners.MyAdapter;
import pictures.ImageHolder;
import runners.DrawingRunner;
import runners.ReadingThread;


public class MainForm extends JPanel implements ActionListener{

	static final Logger logger = LogManager.getLogger(MainForm.class.getName());
	private static final long serialVersionUID = 1L;
	
	private String primePicsDefaultLocation;
	private Color backGroundColor = null;

	private JTextField heightField;
	private JTextField widthField;
	transient MyAdapter adapter  = null;
	transient ColorChooserListener listener = null;
	
	private JButton chooser1 = new JButton("Ends with 1");
	private JButton chooser3 = new JButton("Ends with 3");
	private JButton chooser7 = new JButton("Ends with 7");
	private JButton chooser9 = new JButton("Ends with 9");

	private JLabel choose1Label = new JLabel();
	private JLabel choose3Label = new JLabel();
	private JLabel choose7Label = new JLabel();
	private JLabel choose9Label = new JLabel();
	
	private JButton generatePic    = new JButton("Create Picture");
	private JProgressBar progressBar = new JProgressBar();
	
	private transient DrawingRunner myDrawingRunner;
	private transient ReadingThread readingThread = null;
	private final transient  ConfigurationGetter configGetter;
	
	
	public MainForm(ReadingThread rThread, ConfigurationGetter configGetter) {
		this.listener = new ColorChooserListener(this);
		this.readingThread = rThread;
		this.configGetter = configGetter;
		primePicsDefaultLocation = configGetter.getDesktopFileLocation();
		
		makeForm();
		
		this.setPreferredSize(new Dimension(configGetter.getFormWidth(),configGetter.getFormHeight()));
	}
	
	private MainForm makeForm() {
		
		this.setLayout(new BorderLayout());
		
		createDirectoryForPictures();

        EmptyBorder border = new EmptyBorder(0,10,0,0);		//top,left,bottom,right
        setColorChoosers( border );
		
		generatePic.addActionListener(this);
		progressBar.setStringPainted(true);
		progressBar.setMaximum(100);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1,2));
		bottomPanel.add(generatePic);
		bottomPanel.add(progressBar);
		
		this.add( getMiddlepanel( border ), BorderLayout.CENTER );
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

	private Component getMiddlepanel( EmptyBorder border ) {
		int textFieldLength = configGetter.getTextFieldLength();
		MyAdapter myAdapter = new MyAdapter(this);
		widthField = new JTextField(textFieldLength);
		heightField = new JTextField(textFieldLength);
		widthField.addMouseListener( myAdapter );
		widthField.setBorder( border );
		heightField.addMouseListener( myAdapter );
		heightField.setBorder( border );
		
		JLabel hLabel = new JLabel("Height");
		JLabel wLabel = new JLabel("Width");
		hLabel.setBorder( border );
		wLabel.setBorder( border );
		
		JPanel middlePanel = new JPanel(new GridLayout(3,4));
		middlePanel.add(wLabel);
		middlePanel.add(widthField);
		middlePanel.add(hLabel);
		middlePanel.add(heightField);
		middlePanel.add(chooser1);
		middlePanel.add(choose1Label);
		middlePanel.add(chooser3);
		middlePanel.add(choose3Label);
		middlePanel.add(chooser7);
		middlePanel.add(choose7Label);
		middlePanel.add(chooser9);
		middlePanel.add(choose9Label);
		
		return middlePanel;
	}

	private void setColorChoosers(EmptyBorder border) {
        listener = new ColorChooserListener(this);
        chooser1.addActionListener(listener);
        chooser3.addActionListener(listener);
        chooser7.addActionListener(listener);
        chooser9.addActionListener(listener);

		choose1Label.setOpaque(true);		
		choose3Label.setOpaque(true);
		choose7Label.setOpaque(true);
		choose9Label.setOpaque(true);
		choose1Label.setBorder( border );
		choose3Label.setBorder( border );
		choose7Label.setBorder( border );
		choose9Label.setBorder( border );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String hTextFieldText = heightField.getText();
		String wTextFieldText = widthField.getText();
		
		// Checks for non numberic characters
		if( onlyNumbers(hTextFieldText) && onlyNumbers(wTextFieldText) ) {
			int heightSize = Integer.parseInt(hTextFieldText);
			int widthSize  = Integer.parseInt(wTextFieldText);
			
			BufferedImage buffImage = new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_4BYTE_ABGR);
			ImageHolder imageHolder = new ImageHolder( buffImage, listener.getPrimePalette(), primePicsDefaultLocation, 0 , readingThread.getMaxFileSize() );
			
			DrawingRunnableParameter parameter = new DrawingRunnableParameter(imageHolder, readingThread.getPrimes(), this);
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

	public JButton getChooser1() {
		return chooser1;
	}

	public JButton getChooser3() {
		return chooser3;
	}

	public JButton getChooser7() {
		return chooser7;
	}

	public JButton getChooser9() {
		return chooser9;
	}
	
	public JLabel getChoose1Label() {
		return choose1Label;
	}

	public JLabel getChoose3Label() {
		return choose3Label;
	}

	public JLabel getChoose7Label() {
		return choose7Label;
	}

	public JLabel getChoose9Label() {
		return choose9Label;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public DrawingRunner getMyDrawingRunner() {
		return myDrawingRunner;
	}
	
	public JButton getGeneratePicButton() {
		return generatePic;
	}
	
	public void setReadingThread(ReadingThread readThread) {
		this.readingThread = readThread;
	}
	
	public ReadingThread getReadingThread() {
		return this.readingThread;
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
	
	public void setProgressString(String str) {
		progressBar.setString(str);
	}
	
	public void setProgressValue(Integer value) {
		progressBar.setValue(value);
	}
	
	public void disableGeneration() {
		generatePic.setEnabled(false);
	}
	
	public void enableGeneration() {
		generatePic.setEnabled(true);
	}
}
