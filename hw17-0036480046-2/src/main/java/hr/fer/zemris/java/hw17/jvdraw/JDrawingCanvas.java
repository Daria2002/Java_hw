package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private Tool tool;
	private DrawingModel dm;
	private String textFile;
	
	public JDrawingCanvas(Supplier<Tool> supplierTool, DrawingModel dm) {
		super();
		this.tool = supplierTool.get();
		this.dm = dm;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		GeometricalObjectVisitor v = new GeometricalObjectPainter(g2d);
		SaveVisitor sv = new SaveVisitor();
		
		for(int i = 0; i < this.dm.getSize(); i++) {
			dm.getObject(i).accept(v);
			dm.getObject(i).accept(sv);
		}
		
		textFile = sv.getFileText();
		
		if(tool != null) {
			this.tool.paint(g2d);
		}
	}
	
	public String getTextFile() {
		return textFile;
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
}
