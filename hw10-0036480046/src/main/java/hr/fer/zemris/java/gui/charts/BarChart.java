package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents bar chart.
 * @author Daria Matković
 *
 */
public class BarChart {

	/** list of values to show on bar chart **/
	private final List<XYValue> values;
	/** x description **/
	private final String xDescription;
	/** y description **/
	private final String yDescription;
	/** y min **/
	private final int yMin;
	/** y max **/
	private final int yMax;
	/** space between two y values **/
	private final int space;
	
	/**
	 * Gets values
	 * @return returns list of XYValue objects
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets description for x axis
	 * @return returns description for x axis
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Gets description for y axis
	 * @return returns description for y axis
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Gets minimum value on y axis
	 * @return returns minimum value on y axis
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Gets maximum value on y axis
	 * @return returns maximum value on y axis
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Gets space
	 * @return returns space
	 */
	public int getSpace() {
		return space;
	}

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
