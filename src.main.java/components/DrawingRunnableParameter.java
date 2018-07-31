package components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;


public class DrawingRunnableParameter {
	private BufferedImage img; 
	private int height;
	private int width;
	private List<String> primes;
	private MainForm mainForm;
	private PrimePalette primePalette;
	private File primePictureFileLocation;
	private int startingIndex;
	
	
	public DrawingRunnableParameter(ImageHolder imgHolder, List<String> primesList, MainForm form) {
		this.img = imgHolder.getBufferedImage();
		this.height = imgHolder.getBufferedImage().getHeight();
		this.width = imgHolder.getBufferedImage().getWidth();
		this.primePalette = imgHolder.getPrimePalette();
		this.primePictureFileLocation = imgHolder.getPrimePictureFile();
		this.startingIndex = imgHolder.getStart();
		this.primes = primesList;
		this.mainForm = form;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<String> getPrimes() {
		return primes;
	}

	public void setPrimes(List<String> primes) {
		this.primes = primes;
	}

	public MainForm getMainForm() {
		return mainForm;
	}

	public void setMainForm(MainForm mainForm) {
		this.mainForm = mainForm;
	}

	public PrimePalette getPrimePalette() {
		return primePalette;
	}

	public void setPrimePalette(PrimePalette primePalette) {
		this.primePalette = primePalette;
	}
	
	public int getMaxDimensionSize() {
		return this.height * this.width;
	}

	public File getPrimePictureFileLocation() {
		return primePictureFileLocation;
	}

	public void setPrimePictureFileLocation(File primePictureFileLocation) {
		this.primePictureFileLocation = primePictureFileLocation;
	}

	public int getStartingIndex() {
		return startingIndex;
	}

	public void setStartingIndex(int startingIndex) {
		this.startingIndex = startingIndex;
	}
	
}
