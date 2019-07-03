package hr.fer.zemris.java.hw17.jvdraw;

import java.util.ArrayList;
import java.util.List;

public class SaveVisitor implements GeometricalObjectVisitor {

	List<String> fileText = new ArrayList<String>();
	
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
}
