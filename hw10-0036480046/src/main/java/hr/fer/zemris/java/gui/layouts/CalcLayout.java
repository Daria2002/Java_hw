package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

public class CalcLayout implements LayoutManager2 {

	private int spaceBetweenRowsAndColumns;
	private Map<Component, RCPosition> components;
	private static final int NUMBER_OF_ROWS = 5;
	private static final int NUMBER_OF_COLUMNS = 7;
	private static final int TOTAL_NUMBER_OF_COMPONENTS = 31;
	private int[] columnsWidth;
	private int[] rowsHeight;
	
	public CalcLayout() {
		this(0);
	}
	
	public CalcLayout(int space) {
		System.out.println("kons");
		spaceBetweenRowsAndColumns = space;
		components = new HashMap<Component, RCPosition>();
		columnsWidth = new int[7];
		rowsHeight = new int[5];
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This method is not meant to "
				+ "be called.");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.removeLayoutComponent(comp);
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		System.out.println("preferredLayoutSize pozvano");
		Dimension preferredDimension = new Dimension(0, 0);
		
		for(Component comp : components.keySet()) {
			if(comp.isPreferredSizeSet()) {
				preferredDimension = getDimension(comp, preferredDimension, comp.getPreferredSize());
			}
		}
		
		if(preferredDimension.height == 0 && preferredDimension.width == 0) {
			if(parent.getWidth() != 0 && parent.getHeight() != 0) {
				columnsWidth = calculateComponentSize(parent.getWidth(), NUMBER_OF_COLUMNS);
				rowsHeight = calculateComponentSize(parent.getHeight(), NUMBER_OF_ROWS);
				
				return new Dimension(columnsWidth[0] * NUMBER_OF_COLUMNS +
						(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns, 
						rowsHeight[0] * NUMBER_OF_ROWS +
						(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
				
			} else {
				Dimension maxDim = getMaxDimensions();
				Arrays.fill(columnsWidth, maxDim.width);
				Arrays.fill(rowsHeight, maxDim.height);
				
				int width = 0;
				for(int i = 0; i < columnsWidth.length; i++) {
					width += columnsWidth[i];
				}
				
				int height = 0;
				for(int i = 0; i < rowsHeight.length; i++) {
					height += rowsHeight[i];
				}
				
				return new Dimension(width + (NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns,
						height + (NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
			}
		}
		
		System.out.println("width: " + preferredDimension.width * NUMBER_OF_COLUMNS +
				(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns);
		
		System.out.println("height: " + preferredDimension.height * NUMBER_OF_ROWS +
				(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
		
		return new Dimension(preferredDimension.width * NUMBER_OF_COLUMNS +
				(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns, 
				preferredDimension.height * NUMBER_OF_ROWS +
				(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
	}
	
	private Dimension getDimension(Component comp, Dimension maxDim, Dimension componentSize) {
		// if first element
		if(components.get(comp).getColumn() == 1 &&
				components.get(comp).getRow() == 1) {
			
			if((comp.getPreferredSize().width - 4 * spaceBetweenRowsAndColumns)/5 > maxDim.width) {
				maxDim.width = (componentSize.width - 4 * spaceBetweenRowsAndColumns) / 5;
				
			} if(comp.getPreferredSize().height > maxDim.height) {
				maxDim.height = componentSize.height;
			}
			
			return maxDim;
		}
		
		if(maxDim.height > componentSize.height) {
			maxDim.height = componentSize.height;
		}
		
		if(maxDim.width > componentSize.width) {
			maxDim.width = componentSize.width;
		}
		
		return maxDim;
	}
	
	/**
	 * This method calculates width or height of each column or row.
	 * @param size
	 * @param numberOfComponents
	 * @return
	 */
	private int[] calculateComponentSize(int size, int numberOfComponents) {
		int[] componentsSize = new int[numberOfComponents]; 
		
		if((size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns)) % numberOfComponents != 0) {
			// number of elements with smaller size
			int smallerSize = roundUp(
					(size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns)),
					numberOfComponents) * numberOfComponents - 
					size;
			
			for(int i = 0; i < numberOfComponents; i++) {
				if(i % 2 == 0 && smallerSize > 0) {
					componentsSize[i] = 
							roundUp((size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns)),
									numberOfComponents)-1;
					smallerSize--;
				} else {
					componentsSize[i] = 
							roundUp((size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns)),
									numberOfComponents);
				}
			}
		}
		else {
			Arrays.fill(componentsSize,
					(size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns))/numberOfComponents);
		}
		return componentsSize;
	}

	public static int roundUp(int num, int divisor) {
	    int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
	    return sign * (Math.abs(num) + Math.abs(divisor) - 1) / Math.abs(divisor);
	}
	
	private Dimension getMaxDimensions() {
		int maxWidth = 0; 
		int maxHeight = 0;
		
		
		for(Component comp : components.keySet()) {
			if(comp.getPreferredSize().getHeight() == 0) {
				// calculate size so largest element can show all informations
				return calculateSize();
			}
			
			if(components.get(comp).getColumn() == 1 &&
					components.get(comp).getRow() == 1) {
				if(comp.getPreferredSize().width/5 > maxWidth) {
					maxWidth = (comp.getPreferredSize().width -
							4 * spaceBetweenRowsAndColumns) / 5;
					
				} if(comp.getPreferredSize().height > maxHeight) {
					maxHeight = comp.getPreferredSize().height;
				}
				continue;
			}
			
			if(comp.getPreferredSize().width > maxWidth) {
				maxWidth = comp.getPreferredSize().width;
				
			} if(comp.getPreferredSize().height > maxHeight) {
				maxHeight = comp.getPreferredSize().height;
			}
		}
		
		return new Dimension(maxWidth, maxHeight);
	}

	private Dimension calculateSize() {
		Font labelFont;
		String labelText;
		int stringWidth;
		int stringHeight;
		int maxWidth = 0; 
		int maxHeight = 0;
		
		for(Component comp : components.keySet()) {
			labelFont = comp.getFont();
			JLabel compLabel = (JLabel) comp;
			labelText = compLabel.getText();
			
			stringWidth = comp.getFontMetrics(labelFont).stringWidth(labelText);
			stringHeight = comp.getFontMetrics(labelFont).getHeight();
			
			if(stringHeight > maxHeight) {
				maxHeight = stringHeight;
			} if(stringWidth > maxWidth) {
				maxWidth = stringWidth;
			}
		}
		
		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		System.out.println("min ly size");
		Dimension minimumDimension = new Dimension(0, 0);
		
		for(Component comp : components.keySet()) {
			if(comp.isMinimumSizeSet()) {
				minimumDimension = getDimension(comp, minimumDimension, comp.getMinimumSize());
			}
		}

		if(minimumDimension.height == 0 && minimumDimension.width == 0) {
			return new Dimension(0, 0);
		}
		
		return new Dimension(minimumDimension.width * NUMBER_OF_COLUMNS +
				(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns, 
				minimumDimension.height * NUMBER_OF_ROWS +
				(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		System.out.println("max ly sjwnd");
		Dimension maximumDimension = new Dimension(0, 0);
		
		for(Component comp : components.keySet()) {
			if(comp.isMaximumSizeSet()) {
				maximumDimension = getDimension(comp, maximumDimension, comp.getMaximumSize());
			}
		}

		if(maximumDimension.height == 0 && maximumDimension.width == 0) {
			return new Dimension(0, 0);
		}
		
		return new Dimension(maximumDimension.width * NUMBER_OF_COLUMNS +
				(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns, 
				maximumDimension.height * NUMBER_OF_ROWS +
				(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
	}

	@Override
	public void layoutContainer(Container parent) {
		System.out.println("layout container");
		
		System.out.println("parent width: "+ parent.getWidth());
		System.out.println("parent heigth: "+ parent.getHeight());
		
		int numberOfComponents = components.size();
		
		
		

		
		if (numberOfComponents == 0) {
			return;
		}

		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		

		columnsWidth = calculateComponentSize(parent.getWidth(), NUMBER_OF_COLUMNS);
		rowsHeight = calculateComponentSize(parent.getHeight(), NUMBER_OF_ROWS);
		
		for(Component comp : components.keySet()) {
			RCPosition position = components.get(comp);
			if (position != null) {
				
				x = 0;
				y = 0;
				w = 0;
				h = 0;
				
				
				if(position.getColumn() == 1 && position.getRow() == 1) {
					w = 4 * spaceBetweenRowsAndColumns + columnsWidth[0] + 
							columnsWidth[1] + columnsWidth[2] + columnsWidth[3] + 
							 + columnsWidth[4];
					
				} else {
					x = spaceBetweenRowsAndColumns * (position.getColumn()-1);
					for(int i = 0; i < position.getColumn()-1; i++) {
						x += columnsWidth[i];
					}
					
					y = spaceBetweenRowsAndColumns * (position.getRow()-1);
					for(int i = 0; i < position.getRow()-1; i++) {
						y += rowsHeight[i];
					}
					
					w = columnsWidth[position.getColumn()-1];
				}
				
				h = rowsHeight[position.getRow()-1];
				comp.setBounds(x, y, w, h);
			}
		}
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(!(constraints instanceof RCPosition)) {
			System.out.println(constraints.getClass());
			throw new UnsupportedOperationException("Constraints must be "
					+ "instance of RCPosition.");
		}
		
		if(constraints != null && components.get(constraints) != null) {
			throw new UnsupportedOperationException("Given constraints already added.");
		}
		
		components.put(comp, (RCPosition) constraints);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}

}
