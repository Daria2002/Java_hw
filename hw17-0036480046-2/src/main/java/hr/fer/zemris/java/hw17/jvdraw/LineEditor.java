package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LineEditor extends GeometricalObjectEditor {

	Line line;
	JTextField x0 = new JTextField();
	JTextField y0 = new JTextField();
	JTextField x1 = new JTextField();
	JTextField y1 = new JTextField();
	JColorChooser colorChooser = new JColorChooser();
	
	private int newX0;
	private int newY0;
	private int newX1;
	private int newY1;
	private Color newColor;
	
	public LineEditor(Line line) {
		this.line = line;
		x0.setText(String.valueOf(line.getX0()));
		y0.setText(String.valueOf(line.getY0()));
		x1.setText(String.valueOf(line.getX1()));
		y1.setText(String.valueOf(line.getY1()));
		colorChooser.setColor(line.getColor());
		
		JPanel editing = new JPanel();
		
		editing.add(new JLabel("x0"));
		editing.add(x0);
		editing.add(new JLabel("y0"));
		editing.add(y0);
		editing.add(new JLabel("x1"));
		editing.add(x1);
		editing.add(new JLabel("y1"));
		editing.add(y1);
		editing.add(new JLabel("color"));
		editing.add(colorChooser);
		
		add(editing);
	}

	@Override
	public void checkEditing() {
		try {
			newX0 = Integer.valueOf(x0.getText());
			newY0 = Integer.valueOf(y0.getText());
			newX1 = Integer.valueOf(x1.getText());
			newY1 = Integer.valueOf(y1.getText());
			newColor = colorChooser.getColor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Arguments after edit are illegal");
		}
	}

	@Override
	public void acceptEditing() {
		line.setX0(newX0);
		line.setY0(newY0);
		line.setX1(newX1);
		line.setY1(newY1);
		line.setColor(newColor);
	}
	
}
