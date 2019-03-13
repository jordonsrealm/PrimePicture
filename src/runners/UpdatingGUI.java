package runners;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import components.MainForm;


public class UpdatingGUI implements Runnable{

	private static final Logger logger = LogManager.getLogger(UpdatingGUI.class.getName());
	
	private MainForm mainForm;
	private Thread updatingThread;
	private File primePicLocation;
	
	
	public UpdatingGUI(MainForm form, File primePicFile) {
		this.mainForm = form;
		this.primePicLocation = primePicFile;
	}
	
	public void stopUpdating() {
		if(updatingThread != null) {
			try {
				updatingThread.join();
				updatingThread = null;
			} catch (InterruptedException e) {
				logger.error("Stopping thread caused errors", e);
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void startUpdating() {
		if(updatingThread == null) {
			updatingThread = new Thread(this, "GUIUpdatingThread");
			updatingThread.start();
		}
	}
	
	@Override
	public void run() {
		mainForm.disablePictureGeneration();
		
		while(mainForm.getMyDrawingRunner().getIndex() > -1){
			int progressValue = this.mainForm.getMyDrawingRunner().getProgress();
			if(progressValue >= 50) {
				mainForm.setProgressString("Writing image to disk...");
			} else {
				mainForm.setProgressString("Settings pixels for image...");
			}
			mainForm.getComponentManager().getProgressBar().setValue(progressValue);
			mainForm.repaint();
		}
		
		synchronized (primePicLocation) {
			while(mainForm.getMyDrawingRunner().getIndex() > -1) {
					logger.info("Waiting now from drawing thread to finish");
					try {
						primePicLocation.wait();
					} catch (InterruptedException e) {
						logger.info("Updating thread was interrupted now...");
						updatingThread.interrupt();
					}
			}
		}
		
		mainForm.getComponentManager().getProgressBar().setStringPainted(true);
		mainForm.setProgressString("Finished");
		mainForm.enablePictureGeneration();
	}
}
