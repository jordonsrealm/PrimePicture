package components;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import frames.MainForm;


public class MyMenuBar extends JMenuBar {
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	static final Logger logger = LogManager.getLogger(MyMenuBar.class.getName());
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
													
													this.mainForm.setBackGroundColor(backgroundColor);
												}
											);
		
		menuEdit.add(menuItemBackground);
		this.add(menuEdit);
	}
	
}
