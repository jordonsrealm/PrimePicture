package components;

import java.awt.Color;


public class ColorHolder {

	private Color primeColor1;
	private Color primeColor3;
	private Color primeColor7;
	private Color primeColor9;
	
	
	public ColorHolder( Color primeColor1, Color primeColor3, Color primeColor7, Color primeColor9) {
		this.primeColor1 = primeColor1;
		this.primeColor3 = primeColor3;
		this.primeColor7 = primeColor7;
		this.primeColor9 = primeColor9;
	}


	public Color getPrimeColor1() {
		return primeColor1;
	}

	public void setPrimeColor1(Color primeColor1) {
		this.primeColor1 = primeColor1;
	}

	public Color getPrimeColor3() {
		return primeColor3;
	}

	public void setPrimeColor3(Color primeColor3) {
		this.primeColor3 = primeColor3;
	}


	public Color getPrimeColor7() {
		return primeColor7;
	}

	public void setPrimeColor7(Color primeColor7) {
		this.primeColor7 = primeColor7;
	}

	public Color getPrimeColor9() {
		return primeColor9;
	}

	public void setPrimeColor9(Color primeColor9) {
		this.primeColor9 = primeColor9;
	}
	
	public int getPrime1RGB() {
		return primeColor1.getRGB();
	}
	
	public int getPrime3RGB() {
		return primeColor1.getRGB();
	}
	
	public int getPrime7RGB() {
		return primeColor1.getRGB();
	}
	
	public int getPrime9RGB() {
		return primeColor1.getRGB();
	}
	
	public int getPrimeNumberRGB(String lastNumber) {
		int returnColor = 0;
		switch (lastNumber) {
			case "1": 
				returnColor = getPrime1RGB();
				break;
			case "3": 
				returnColor = getPrime3RGB();
				break;
			case "7": 
				returnColor = getPrime7RGB();
				break;
			case "9": 
				returnColor = getPrime9RGB();
				break;
			default: break;
		}
		
		return returnColor;
	}
	
	public void setPrime1RGB(int integerColor) {
		this.primeColor1 = getConvertedColor(integerColor);
	}
	
	public void setPrime3RGB(int integerColor) {
		this.primeColor3 = getConvertedColor(integerColor);
	}
	
	public void setPrime7RGB(int integerColor) {
		this.primeColor7 = getConvertedColor(integerColor);
	}
	
	public void setPrime9RGB(int integerColor) {
		this.primeColor9 = getConvertedColor(integerColor);
	}
	
	private Color getConvertedColor(int integerColor) {
		int r = (integerColor) & 0xFF;
		int g = (integerColor >> 8) & 0xFF;
		int b = (integerColor >> 16) & 0xFF;
		int a = (integerColor >> 24) & 0xFF;
		return new Color( r, g, b, a);
	}
	
	public void setPrimeColor1ToClearIfNull() {
		if(getPrimeColor1() == null) {
			setPrimeColor1(new Color(0,0,0,Color.TRANSLUCENT));
		}
	}
	
	public void setPrimeColor3ToClearIfNull() {
		if(getPrimeColor3() == null) {
			setPrimeColor3(new Color(0,0,0,Color.TRANSLUCENT));
		}
	}
	
	public void setPrimeColor7ToClearIfNull() {
		if(getPrimeColor7() == null) {
			setPrimeColor7(new Color(0,0,0,Color.TRANSLUCENT));
		}
	}
	
	public void setPrimeColor9ToClearIfNull() {
		if(getPrimeColor9() == null) {
			setPrimeColor9(new Color(0,0,0,Color.TRANSLUCENT));
		}
	}
}
