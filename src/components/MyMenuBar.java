package components;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import frames.MainForm;


public class MyMenuBar extends JMenuBar {
	
	MainForm mainForm;
	Color backgroundColor;
	
	public MyMenuBar(MainForm mForm) {
		mainForm = mForm;
		
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem menuItemBackground = new JMenuItem("Set Background Color");
		
		menuItemBackground.addActionListener(e ->{
												backgroundColor = JColorChooser.showDialog(mainForm, "Select the background color", Color.WHITE);
													if(backgroundColor == null) {
														backgroundColor = new Color(Color.TRANSLUCENT);
													}
												if(mainForm == null) System.out.println("Main form is null");
												this.mainForm.setBackGroundColor(backgroundColor);
												}
											);
		
		menuEdit.add(menuItemBackground);
		this.add(menuEdit);
	}
	
}
