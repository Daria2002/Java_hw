package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class represents editor for geometrical object line
 * @author Daria Matković
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	/** line */
	Line line;
	/** x0 */
	JTextField x0 = new JTextField();
	/** y0 */
	JTextField y0 = new JTextField();
	/** x1 */
	JTextField x1 = new JTextField();
	/** y1 */
	JTextField y1 = new JTextField();
	/** color chooser */
	JColorChooser colorChooser = new JColorChooser();
	/** new x0 */
	private int newX0;
	/** new y0 */
	private int newY0;
	/** new x1 */
	private int newX1;
	/** new y1 */
	private int newY1;
	/** new color */
	private Color newColor;
	
	/**
	 * Constructor for line editor
	 * @param line line
	 */
	public LineEditor(Line line) {
		ScrollPane sp = new ScrollPane();
		sp.setSize(800, 800);
		
		this.line = line;
		x0.setText(String.valueOf(line.getX0()));
		y0.setText(String.valueOf(line.getY0()));
		x1.setText(String.valueOf(line.getX1()));
		y1.setText(String.valueOf(line.getY1()));
		colorChooser.setColor(line.getColor());
		
		JPanel editing = new JPanel(new GridLayout(2, 1));
		
		JPanel help1 = new JPanel(new GridLayout(1, 8));

		JPanel help2 = new JPanel(new GridLayout(1, 2));
		
		help1.add(new JLabel("x0:"));
		help1.add(x0);
		help1.add(new JLabel("y0:"));
		help1.add(y0);
		help1.add(new JLabel("x1:"));
		help1.add(x1);
		help1.add(new JLabel("y1:"));
		help1.add(y1);
		help2.add(new JLabel("color:"));
		help2.add(colorChooser);
		editing.add(help1);
		editing.add(help2);
		sp.add(editing);
		add(sp);
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
