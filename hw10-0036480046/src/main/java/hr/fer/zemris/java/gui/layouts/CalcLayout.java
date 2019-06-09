package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * This class represents calculator layout that implements LayoutManager2.
 * @author Daria Matković
 *
 */
public class CalcLayout implements LayoutManager2 {
	/** space between rows and columns **/
	private int spaceBetweenRowsAndColumns;
	/** map of components and positions **/
	private Map<Component, RCPosition> components;
	/** number of rows **/
	private static final int NUMBER_OF_ROWS = 5;
	/** number of columns **/
	private static final int NUMBER_OF_COLUMNS = 7;
	
	/**
	 * Calc layout constructor that initialize space between components to 0
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Calc layout constructor that initialize spaceBetweenRowsAndColumns to 
	 * given value and rest of variables to default values.
	 * @param space
	 */
	public CalcLayout(int space) {
		spaceBetweenRowsAndColumns = space;
		components = new HashMap<Component, RCPosition>();
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

	/**
	 * Gets preferred, maximum or minimum dimension
	 * @param components set of components
	 * @param f1 isPreferredSizeSet, isMaximumSizeSet, isMinimumSizeSet
	 * @param f2 getPreferredSize, getMaximumSize, getMinimumSize
	 * @return dimension
	 */
	private Dimension getWantedDimension(Set<Component> components, Function<Component, Dimension> f1, 
			Function<Component, Boolean> f2) {
		Dimension dim = new Dimension(0, 0);
		
		for(Component comp : components) {
			if(f2.apply(comp)) {
				dim = getDimension(comp, dim, f1.apply(comp));
			}
		}
		
		return dim;
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension preferredDimension = getWantedDimension(components.keySet(),
				(t) -> {return t.getPreferredSize(); },
				(t) -> {return t.isPreferredSizeSet();});
		
		int[] columnsWidth = new int[7];
		int[] rowsHeight = new int[5];
		
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
		
		return new Dimension(preferredDimension.width * NUMBER_OF_COLUMNS +
				(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns, 
				preferredDimension.height * NUMBER_OF_ROWS +
				(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
	}
	
	/**
	 * This method compares component dimension to max dimensions and returns bigger size/width
	 * @param comp component
	 * @param maxDim current max dimension
	 * @param componentSize component size
	 * @return new max dimensions if found, otherwise old max values
	 */
	private Dimension getDimension(Component comp, Dimension maxDim, Dimension componentSize) {
		// if first element
		if(components.get(comp).getColumn() == 1 &&
				components.get(comp).getRow() == 1) {
			
			if((componentSize.width - 4 * spaceBetweenRowsAndColumns)/5 > maxDim.width) {
				maxDim.width = (componentSize.width - 4 * spaceBetweenRowsAndColumns) / 5;
				
			} if(componentSize.height > maxDim.height) {
				maxDim.height = componentSize.height;
			}
			
			return maxDim;
		}
		
		if(maxDim.height < componentSize.height) {
			maxDim.height = componentSize.height;
		}
		
		if(maxDim.width < componentSize.width) {
			maxDim.width = componentSize.width;
		}
		
		return maxDim;
	}
	
	/**
	 * This method calculates width or height of each column or row.
	 * @param size total length
	 * @param numberOfComponents number of components
	 * @return array of heights/widths of rows/columns
	 */
	private int[] calculateComponentSize(int size, int numberOfComponents) {
		int[] componentsSize = new int[numberOfComponents]; 
		
		if((size - ((numberOfComponents-1) * spaceBetweenRowsAndColumns)) % 
				numberOfComponents != 0) {
			// number of elements with smaller size
			int smallerSize = roundUp(
					(size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns)),
					numberOfComponents) * numberOfComponents - 
					size;
			
			for(int i = 0; i < numberOfComponents; i++) {
				if(i % 2 == 0 && smallerSize > 0) {
					componentsSize[i] = 
							roundUp((size - ((numberOfComponents-1) * 
									spaceBetweenRowsAndColumns)),
									numberOfComponents)-1;
					smallerSize--;
				} else {
					componentsSize[i] = 
							roundUp((size - ((numberOfComponents-1) * 
									spaceBetweenRowsAndColumns)),
									numberOfComponents);
				}
			}
		}
		else {
			Arrays.fill(componentsSize,
					(size - ((numberOfComponents-1)*spaceBetweenRowsAndColumns)) / 
					numberOfComponents);
		}
		return componentsSize;
	}

	/**
	 * Round up result of dividing num by divisor
	 * @param num given value to divide
	 * @param divisor value to divide by
	 * @return round up result of dividing num by divisor
	 */
	private static int roundUp(int num, int divisor) {
	    int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
	    return sign * (Math.abs(num) + Math.abs(divisor) - 1) / Math.abs(divisor);
	}
	
	/**
	 * Calculates max dimension of all components
	 * @return max dimension of all components
	 */
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

	/**
	 * Calculates max label text size
	 * @return dimension of max label text size
	 */
	private Dimension calculateSize() {
		Font labelFont;
		String labelText;
		int stringWidth;
		int stringHeight;
		int maxWidth = 0; 
		int maxHeight = 0;
		
		for(Component comp : components.keySet()) {
			labelFont = comp.getFont();
			
			try {
				JLabel compLabel = (JLabel) comp;
				labelText = compLabel.getText();
			} catch (Exception e) {
				try {
					JButton button = (JButton) comp;
					labelText = button.getText();
				} catch (Exception e1) {
					JCheckBox button = (JCheckBox) comp;
					labelText = button.getText();
				}
			}
			
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

	
	/**
	 * This function gets maximumLayoutSize or minimumLayoutSize
	 * @param f1 gets minimum or maximum size
	 * @param f2 call isMaximumSize or isMinimumSize
	 * @return returns minimumLayoutSize or maximumLayoutSize
	 */
	private Dimension getLayoutSize(Function<Component, Dimension> f1, 
			Function<Component, Boolean> f2) {
		Dimension dim = getWantedDimension(components.keySet(), f1, f2);
		
		if(dim.height == 0 && dim.width == 0) {
			return new Dimension(0, 0);
		}
		
		return new Dimension(dim.width * NUMBER_OF_COLUMNS +
				(NUMBER_OF_COLUMNS - 1) * spaceBetweenRowsAndColumns, 
				dim.height * NUMBER_OF_ROWS +
				(NUMBER_OF_ROWS - 1) * spaceBetweenRowsAndColumns);
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize((t) -> {return t.getMinimumSize();}, (t) -> {return t.isMinimumSizeSet();});
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize((t) -> {return t.getMaximumSize();}, (t) -> {return t.isMaximumSizeSet();});
	}

	@Override
	public void layoutContainer(Container parent) {
		if (components.size() == 0) {
			return;
		}

		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		
		int[] columnsWidth = new int[7];
		int[] rowsHeight = new int[5];
		
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
		
	}

}
