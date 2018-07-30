package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import components.MainForm;
import drawingparameters.ColorHolder;
import pictures.PrimePalette;


public class ColorChooserListener implements ActionListener {

	static final Logger logger = LogManager.getLogger(ColorChooserListener.class.getName());
	
	private Color primes1 = Color.WHITE;
	private Color primes3 = Color.WHITE;
	private Color primes7 = Color.WHITE;
	private Color primes9 = Color.WHITE;
	
	private JLabel label1 = null;
	private JLabel label3 = null; 
	private JLabel label7 = null; 
	private JLabel label9 = null;
	
	private Color backgroundColor = null;
	private MainForm mainForm = null;
	
	private String prime1Text = "Choose color for primes ending in '1'";
	private String prime3Text = "Choose color for primes ending in '3'";
	private String prime7Text = "Choose color for primes ending in '7'";
	private String prime9Text = "Choose color for primes ending in '9'";
	
	
	public ColorChooserListener(MainForm form) {
		this.mainForm = form;
		label1 = form.getChoose1Label();
		label3 = form.getChoose3Label();
		label7 = form.getChoose7Label();
		label9 = form.getChoose9Label();
		this.setBackgroundColor( new Color(0,0,0,0) );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = ((JButton) e.getSource() ).getText();
		String compareStr = buttonText.charAt(buttonText.length() - 1) + "";
		switch (compareStr){
		case "1": primes1 = JColorChooser.showDialog(mainForm, prime1Text, Color.WHITE);
				  this.label1.setBackground(primes1);
				  break;
		case "3": primes3 = JColorChooser.showDialog(mainForm, prime3Text, Color.WHITE);
				  this.label3.setBackground(primes3);
				  break;
		case "7": primes7 = JColorChooser.showDialog(mainForm, prime7Text, Color.WHITE);
				  this.label7.setBackground(primes7);
			      break;
		case "9": primes9 = JColorChooser.showDialog(mainForm, prime9Text, Color.WHITE);
				  this.label9.setBackground(primes9);
			      break;
		default:  mainForm.repaint();
		}
	}
	
	public PrimePalette getPrimePalette() {
		return new PrimePalette( new ColorHolder( primes1, primes3, primes7, primes9), this.backgroundColor);
	}

	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
