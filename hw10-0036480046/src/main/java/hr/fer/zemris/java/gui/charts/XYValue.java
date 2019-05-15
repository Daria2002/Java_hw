package hr.fer.zemris.java.gui.charts;

/**
 * This class represents x and y values for data to show on bar chart
 * @author Daria Matković
 *
 */
public class XYValue {
	/** value on x axis **/
	private int x;
	/** value on y axis **/
	private int y;
	
	/**
	 * This method represents constructor for x and y
	 * @param x value to show on x axis
	 * @param y value to show on y axis
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets x
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets y 
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
}
