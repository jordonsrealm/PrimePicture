package adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import frames.MainForm;


public class MyAdapter extends MouseAdapter {

	MainForm mainForm;
	
	public MyAdapter(MainForm form) {
		this.mainForm = form;
	}
	
	 @Override
     public void mouseClicked(MouseEvent e){
		 // Allows the text to be seen
		this.mainForm.getProgressBar().setStringPainted(true);
		
		// This allows the default string be painted
		this.mainForm.getProgressBar().setString(null);
		
		// Sets progress to zero
		this.mainForm.getProgressBar().setValue(0);
     }
}
