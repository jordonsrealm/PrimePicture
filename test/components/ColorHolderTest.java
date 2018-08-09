package components;
import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;


public class ColorHolderTest {

	
	@Test
	public void gettingRedColor() {
		Color redColor = new Color( 100, 0, 0, 0);
		ColorHolder colorHolder = new ColorHolder( redColor, null , null, null);
		assertEquals(colorHolder.getPrime1RGB(), redColor.getRGB());
	}
	
	@Test
	public void gettingGreenColor() {
		Color greenColor = new Color( 0, 100, 0, 0);
		ColorHolder colorHolder = new ColorHolder( greenColor, null, null, null);
		assertEquals(colorHolder.getPrime1RGB(), greenColor.getRGB());
	}
	
	@Test
	public void gettingBlueColor() {
		Color blueColor = new Color( 0, 0, 100, 0);
		ColorHolder colorHolder = new ColorHolder( blueColor, null, null, null);
		assertEquals(colorHolder.getPrime1RGB(), blueColor.getRGB());
	}
	
	@Test
	public void gettingAlphaColor() {
		Color alphaColor = new Color( 0, 0, 0, 100);
		ColorHolder colorHolder = new ColorHolder( alphaColor, null , null, null);
		assertEquals(colorHolder.getPrime1RGB(), alphaColor.getRGB());
	}
	
	@Test
	public void gettingColor1() {
		Color color = new Color( 100, 100, 100, 100);
		ColorHolder colorHolder = new ColorHolder( color, null , null, null);
		assertEquals(colorHolder.getPrime1RGB(), color.getRGB());
	}
	
	@Test
	public void gettingColor3() {
		Color color = new Color( 100, 100, 100, 100);
		ColorHolder colorHolder = new ColorHolder( null, color, null, null);
		assertEquals(colorHolder.getPrime3RGB(), color.getRGB());
	}
	
	@Test
	public void gettingColor7() {
		Color color = new Color( 100, 100, 100, 100);
		ColorHolder colorHolder = new ColorHolder( null, null, color, null);
		assertEquals(colorHolder.getPrime7RGB(), color.getRGB());
	}
	
	@Test
	public void gettingColor9() {
		Color color = new Color( 100, 100, 100, 100);
		ColorHolder colorHolder = new ColorHolder( null, null, null, color);
		assertEquals(colorHolder.getPrime9RGB(), color.getRGB());
	}
}
