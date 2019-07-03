package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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
		this.circle = circle;
		
		radius.setText(String.valueOf(circle.getRadius()));
		xCenter.setText(String.valueOf(circle.getCenterX()));
		yCenter.setText(String.valueOf(circle.getCenterY()));
		colorChooser.setColor(circle.getColor());
		
		JPanel editingPanel = new JPanel(new GridLayout(4,2));
		
		editingPanel.add(new JLabel("Radius"));
		editingPanel.add(radius);
		editingPanel.add(new JLabel("x center"));
		editingPanel.add(xCenter);
		editingPanel.add(new JLabel("y center"));
		editingPanel.add(yCenter);
		editingPanel.add(new JLabel("color"));
		editingPanel.add(colorChooser);
		
		add(editingPanel);
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
