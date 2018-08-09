package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import components.MainForm;


public class MyMouseListener extends MouseAdapter {

	MainForm mainForm;
	
	
	public MyMouseListener(MainForm form) {
		this.mainForm = form;
	}
	
	 @Override
     public void mouseClicked(MouseEvent e){
		mainForm.getProgressBar().setStringPainted(true);
		
		// This allows the default string be painted
		mainForm.setProgressString(null);
		mainForm.setProgressValue(0);
     }
}
