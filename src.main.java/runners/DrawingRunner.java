package runners;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import components.MainForm;
import drawingparameters.ColorHolder;
import drawingparameters.DrawingRunnableParameter;


public class DrawingRunner implements Runnable{

	public static final Logger logger = LogManager.getLogger(DrawingRunner.class.getName());

	private BufferedImage bufferedImage;
	private ArrayList<String> primeList;
	private ColorHolder colorHolder;
	private int picWidth  = 0;
	private int picHeight = 0;
	private Long totalPixelCount = (long) 0;
	private File primePicFile;
	private int offset;
	private int maximumPixels;
	private Color backgroundColor;
	private MainForm mainForm;
	
	private Long totalPixels = (long) 0;
	private Long totalPrimePictureBytes = (long) 0;
	private Long currentPicBytesCount = (long) 0;
	private int index;
	private Thread thread;


	public DrawingRunner(DrawingRunnableParameter parameter) {
		this.bufferedImage 		= parameter.getImg();
		this.primeList 			= (ArrayList<String>) parameter.getPrimes();
		this.colorHolder 		= parameter.getPrimePalette().getColorHolder();
		this.picWidth 			= parameter.getWidth();
		this.picHeight 			= parameter.getHeight();
		this.primePicFile 		= parameter.getPrimePictureFileLocation();
		this.offset 			= parameter.getStartingIndex();
		this.maximumPixels 		= parameter.getMaxNumberOfPixels();
		this.backgroundColor 	= parameter.getPrimePalette().getBackground();
		this.mainForm 			= parameter.getMainForm();
		
		this.totalPixelCount 	= picWidth * (long) picHeight;
	}

	public void startDrawingPicture() {
		UpdatingGUI updateGUI = new UpdatingGUI(mainForm, primePicFile);
		updateGUI.startUpdating();

		if(thread == null) {
			thread = new Thread(this, "DrawingThread");
			thread.start();
		}
	}

	public void stopDrawingPicture() {
		if(thread != null) {
			try {
				thread.join();
				thread = null;
			} catch (InterruptedException e) {
				logger.error("Interrupted Exception occured", e);
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void run() {
		nullCheck();

		for(int x =0; x< 1;x++) {
			offset = x;
			// Set the current index for reading the prime list
			index = adjustedIndex(offset);
			processImage();
			writeImage();
		}

		// Tells any threads that the processing is completed
		this.index = -1;

		synchronized(primePicFile) {
			logger.info("Calling DrawingRunner thread to stop...");
			primePicFile.notifyAll();
		}
	}

	private void writeImage() {
		byte[] byteArray = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try(OutputStream outputStream = new FileOutputStream(primePicFile.toPath().toString() + ".png")) {
			ImageIO.write(bufferedImage, "png", baos);
			byteArray = baos.toByteArray();
			totalPrimePictureBytes = Long.parseLong(byteArray.length +"");
			baos.flush();
			baos.close();

			for(currentPicBytesCount = (long) 0;currentPicBytesCount < totalPrimePictureBytes; currentPicBytesCount++) {
				outputStream.write(byteArray[(int) getCurrentByteCount()]);
			}

			outputStream.flush();
		} catch (IOException e11) {
			logger.error("Error when writing to outputstream", e11);
		}

		bufferedImage.flush();

		logger.info("Finished writing image now...");
	}

	private void processImage() {
		logger.info("DrawingRunner thread is now drawing");

		for(int x = 0;x < totalPixelCount; x++) {
			
			int xpt= (x%(picWidth - 1));
			int ypt= (x/(picWidth));

			String totalLengthStr = (x + offset)+ "";
			String lastNumber = totalLengthStr.charAt(totalLengthStr.length() - 1) + "";

			if( primeList.get(index).equals(totalLengthStr) ) {
				bufferedImage.setRGB( xpt, ypt, colorHolder.getPrimeNumberRGB(lastNumber));

				index++;
			} else {
				bufferedImage.setRGB( xpt, ypt, backgroundColor.getRGB());
			}

			totalPixels = (long) x;
		}

		// This registers a full progress of drawn since the for loop doesn't finish up to the totalPixelCount value
		totalPixels++;

		// Check if the dimension the user inputted are larger than the file size of primes
		if(index > maximumPixels) {
			logger.debug("Dimension provided exceeds the file size of primes, adding a custom file to the project that exceeds the current dimension will work as well.");
		}

	}

	private void nullCheck() {
		colorHolder.setPrimeColor1ToClearIfNull();
		colorHolder.setPrimeColor3ToClearIfNull();
		colorHolder.setPrimeColor7ToClearIfNull();
		colorHolder.setPrimeColor9ToClearIfNull();
	}

	public synchronized int getIndex() {
		return index;
	}

	public long getTotalByteCount() {
		return (totalPrimePictureBytes == null)?0:totalPrimePictureBytes;
	}

	public long getCurrentByteCount() {
		return currentPicBytesCount;
	}

	public double getProgressWritten(){
		if(currentPicBytesCount == null || getTotalByteCount() == 0) {
			return 0;
		}

		return ((double)currentPicBytesCount/getTotalByteCount() ) * .5;
	}

	public double getProgressDrawn() {
		return (totalPixels / (double)totalPixelCount) * .5;
	}

	public int getProgress() {
		double ratio1 = getProgressDrawn();
		double ratio2 = getProgressWritten();
		return (int)(( ratio1 + ratio2 ) * 100);
	}

	private int adjustedIndex(int startingIndex) {
		if(startingIndex < 3) return 0;
		int start = 0;
		while(Integer.parseInt(primeList.get(start)) <= startingIndex ) {
			start++;
		}
		return start;
	}

}
