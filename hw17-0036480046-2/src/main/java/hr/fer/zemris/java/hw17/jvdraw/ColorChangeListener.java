package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * This class represents color change listener
 * @author Daria Matković
 *
 */
public interface ColorChangeListener {
	/**
	 * This method is called when new color is selected
	 * @param source color provider
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, 
			Color newColor);
}
