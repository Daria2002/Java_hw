package hr.fer.zemris.java.gui.layouts;

/**
 * This class represents component coordinates
 * @author Daria Matković
 *
 */
public class RCPosition {

	/** row **/
	private int row;
	/** column **/
	private int column;
	/** number of rows **/
	private static final int HEIGHT = 5;
	/** number of columns **/
	private static final int WIDTH = 7;
	/** start index **/
	private static final int START_INDEX = 1;
	/** first free column in first row **/
	private static final int END_OF_TAKEN_RANGE = 6;
	
	/**
	 * This method represents constructor that initializes component position
	 * @param row row
	 * @param column column
	 */
	public RCPosition(int row, int column) {
		
		if(row < START_INDEX || row > HEIGHT || 
				column < START_INDEX || column > WIDTH) {
			
			throw new CalcLayoutException("Row and height range is not ok.");
		
		} else if(row == START_INDEX && column > START_INDEX && 
				column < END_OF_TAKEN_RANGE) {
			
			throw new CalcLayoutException("Column range [1, 5] in first row is taken");
			
		}
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Gets row
	 * @return row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Gets column
	 * @return column
	 */
	public int getColumn() {
		return column;
	}	
}
