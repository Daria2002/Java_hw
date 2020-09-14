package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * This class represents color info label that extends JLabel
 * @author Daria Matković
 *
 */
public class ColorInfoLabel extends JLabel {
	/** info */
	private String info;
	/** foreground red component */
	int fgColorAreaRed;
	/** foreground green component */
	int fgColorAreaGreen;
	/** foreground blue component */
	int fgColorAreaBlue;
	/** background red component */
	int bgColorAreaRed;
	/** background green component */
	int bgColorAreaGreen;
	/** background blue component */
	int bgColorAreaBlue;
	
	/**
	 * Constructor for color info label
	 * @param fgColorArea
	 * @param bgColorArea
	 */
	public ColorInfoLabel(JColorArea fgColorArea, JColorArea bgColorArea) {
		fgColorAreaRed = fgColorArea.getCurrentColor().getRed();
		fgColorAreaGreen = fgColorArea.getCurrentColor().getGreen();
		fgColorAreaBlue = fgColorArea.getCurrentColor().getBlue();
		
		bgColorAreaRed = bgColorArea.getCurrentColor().getRed();
		bgColorAreaGreen = bgColorArea.getCurrentColor().getGreen();
		bgColorAreaBlue = bgColorArea.getCurrentColor().getBlue();
		
		info = "Foreground color: (" + fgColorAreaRed + ", " + fgColorAreaGreen
		 + ", " + fgColorAreaBlue + "), background color: (" +
				bgColorAreaRed + ", " + bgColorAreaGreen + ", " + bgColorAreaBlue
				+ ").";
		setText(info);
	}
}
