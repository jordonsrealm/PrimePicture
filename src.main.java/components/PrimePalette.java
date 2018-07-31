package components;

import java.awt.Color;


public class PrimePalette {

	private ColorHolder colorHolder;
	private Color background;
	
	
	public PrimePalette( ColorHolder colorHolder, Color backgroundColor) {
		this.colorHolder = colorHolder;
		this.background = backgroundColor;
	}
	
	public void setPrimePalette( ColorHolder colorHolder ) {
		this.colorHolder = colorHolder;
	}
	
	public PrimePalette getPrimePalette() {
		return new PrimePalette( colorHolder, background);
	}
	

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public ColorHolder getColorHolder() {
		return colorHolder;
	}

	public void setColorHolder(ColorHolder colorHolder) {
		this.colorHolder = colorHolder;
	}
	
}
