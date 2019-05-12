package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {

	private int spaceBetweenRowsAndColumns;
	private Map<Component, RCPosition> components;
	
	public CalcLayout() {
		this(0);
	}
	
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

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(1, 2);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(1, 2);
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension d = preferredLayoutSize(parent);
        parent.setSize(d);

        int x = spaceBetweenRowsAndColumns;
        int y = spaceBetweenRowsAndColumns;
		
		for(Component comp : components.keySet()) {

	    	int componentSize = comp.getWidth()/7;
	    	
			RCPosition position = components.get(comp);
			comp.setBounds(x, y, position.getColumn(), position.getRow());
			
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(!(constraints instanceof RCPosition)) {
			System.out.println(constraints.getClass());
			throw new UnsupportedOperationException("Constraints must be "
					+ "instance of RCPosition.");
		}
		
		components.put(comp, (RCPosition) constraints);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		// TODO Auto-generated method stub
		return null;
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
