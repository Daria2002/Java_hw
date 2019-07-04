package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

public class ColorInfoLabel extends JLabel {
	
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
