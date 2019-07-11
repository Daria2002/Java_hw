package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilledTEditor   extends GeometricalObjectEditor {

	/** filled circle to edit */
	FilledT fc;
	/** fill color */
	JColorChooser fillColor = new JColorChooser();
	/** outline color */
	JColorChooser outlineColor = new JColorChooser();
	
	Color newFillColor;
	/** new outline color */
	Color newOutlineColor;
	
	public FilledTEditor(FilledT filledCircle){
		ScrollPane sp = new ScrollPane();
		sp.setSize(800, 800);
		fc = filledCircle;
		JPanel editingPanel = new JPanel(new GridLayout(3, 1));
		
		JPanel help1 = new JPanel(new GridLayout(1, 6));
		JPanel help2 = new JPanel(new GridLayout(1, 2));
		JPanel help3 = new JPanel(new GridLayout(1, 2));
		
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
			newFillColor = fillColor.getColor();
			newOutlineColor = outlineColor.getColor();
		} catch (Exception e) {
			throw new IllegalArgumentException("Arguments after edit are illegal");
		}
	}

	@Override
	public void acceptEditing() {
		fc.setFillColor(fillColor.getColor());
		fc.setOutlineColor( outlineColor.getColor());
	}
	
}
