package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilledCircleEditor extends GeometricalObjectEditor {

	FilledCircle fc;
	JColorChooser fillColor = new JColorChooser();
	JColorChooser outlineColor = new JColorChooser();
	JTextField radius = new JTextField();
	JTextField x = new JTextField();
	JTextField y = new JTextField();
	
	int newRadius;
	int newX;
	int newY;
	Color newFillColor;
	Color newOutlineColor;
	
	public FilledCircleEditor(FilledCircle filledCircle) {
		ScrollPane sp = new ScrollPane();
		sp.setSize(800, 800);
		fc = filledCircle;
		JPanel editingPanel = new JPanel(new GridLayout(3, 1));
		
		JPanel help1 = new JPanel(new GridLayout(1, 6));
		JPanel help2 = new JPanel(new GridLayout(1, 2));
		JPanel help3 = new JPanel(new GridLayout(1, 2));
		
		help1.add(new JLabel("Radius:"));
		radius.setText(String.valueOf(filledCircle.getRadius()));
		help1.add(radius);
		help1.add(new JLabel("x center:"));
		x.setText(String.valueOf(filledCircle.getCenterX()));
		help1.add(x);
		help1.add(new JLabel("y center:"));
		y.setText(String.valueOf(filledCircle.getCenterY()));
		help1.add(y);
		help2.add(new JLabel("fill color:"));
		fillColor.setColor(filledCircle.getFillColor());
		help2.add(fillColor);
		help3.add(new JLabel("outline color:"));
		outlineColor.setColor(filledCircle.getOutlineColor());
		help3.add(outlineColor);
		
		editingPanel.add(help1);
		editingPanel.add(help2);
		editingPanel.add(help3);
		
		sp.add(editingPanel);
		add(sp);
	}

	@Override
	public void checkEditing() {
		try {
			newRadius = Integer.valueOf(radius.getText());
			newX = Integer.valueOf(x.getText());
			newY = Integer.valueOf(y.getText());
			newFillColor = fillColor.getColor();
			newOutlineColor = outlineColor.getColor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Arguments after edit are illegal");
		}
	}

	@Override
	public void acceptEditing() {
		fc.setCenterX(newX);
		fc.setCenterY(newY);
		fc.setRadius(newRadius);
		fc.setFillColor(newFillColor);
		fc.setOutlineColor(newOutlineColor);
	}	
}
