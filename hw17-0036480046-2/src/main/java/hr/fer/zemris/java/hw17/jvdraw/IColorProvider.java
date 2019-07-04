package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * Interface that represents color provider
 * @author Daria Matković
 *
 */
public interface IColorProvider {
	/**
	 * Gets current color 
	 * @return color
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds color change listener
	 * @param l listener to add
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Removes color change listener
	 * @param l listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}