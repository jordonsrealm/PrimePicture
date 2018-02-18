package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
import adapters.MyAdapter;
import drawingparameters.DrawingRunnableParameter;
import listeners.ColorChooserListener;
import runners.DrawingRunner;
import runners.ReadingThread;
import runners.UpdatingGUI;


public class MainForm extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	static final Logger logger = LogManager.getLogger(Logger.class.getName());
	
	private static final String PRIME_PICS_HD = System.getProperty("user.home") + "/Desktop" + "\\PrimePicForVideo\\";
	private static final int FORM_WIDTH = 400;
	private static final int FORM_HEIGHT = 150;
	
	JTextField heightField = new JTextField(20);
	JTextField widthField  = new JTextField(20);
	transient MyAdapter adapter  = null;
	transient ColorChooserListener listener = null;
	
	JButton chooser1 = new JButton("Ends with 1");
	JButton chooser3 = new JButton("Ends with 3");
	JButton chooser7 = new JButton("Ends with 7");
	JButton chooser9 = new JButton("Ends with 9");

	JLabel choose1Label = new JLabel();
	JLabel choose3Label = new JLabel();
	JLabel choose7Label = new JLabel();
	JLabel choose9Label = new JLabel();
	
	JButton generatePic    = new JButton("Create Picture");
	JProgressBar progressBar = new JProgressBar();
	
	transient DrawingRunner myDrawingRunner;
	transient ReadingThread readingThread = null;
	
	
	public MainForm(ReadingThread rThread) {
		listener = new ColorChooserListener(this);
		readingThread = rThread;
		
		makeForm();
		
		this.setPreferredSize(new Dimension(FORM_WIDTH,FORM_HEIGHT));
	}
	
	private MainForm makeForm() {
		
		Path path = Paths.get(PRIME_PICS_HD);
		
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
                logger.info("Creating directory now");
            } catch (IOException e) {
                logger.error("Unable to create directories");
            }
        }
        
        listener = new ColorChooserListener(this);
        
        chooser1.addActionListener(listener);
        chooser3.addActionListener(listener);
        chooser7.addActionListener(listener);
        chooser9.addActionListener(listener);
		
		choose1Label.setOpaque(true);
		choose1Label.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		choose3Label.setOpaque(true);
		choose3Label.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		choose7Label.setOpaque(true);
		choose7Label.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		choose9Label.setOpaque(true);
		choose9Label.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		
		JPanel middlePanel = new JPanel(new GridLayout(3,4));
		JPanel bottomPanel = new JPanel(new GridLayout(1,2));
		
		widthField.addMouseListener(new MyAdapter(this));
		widthField.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		heightField.addMouseListener(new MyAdapter(this));
		heightField.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		//widthField.setBorder(BorderFactory.createTitledBorder("Width"));
		//heightField.setBorder(BorderFactory.createTitledBorder("Height"));
		
		JLabel hLabel = new JLabel("Height");
		hLabel.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		JLabel wLabel = new JLabel("Width");
		wLabel.setBorder(new EmptyBorder(0,10,0,0));//top,left,bottom,right
		
		middlePanel.add(hLabel);
		middlePanel.add(heightField);
		middlePanel.add(wLabel);
		middlePanel.add(widthField);
		
		middlePanel.add(chooser1);
		middlePanel.add(choose1Label);
		middlePanel.add(chooser3);
		middlePanel.add(choose3Label);
		middlePanel.add(chooser7);
		middlePanel.add(choose7Label);
		middlePanel.add(chooser9);
		middlePanel.add(choose9Label);
		
		generatePic.addActionListener(this);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		
		bottomPanel.add(generatePic);
		bottomPanel.add(progressBar);
		
		this.setLayout(new BorderLayout());
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		return this;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String hTextFieldText = heightField.getText();
		String wTextFieldText = widthField.getText();
		
		//
		// Checks for non numberic characters
		//
		if(onlyNumbers(hTextFieldText) && onlyNumbers(wTextFieldText)) {
			int heightSize = Integer.parseInt(hTextFieldText);
			int widthSize  = Integer.parseInt(wTextFieldText);
			
			logger.info("Picture size: {} x {} is the width and {}" + " is the prime size", widthSize, heightSize, readingThread != null);
			
			DrawingRunnableParameter parameter = new DrawingRunnableParameter(new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_4BYTE_ABGR), 
																			  new Dimension(widthSize, heightSize), 
																			  readingThread.getPrimes(), 
																			  this, 
																			  listener.getPrimePalette());
			
			File primePictureFile = new File(PRIME_PICS_HD + "PrimePicture_" + widthSize + "_" + heightSize);
			myDrawingRunner = new DrawingRunner(parameter, primePictureFile, 0, readingThread.getMaxFileSize());
			UpdatingGUI updateGUI = new UpdatingGUI(this, primePictureFile);
			
			updateGUI.startUpdating();
			myDrawingRunner.startDrawingPicture();
		}
		
	}
	
	
	private boolean onlyNumbers(String text) {
		// Empty string check
		if(text.length() == 0) {
			logger.debug("No input value for text field");
			return false;
		}
		if( !text.matches(".*\\D.*") && Integer.parseInt(text) > 15000) {
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

}
