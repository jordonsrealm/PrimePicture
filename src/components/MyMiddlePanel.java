package components;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import containers.ComponentManager;
import listeners.MyMouseListener;


public class MyMiddlePanel {

	private JPanel middlePanel;
	

	public MyMiddlePanel(ComponentManager componentManager) {
		EmptyBorder border = new EmptyBorder(0,10,0,0);		//top,left,bottom,right
		MyMouseListener myAdapter = new MyMouseListener(componentManager);
		componentManager.getWidthField().addMouseListener( myAdapter );
		componentManager.getWidthField().setBorder( border );
		componentManager.getHeightField().addMouseListener( myAdapter );
		componentManager.getHeightField().setBorder( border );
		
		JLabel hLabel = new JLabel("Height");
		JLabel wLabel = new JLabel("Width");
		hLabel.setBorder( border );
		wLabel.setBorder( border );
		
		JPanel middlePanel = new JPanel(new GridLayout(3,4));
		middlePanel.add(wLabel);
		middlePanel.add(componentManager.getWidthField());
		middlePanel.add(hLabel);
		middlePanel.add(componentManager.getHeightField());
		middlePanel.add(componentManager.getChooser1());
		middlePanel.add(componentManager.getChoose1Label());
		middlePanel.add(componentManager.getChooser3());
		middlePanel.add(componentManager.getChoose3Label());
		middlePanel.add(componentManager.getChooser7());
		middlePanel.add(componentManager.getChoose7Label());
		middlePanel.add(componentManager.getChooser9());
		middlePanel.add(componentManager.getChoose9Label());
		
		this.middlePanel = middlePanel;
	}
	
	public JPanel getMiddlePanel() {
		return this.middlePanel;
	}
}
