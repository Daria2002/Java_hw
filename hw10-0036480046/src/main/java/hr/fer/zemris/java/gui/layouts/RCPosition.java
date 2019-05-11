package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	private int row;
	private int column;
	
	private static final int HEIGHT = 5;
	private static final int WIDTH = 7;
	private static final int START_INDEX = 1;
	private static final int END_OF_TAKEN_RANGE = 6;
	
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
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}	
}
