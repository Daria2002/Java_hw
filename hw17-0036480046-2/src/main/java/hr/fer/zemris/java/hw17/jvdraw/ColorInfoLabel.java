package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.JLabel;

public class ColorInfoLabel extends JLabel {
	
	public ColorInfoLabel(JColorArea fgColorArea, JColorArea bgColorArea) {
		int fgColorAreaRed = fgColorArea.getCurrentColor().getRed();
		int fgColorAreaGreen = fgColorArea.getCurrentColor().getGreen();
		int fgColorAreaBlue = fgColorArea.getCurrentColor().getBlue();
		
		int bgColorAreaRed = fgColorArea.getCurrentColor().getRed();
		int bgColorAreaGreen = fgColorArea.getCurrentColor().getGreen();
		int bgColorAreaBlue = fgColorArea.getCurrentColor().getBlue();
		
		String info = "Foreground color: (" + fgColorAreaRed + ", " +
		fgColorAreaBlue + ", " + fgColorAreaGreen + "), background color: (" +
				bgColorAreaRed + ", " + bgColorAreaGreen + ", " + bgColorAreaBlue
				+ ").";
	}
}
