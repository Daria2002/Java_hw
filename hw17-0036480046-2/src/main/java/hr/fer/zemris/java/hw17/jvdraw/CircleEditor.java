package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CircleEditor extends GeometricalObjectEditor {

	private JTextField radius = new JTextField();
	private JTextField xCenter = new JTextField();
	private JTextField yCenter = new JTextField();
	private Circle circle;
	private JColorChooser colorChooser = new JColorChooser();
	
	private int newRadius;
	private int newXCenter;
	private int newYCenter;
	private Color newColor;
	
	public CircleEditor(Circle circle) {
		ScrollPane sp = new ScrollPane();
		sp.setSize(800, 800);
		
		this.circle = circle;
		
		radius.setText(String.valueOf(circle.getRadius()));
		xCenter.setText(String.valueOf(circle.getCenterX()));
		yCenter.setText(String.valueOf(circle.getCenterY()));
		colorChooser.setColor(circle.getColor());
		
		JPanel editingPanel = new JPanel(new GridLayout(2, 1));
		
		JPanel help1 = new JPanel(new GridLayout(1, 6));

		JPanel help2 = new JPanel(new GridLayout(1, 2));
		
		help1.add(new JLabel("Radius:"));
		help1.add(radius);
		help1.add(new JLabel("x center:"));
		help1.add(xCenter);
		help1.add(new JLabel("y center:"));
		help1.add(yCenter);
		help2.add(new JLabel("color:"));
		help2.add(colorChooser);
		
		editingPanel.add(help1);
		editingPanel.add(help2);
		sp.add(editingPanel);
		add(sp);
	}

	@Override
	public void checkEditing() {
		try {
			newRadius = Integer.valueOf(radius.getText());
			newXCenter = Integer.valueOf(xCenter.getText());
			newYCenter = Integer.valueOf(yCenter.getText());
			newColor = colorChooser.getColor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Arguments after edit are illegal");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenterX(newXCenter);
		circle.setCenterY(newYCenter);
		circle.setRadius(newRadius);
		circle.setColor(newColor);
	}
}
