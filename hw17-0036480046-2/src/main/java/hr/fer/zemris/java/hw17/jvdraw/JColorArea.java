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

/**
 * Color area that represents color manager
 * @author Daria Matković
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	/** width */
	private static final int WIDTH = 15;
	/** height */
	private static final int HEIGHT = 15;
	/** selected color */
	private Color selectedColor;
	/** old color */
	private Color oldColor = null;
	/** listeners */
	private List<ColorChangeListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor that initialize selected color
	 * @param selectedColor selected color
	 */
	public JColorArea(Color selectedColor) {
		super();
		this.selectedColor = selectedColor;
		setForeground(selectedColor);
		
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
					l.newColorSelected(JColorArea.this, oldColor, JColorArea.this.selectedColor);
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
	public Dimension getMaximumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public Dimension getMinimumSize() {
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
