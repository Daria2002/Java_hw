package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents bar chart.
 * @author Daria Matković
 *
 */
public class BarChart {

	/** list of values to show on bar chart **/
	List<XYValue> values;
	/** x description **/
	String xDescription;
	/** y description **/
	String yDescription;
	/** y min **/
	int yMin;
	/** y max **/
	int yMax;
	/** space between two y values **/
	int space;
	
	/** 
	 * This method represents constructor for bar chart that initialize variables.
	 * @param values list of values to show on bar chart
	 * @param xDescription x description
	 * @param yDescription y description
	 * @param yMin y min
	 * @param yMax y max 
	 * @param space space between two y values
	 */
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
