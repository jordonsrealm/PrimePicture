package pictures;

import java.awt.Color;


public class PrimePalette {

	private Color prime1;
	private Color prime3;
	private Color prime7;
	private Color prime9;
	private Color background;
	
	
	public PrimePalette(Color prime1Num, Color prime3Num, Color prime7Num, Color prime9Num, Color backgroundColor) {
		this.prime1 = prime1Num;
		this.prime3 = prime3Num;
		this.prime7 = prime7Num;
		this.prime9 = prime9Num;
		this.background = backgroundColor;
	}
	
	public void setPrimePalette(Color prime1Num, Color prime3Num, Color prime7Num, Color prime9Num) {
		prime1 = prime1Num;
		prime3 = prime3Num;
		prime7 = prime7Num;
		prime9 = prime9Num;
	}
	
	public PrimePalette getPrimePalette() {
		return new PrimePalette(prime1, prime3, prime7, prime9, background);
	}
	

	public Color getPrime1() {
		return prime1;
	}

	public void setPrime1(Color prime1) {
		this.prime1 = prime1;
	}

	public Color getPrime3() {
		return prime3;
	}

	public void setPrime3(Color prime3) {
		this.prime3 = prime3;
	}

	public Color getPrime7() {
		return prime7;
	}

	public void setPrime7(Color prime7) {
		this.prime7 = prime7;
	}

	public Color getPrime9() {
		return prime9;
	}

	public void setPrime9(Color prime9) {
		this.prime9 = prime9;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}
	
}
