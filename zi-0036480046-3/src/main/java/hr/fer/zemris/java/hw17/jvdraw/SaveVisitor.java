package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents visitor for saving .jvd files
 * @author Daria Matković
 *
 */
public class SaveVisitor implements GeometricalObjectVisitor {
	/** file text */
	List<String> fileText = new ArrayList<String>();
	
	/**
	 * gets text from file
	 * @return text from file
	 */
	public String getFileText() {
		return String.join("\n", fileText);
	}

	@Override
	public void visit(Line line) {
		fileText.add(
			line.getName() + " " +
			line.getX0() + " " +
			line.getY0() + " " +
			line.getX1() + " " +
			line.getY1() + " " +
			line.getColor().getRed() + " " +
			line.getColor().getGreen() + " " +
			line.getColor().getBlue());
	}

	@Override
	public void visit(Circle circle) {
		fileText.add(
			circle.getName() + " " +
			circle.getCenterX() + " " +
			circle.getCenterY() + " " +
			circle.getRadius() + " " +
			circle.getColor().getRed() + " " +
			circle.getColor().getGreen() + " " +
			circle.getColor().getBlue());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		fileText.add(
			filledCircle.getName() + " " +
			filledCircle.centerX + " " +
			filledCircle.centerY + " " +
			filledCircle.radius + " " +
			filledCircle.getFillColor().getRed() + " " +
			filledCircle.getFillColor().getGreen() + " " +
			filledCircle.getFillColor().getBlue() + " " +
			filledCircle.getFillColor().getRed() + " " +
			filledCircle.getFillColor().getGreen() + " " +
			filledCircle.getFillColor().getBlue());
	}
	@Override
	public void visit(FilledT filledT) {
		fileText.add(
				filledT.getName() + " " +
						filledT.x[0] + " " +
						filledT.y[0] + " " +
						filledT.x[1] + " " +
						filledT.y[1] + " " +
						filledT.x[2] + " " +
						filledT.y[2] + " " +
						filledT.getFillColor().getRed() + " " +
						filledT.getFillColor().getGreen() + " " +
						filledT.getFillColor().getBlue() + " " +
						filledT.getFillColor().getRed() + " " +
						filledT.getFillColor().getGreen() + " " +
						filledT.getFillColor().getBlue());
	}
}
