package hr.fer.zemris.java.gui.charts;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Simple demo program for bar chart 
 * @author Daria Matković
 *
 */
public class MyDemo {
	
	/**
	 * This method is executed when program is run.
	 * @param args
	 */
	public static void main(String[] args) {

		BarChart model = new BarChart(
				Arrays.asList(
				new XYValue(1,8), new XYValue(2,20),
				new XYValue(3,22),
				new XYValue(4,10), new XYValue(5,4)
				),
				"Number of people in the car",
				"Frequency",
				0,
				// y-os kreće od 0
				22,
				// y-os ide do 22
				2
				);
		
	}
}
