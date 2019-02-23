package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import components.MainForm;
import containers.ComponentManager;


public class MyMouseListener extends MouseAdapter {

	ComponentManager componentManager;
	
	
	public MyMouseListener(ComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	 @Override
     public void mouseClicked(MouseEvent e){
		 componentManager.getProgressBar().setStringPainted(true);
		
		// This allows the default string be painted
		 componentManager.getProgressBar().setString(null);
		 componentManager.getProgressBar().setValue(0);
     }
}
