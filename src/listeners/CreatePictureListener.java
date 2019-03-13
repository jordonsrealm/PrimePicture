package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import components.DrawingRunnableParameter;
import components.ImageHolder;
import components.MainForm;
import runners.DrawingRunner;
import utility.TextEvaluator;


public class CreatePictureListener implements ActionListener {

	private MainForm mainForm;
	
	
	public CreatePictureListener(MainForm mainForm) {
		this.mainForm = mainForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String hTextFieldText = mainForm.getComponentManager().getHeightField().getText();
		String wTextFieldText = mainForm.getComponentManager().getWidthField().getText();
		
		// Checks for non numberic characters
		if( TextEvaluator.isNumericOnly(hTextFieldText) && TextEvaluator.isNumericOnly(wTextFieldText) ) {
			int heightSize = Integer.parseInt(hTextFieldText);
			int widthSize  = Integer.parseInt(wTextFieldText);
			
			BufferedImage buffImage = new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_4BYTE_ABGR);
			ImageHolder imageHolder = new ImageHolder( buffImage, mainForm.getColorChooserListener().getPrimePalette(), mainForm.getPrimePicsDefaultLocation(), 0);
			
			DrawingRunnableParameter parameter = new DrawingRunnableParameter(imageHolder, mainForm.getPrimeListThread().getPrimes(), mainForm);
			mainForm.setMyDrawingRunner( new DrawingRunner(parameter));
			mainForm.getMyDrawingRunner().startDrawingPicture();
		}
	}
}
