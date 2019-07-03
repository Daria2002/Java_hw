package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Rectangle;

/**
 * Visitor
 * @author Daria Matković
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	public GeometricalObjectBBCalculator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visit(Line line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Circle circle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		// TODO Auto-generated method stub
		
	}
	
	public Rectangle getBoundingBox() {
		return null;
		
	}

}
