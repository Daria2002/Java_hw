package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {

	List<XYValue> values;
	String xDescription;
	String yDescription;
	int yMin;
	int yMax;
	int space;
	
	public BarChart(List<XYValue> values, String xDescription, 
			String yDescription, int yMin, int yMax, int space) {
		
		if(yMin < 0 || yMax < yMin) {
			throw new IllegalArgumentException("y range is not ok.");
		}
		
		if((yMax - yMin) % space != 0) {
			int help = (yMax - yMin + space) / space;
			yMax = space * help;
		}
		
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.space = space;
		
	}
}
