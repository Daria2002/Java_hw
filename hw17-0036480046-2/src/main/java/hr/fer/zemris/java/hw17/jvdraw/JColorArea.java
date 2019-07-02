package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

public class JColorArea extends JComponent implements IColorProvider {

	private static final int WIDTH = 15;
	private static final int HEIGHT = 15;
	private Color selectedColor;
	private Color oldColor = null;
	private List<ColorChangeListener> listeners = new ArrayList<>();
	
	public JColorArea(Color selectedColor) {
		super();
		
		setBackground(selectedColor);
		
		this.selectedColor = selectedColor;
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				Color helpColor = JColorChooser.showDialog(getParent(),
						"Choose color", JColorArea.this.selectedColor);
				
				if(helpColor == null) {
					return;
				}
				
				oldColor = JColorArea.this.selectedColor;

				JColorArea.this.selectedColor = helpColor;
				
				callListeners();
				repaint();
			}

			private void callListeners() {
				for(ColorChangeListener l:JColorArea.this.listeners) {
					l.newColorSelected(JColorArea.this, oldColor, selectedColor);
				}
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(selectedColor);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
}
