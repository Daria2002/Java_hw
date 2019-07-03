package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

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
		fc = filledCircle;
		
		JPanel editingPanel = new JPanel();
		
		editingPanel.add(new JLabel("Radius"));
		editingPanel.add(radius);
		editingPanel.add(new JLabel("x center"));
		editingPanel.add(x);
		editingPanel.add(new JLabel("y center"));
		editingPanel.add(y);
		editingPanel.add(new JLabel("fill color"));
		editingPanel.add(fillColor);
		editingPanel.add(new JLabel("outline color"));
		editingPanel.add(outlineColor);
		
		add(editingPanel);
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
