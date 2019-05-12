package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {

	private int spaceBetweenRowsAndColumns;
	private Map<Component, RCPosition> components;
	private static final int HEIGHT = 5;
	private static final int WIDTH = 7;
	
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
		return new Dimension(500, 500);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(500, 500);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(500, 500);
	}

	@Override
	public void layoutContainer(Container parent) {
		int numberOfComponents = components.size();
		
		if (numberOfComponents == 0) {
			return;
		}
		
		int i = 0;
		for(Component comp : components.keySet()) {
			RCPosition position = components.get(comp);
			if (position != null) {
				int x = i*2;
				int y = i*2;
				int w = 100;
				int h = 100;
				comp.setBounds(x, y, w, h);
			}
			i++;
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
