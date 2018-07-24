package pictures;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;


public class ImageHolder {
	
	private BufferedImage bufferedImage;
	private Dimension dimensions;
	private PrimePalette primePalette;
	private File primePictureFile;
	private int start;
	private int maxNumberPixel;
	
	
	public ImageHolder(BufferedImage image, PrimePalette palette, String primePictureFile, int startIndex, int maximumNumberOfPixels) {
		this.bufferedImage = image;
		this.dimensions = new Dimension(image.getWidth(), image.getWidth());
		this.primePalette = palette;
		this.primePictureFile = new File(primePictureFile + "PrimePicture_" + image.getWidth() + "_" + image.getWidth());
		this.start = startIndex;
		this.maxNumberPixel = maximumNumberOfPixels;
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public Dimension getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}

	public PrimePalette getPrimePalette() {
		return primePalette;
	}

	public void setPrimePalette(PrimePalette primePalette) {
		this.primePalette = primePalette;
	}

	public File getPrimePictureFile() {
		return primePictureFile;
	}

	public void setPrimePictureFile(File primePictureFile) {
		this.primePictureFile = primePictureFile;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start.intValue();
	}

	public Integer getMaxNumberPixel() {
		return maxNumberPixel;
	}

	public void setMaxNumberPixel(Integer maxNumberPixel) {
		this.maxNumberPixel = maxNumberPixel;
	}

}
