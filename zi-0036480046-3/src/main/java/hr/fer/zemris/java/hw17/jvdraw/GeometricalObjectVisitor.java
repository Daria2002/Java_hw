package hr.fer.zemris.java.hw17.jvdraw;

/**
 * This interface represents geometrical object visitor
 * @author Daria Matković
 *
 */
interface GeometricalObjectVisitor {
	/**
	 * visit given line
	 * @param line line to visit
	 */
	public abstract void visit(Line line);
	
	/**
	 * visit given circle
	 * @param circle circle to visit
	 */
	public abstract void visit(Circle circle);
	
	/**
	 * visit given filled circle
	 * @param filledCircle filled circle
	 */
	public abstract void visit(FilledCircle filledCircle);
	public abstract void visit(FilledT filledT);
}