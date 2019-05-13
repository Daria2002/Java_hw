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
		if(parent.getWidth() != 0 && parent.getHeight() != 0) {
			columnsWidth = calculateComponentSize(parent.getWidth(), NUMBER_OF_COLUMNS);
			rowsHeight = calculateComponentSize(parent.getHeight(), NUMBER_OF_ROWS);
			return new Dimension(parent.getWidth(), parent.getHeight());
		}
		
		Dimension maxDim = getMaxDimensions();
		Arrays.fill(columnsWidth, maxDim.width);
		Arrays.fill(rowsHeight, maxDim.height);
		
		return new Dimension(maxDim.width * NUMBER_OF_COLUMNS + 
				(NUMBER_OF_COLUMNS-1) * spaceBetweenRowsAndColumns, 
				maxDim.height * NUMBER_OF_ROWS + (NUMBER_OF_ROWS-1) * spaceBetweenRowsAndColumns);
	}
	
	private int[] calculateComponentSize(int size, int numberOfComponents) {
		int[] componentsSize = new int[TOTAL_NUMBER_OF_COMPONENTS]; 
		
		if(size % numberOfComponents != 0) {
			// number of elements with smaller size
			int smallerSize = size % numberOfComponents;
			
			for(int i = 0; i < numberOfComponents; i++) {
				if(i % 2 == 0 && smallerSize > 0) {
					componentsSize[i] = size/numberOfComponents - 1;
					smallerSize--;
				} else {
					componentsSize[i] = size/numberOfComponents;
				}
			}
		}
		
		Arrays.fill(componentsSize, size/numberOfComponents);
		return componentsSize;
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
		if(parent.getWidth() != 0 && parent.getHeight() != 0) {
			return new Dimension(parent.getWidth(), parent.getHeight());
		}
		
		Dimension maxDim = getMaxDimensions();
		
		return new Dimension(maxDim.width * NUMBER_OF_COLUMNS, 
				maxDim.height * NUMBER_OF_ROWS);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}

	@Override
	public void layoutContainer(Container parent) {
		int numberOfComponents = components.size();
		
		columnsWidth = calculateComponentSize(parent.getPreferredSize().width, 
				NUMBER_OF_COLUMNS);
		rowsHeight = calculateComponentSize(parent.getPreferredSize().height,
				NUMBER_OF_ROWS);
		
		if (numberOfComponents == 0) {
			return;
		}
		
		for(Component comp : components.keySet()) {
			RCPosition position = components.get(comp);
			if (position != null) {
				
				int x;
				int y;
				int w;
				int h;
				
				if(position.getColumn() == 1 && position.getRow() == 1) {
					x = 0;
					y = 0;
					
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
					
					w = columnsWidth[components.get(comp).getColumn()];
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
