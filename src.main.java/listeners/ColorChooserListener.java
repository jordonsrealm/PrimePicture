package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import frames.MainForm;
import palettes.PrimePalette;


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
	
	
	public ColorChooserListener(MainForm form) {
		this.mainForm = form;
		label1 = form.getChoose1Label();
		label3 = form.getChoose3Label();
		label7 = form.getChoose7Label();
		label9 = form.getChoose9Label();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = ((JButton) e.getSource() ).getText();
		
		String compareStr = buttonText.charAt(buttonText.length() - 1) + "";
		
		switch (compareStr){
		case "1": primes1 = JColorChooser.showDialog(mainForm, "Choose color for primes ending in '1'", Color.WHITE);
				  this.label1.setBackground(primes1);
				  break;
		case "3": primes3 = JColorChooser.showDialog(mainForm, "Choose color for primes ending in '3'", Color.WHITE);
				  this.label3.setBackground(primes3);
				  break;
		case "7": primes7 = JColorChooser.showDialog(mainForm, "Choose color for primes ending in '7'", Color.WHITE);
				  this.label7.setBackground(primes7);
			      break;
		case "9": primes9 = JColorChooser.showDialog(mainForm, "Choose color for primes ending in '9'", Color.WHITE);
				  this.label9.setBackground(primes9);
			      break;
		default:  mainForm.repaint();
		}
	}
	
	public PrimePalette getPrimePalette() {
		return new PrimePalette(primes1, primes3, primes7, primes9, this.backgroundColor);
	}
	
	public Color getPrimes1Color() {
		return primes1;
	}

	public Color getPrimes3Color() {
		return primes3;
	}

	public Color getPrimes7Color() {
		return primes7;
	}

	public Color getPrimes9Color() {
		return primes9;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}