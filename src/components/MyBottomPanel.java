package components;

import java.awt.GridLayout;

import javax.swing.JPanel;

import listeners.CreatePictureListener;


public class MyBottomPanel {

	private JPanel bottomPanel;
	
	
	public MyBottomPanel(MainForm mainForm) {
		CreatePictureListener listener = new CreatePictureListener(mainForm);
		mainForm.getComponentManager().getGeneratePic().addActionListener(listener);
		mainForm.getComponentManager().getProgressBar().setStringPainted(true);
		mainForm.getComponentManager().getProgressBar().setMaximum(100);
		
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(mainForm.getComponentManager().getGeneratePic());
		panel.add(mainForm.getComponentManager().getProgressBar());
		
		this.bottomPanel = panel;
	}
	
	public JPanel getBottomPanel() {
		return this.bottomPanel;
	}
}
