package containers;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import listeners.ColorChooserListener;


public class ComponentManager {
	
	private static final String GENERATE_PICTURE_TEXT = "Create Picture";
	private static final String ENDS_WITH_1_TEXT = "Ends with 1";
	private static final String ENDS_WITH_3_TEXT = "Ends with 3";
	private static final String ENDS_WITH_7_TEXT = "Ends with 7";
	private static final String ENDS_WITH_9_TEXT = "Ends with 9";
	
	private static JTextField heightField = new JTextField();
	private static JTextField widthField = new JTextField();
	
	private static JButton chooser1 = new JButton(ENDS_WITH_1_TEXT);
	private static JButton chooser3 = new JButton(ENDS_WITH_3_TEXT);
	private static JButton chooser7 = new JButton(ENDS_WITH_7_TEXT);
	private static JButton chooser9 = new JButton(ENDS_WITH_9_TEXT);

	private static JLabel choose1Label = new JLabel();
	private static JLabel choose3Label = new JLabel();
	private static JLabel choose7Label = new JLabel();
	private static JLabel choose9Label = new JLabel();
	
	private static JButton generatePic    = new JButton(GENERATE_PICTURE_TEXT);
	private static JProgressBar progressBar = new JProgressBar();
	private static ComponentManager manager;
	private static ColorChooserListener listener;
	
	
	private ComponentManager() {
		
	}
	
	public static ComponentManager getInstance() {
		if(manager == null) {
			manager = new ComponentManager();
			setOpaqueToLabels();
			addColorChooserListenerToButtons();
		}
		
		return manager;
	}
	
	public static ComponentManager getManager() {
		return manager;
	}

	public ColorChooserListener getColorChooserListener() {
		return listener;
	}
	
	public static void setOpaqueToLabels() {
		choose1Label.setOpaque(true);		
		choose3Label.setOpaque(true);
		choose7Label.setOpaque(true);
		choose9Label.setOpaque(true);
		
        EmptyBorder border = new EmptyBorder(0,10,0,0);		//top,left,bottom,right
		choose1Label.setBorder( border );
		choose3Label.setBorder( border );
		choose7Label.setBorder( border );
		choose9Label.setBorder( border );
	}
	
	private static void addColorChooserListenerToButtons() {
		listener = new ColorChooserListener(choose1Label, choose3Label, choose7Label, choose9Label);
        chooser1.addActionListener(listener);
        chooser3.addActionListener(listener);
        chooser7.addActionListener(listener);
        chooser9.addActionListener(listener);
	}
	
	public static String getGeneratePictureText() {
		return GENERATE_PICTURE_TEXT;
	}
	public static String getEndsWith1() {
		return ENDS_WITH_1_TEXT;
	}
	public static String getEndsWith3() {
		return ENDS_WITH_3_TEXT;
	}
	public static String getEndsWith7() {
		return ENDS_WITH_7_TEXT;
	}
	public static String getEndsWith9() {
		return ENDS_WITH_9_TEXT;
	}
	public JTextField getHeightField() {
		return heightField;
	}
	public JTextField getWidthField() {
		return widthField;
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
	public JButton getGeneratePic() {
		return generatePic;
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
}
