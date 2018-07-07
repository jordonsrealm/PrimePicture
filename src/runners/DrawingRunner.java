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
import drawingparameters.DrawingRunnableParameter;


public class DrawingRunner implements Runnable{
	
	public static final Logger logger = LogManager.getLogger(Logger.class.getName());
	
	private BufferedImage bufferedImage;
	private ArrayList<String> primeList;
	private Color primeColor1;
	private Color primeColor3;
	private Color primeColor7;
	private Color primeColor9;
	private Color backgroundColor;
	private int picWidth  = 0;
	private int picHeight = 0;
	private Long totalPixelCount = (long) 0;
	private Long totalPixels = (long) 0;
	private Long totalPrimePictureBytes = (long) 0;
	private Long currentPicBytesCount = (long) 0;
	private int index;
	private Thread thread;
	private File primePicFile;
	private int offset;
	private int maximumPixels;

	
	
	public DrawingRunner(DrawingRunnableParameter parameter) {
		this.bufferedImage = parameter.getImg();
		this.primeList = (ArrayList<String>) parameter.getPrimes();
		this.primeColor1 = parameter.getPrimePalette().getPrime1();
		this.primeColor3 = parameter.getPrimePalette().getPrime3();
		this.primeColor7 = parameter.getPrimePalette().getPrime7();
		this.primeColor9 = parameter.getPrimePalette().getPrime9();
		this.picWidth = parameter.getWidth();
		this.picHeight = parameter.getHeight();
		this.totalPixelCount = picWidth * (long)picHeight;
		this.primePicFile = parameter.getPrimePictureFileLocation();
		this.offset = parameter.getStartingIndex();
		this.maximumPixels = parameter.getMaxNumberOfPixels();
		this.backgroundColor = parameter.getPrimePalette().getBackground();
	}
	
	public void startDrawingPicture() {
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
			this.offset = x;
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
		
		try(OutputStream outputStream = new FileOutputStream(primePicFile.toPath().toString() + "_" + offset + ".png")) {
			ImageIO.write(bufferedImage, "png", baos);
			byteArray = baos.toByteArray();
			totalPrimePictureBytes = Long.parseLong(byteArray.length +"");
			baos.flush();
			baos.close();
			
			for(currentPicBytesCount = (long) 0;currentPicBytesCount < totalPrimePictureBytes;currentPicBytesCount++) {
				
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
			int xpt= (x%(picWidth));
			int ypt= (x/(picWidth));
			
			String totalLengthStr = (x + offset)+ "";
			String lastNumber = totalLengthStr.charAt(totalLengthStr.length() - 1) + "";
			
			if( index < maximumPixels && this.primeList.get(index).equals(totalLengthStr) ) {
				switch (lastNumber) {
				case "1": bufferedImage.setRGB(xpt, ypt, primeColor1.getRGB());
						  break;
				case "3": bufferedImage.setRGB(xpt, ypt, primeColor3.getRGB());
						  break;
			    case "7": bufferedImage.setRGB(xpt, ypt, primeColor7.getRGB());
			    		  break;
				case "9": bufferedImage.setRGB(xpt, ypt, primeColor9.getRGB());
						  break;
				default: break;
				}
				
				index++;
			} else {
				logger.info("{}, {}, {}", xpt, ypt, this.backgroundColor);
				bufferedImage.setRGB(xpt, ypt, this.backgroundColor.getRGB());
			}
			
			totalPixels = (long) x;
		}
		
		// This registers a full progress of drawn since the for loop 
		// doesn't finish up to the totalPixelCount value
		totalPixels++;
		logger.info("Finished processing buffered image {} by {}...", picWidth, picHeight);
		
		// Check if the dimension the user inputted are larger than the file size of primes
		if(index > maximumPixels) {
			logger.debug("Dimension provided exceeds the file size of primes, adding a custom "
						 + "file to the project that exceeds the current dimension will work as well.");
		}
		
	}

	private void nullCheck() {
		if(primeColor1 == null) {
			primeColor1 = new Color(0,0,0,Color.TRANSLUCENT);
		}
		if(primeColor3 == null){
			primeColor3 = new Color(0,0,0,Color.TRANSLUCENT);
		} 
		if(primeColor7 == null) {
			primeColor7 = new Color(0,0,0,Color.TRANSLUCENT);
		} 
		if(primeColor9 == null){
			primeColor9 = new Color(0,0,0,Color.TRANSLUCENT);
		}
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
		return (int)(( ratio1 + ratio2) * 100);
	}
	
	private int adjustedIndex(int startingIndex) {
		if(startingIndex < 3) return 0;
		int start = 0;
		while(Integer.parseInt(this.primeList.get(start)) <= startingIndex ) {
			start++;
		}
		return start;
	}

}
