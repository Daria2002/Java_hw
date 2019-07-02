package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

public class ColorInfoLabel extends JLabel implements ColorChangeListener {
	
	private String info;
	int fgColorAreaRed;
	int fgColorAreaGreen;
	int fgColorAreaBlue;
	int bgColorAreaRed;
	int bgColorAreaGreen;
	int bgColorAreaBlue;
	
	public ColorInfoLabel(JColorArea fgColorArea, JColorArea bgColorArea) {
		fgColorAreaRed = fgColorArea.getCurrentColor().getRed();
		fgColorAreaGreen = fgColorArea.getCurrentColor().getGreen();
		fgColorAreaBlue = fgColorArea.getCurrentColor().getBlue();
		
		bgColorAreaRed = fgColorArea.getCurrentColor().getRed();
		bgColorAreaGreen = fgColorArea.getCurrentColor().getGreen();
		bgColorAreaBlue = fgColorArea.getCurrentColor().getBlue();
		
		info = "Foreground color: (" + fgColorAreaRed + ", " +
		fgColorAreaBlue + ", " + fgColorAreaGreen + "), background color: (" +
				bgColorAreaRed + ", " + bgColorAreaGreen + ", " + bgColorAreaBlue
				+ ").";
		setText(info);
	}
	
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		int red = newColor.getRed();
		int green = newColor.getGreen();
		int blue = newColor.getBlue();
		
		info = "Foreground color: (" + fgColorAreaRed + ", " +
				blue + ", " + green + "), background color: (" +
						bgColorAreaRed + ", " + bgColorAreaGreen + ", " + bgColorAreaBlue
						+ ").";
				setText(info);
	}
}
