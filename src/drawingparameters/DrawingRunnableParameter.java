package drawingparameters;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import frames.MainForm;
import palettes.PrimePalette;


public class DrawingRunnableParameter {
	private BufferedImage img; 
	int height;
	int width;
	private List<String> primes;
	private MainForm mainForm;
	private PrimePalette primePalette;
	
	
	public DrawingRunnableParameter(BufferedImage image, Dimension size, List<String> primesList, MainForm form, PrimePalette palette) {
		this.img = image;
		this.height = size.height;
		this.width = size.width;
		this.primes = primesList;
		this.mainForm = form;
		this.setPrimePalette(palette);
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
	
}
