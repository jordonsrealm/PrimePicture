package runners;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import frames.MainForm;


public class UpdatingGUI implements Runnable{

	private static final Logger logger = LogManager.getLogger(Logger.class.getName());
	
	private MainForm mainForm;
	private Thread updatingThread;
	private File primePicLocation;
	
	
	public UpdatingGUI(MainForm form, File primePicFile) {
		this.mainForm = form;
		this.primePicLocation = primePicFile;
	}
	
	public void stopUpdating() {
		logger.info("Stop updating the progressbar");
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
		logger.info("Start updating the progressbar");
		
		if(updatingThread == null) {
			updatingThread = new Thread(this, "GUIUpdatingThread");
			updatingThread.start();
		}
	}
	
	@Override
	public void run() {
		logger.info("Starting to update the progressbar");
		this.mainForm.getGeneratePicButton().setEnabled(false);
		
		while(this.mainForm.getMyDrawingRunner().getIndex() > -1){
			int progressValue = this.mainForm.getMyDrawingRunner().getProgress();
			if(progressValue >= 50) {
				this.mainForm.getProgressBar().setString("Writing image to disk...");
			} else {
				this.mainForm.getProgressBar().setString("Settings pixels for image...");
			}
			this.mainForm.getProgressBar().setValue(progressValue);
			this.mainForm.repaint();
		}
		
		synchronized (primePicLocation) {
			while(this.mainForm.getMyDrawingRunner().getIndex() > -1) {
					logger.info("Waiting now from drawing thread to finish");
					try {
						primePicLocation.wait();
					} catch (InterruptedException e) {
						logger.info("Updating thread was interrupted now...");
						updatingThread.interrupt();
					}
			}
		}
		
		// Set progress bar to 100% since everything is now finished
		//this.mainForm.getProgressBar().setValue(100);
		this.mainForm.getProgressBar().setStringPainted(true);
		this.mainForm.getProgressBar().setString("Finished");
		this.mainForm.getGeneratePicButton().setEnabled(true);
	}
}
