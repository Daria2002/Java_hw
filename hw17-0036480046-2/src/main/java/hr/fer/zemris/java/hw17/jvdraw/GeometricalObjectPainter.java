package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics2D;

/**
 * Visitor
 * @author Daria Matković
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	Graphics2D g2d;
	
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getX0(), line.getY0(), line.getX1(), line.getY1());
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getCenterX()-circle.getRadius()/2, 
				circle.getCenterY()-circle.getRadius()/2,
				circle.getRadius(), circle.getRadius());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(filledCircle.centerX-filledCircle.radius/2, 
				filledCircle.centerY-filledCircle.radius/2,
				filledCircle.radius, filledCircle.radius);
		
		g2d.setColor(filledCircle.getOutlineColor());
		g2d.drawOval(filledCircle.centerX-filledCircle.radius/2, 
				filledCircle.centerY-filledCircle.radius/2,
				filledCircle.radius, filledCircle.radius);
	}
}
