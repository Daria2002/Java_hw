package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics;
import java.util.function.Supplier;

import javax.swing.JComponent;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	public JDrawingCanvas(Supplier<Tool> supplierTool) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		// TODO Auto-generated method stub
		
	}
	
}
