package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import components.ColorHolder;
import components.PrimePalette;
import containers.ComponentManager;


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
	private ComponentManager mainForm = null;
	
	private String prime1Text = "Choose color for primes ending in '1'";
	private String prime3Text = "Choose color for primes ending in '3'";
	private String prime7Text = "Choose color for primes ending in '7'";
	private String prime9Text = "Choose color for primes ending in '9'";
	
	
	public ColorChooserListener(JLabel label1,JLabel label3,JLabel label7,JLabel label9) {
		this.label1 = label1;
		this.label3 = label3;
		this.label7 = label7;
		this.label9 = label9;
		this.setBackgroundColor( new Color(0,0,0,0) );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonText = ((JButton) e.getSource() ).getText();
		String compareStr = buttonText.charAt(buttonText.length() - 1) + "";
		switch (compareStr){
		case "1": this.primes1 = JColorChooser.showDialog(label1, prime1Text, Color.WHITE);
				  label1.setBackground(primes1);
				  break;
		case "3": this.primes3 = JColorChooser.showDialog(label3, prime3Text, Color.WHITE);
				  label3.setBackground(primes3);
				  break;
		case "7": this.primes7 = JColorChooser.showDialog(label7, prime7Text, Color.WHITE);
				  label7.setBackground(primes7);
			      break;
		case "9": this.primes9 = JColorChooser.showDialog(label9, prime9Text, Color.WHITE);
				  label9.setBackground(primes9);
			      break;
		default:  mainForm.getChoose1Label().invalidate();
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
