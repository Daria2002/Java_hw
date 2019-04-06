package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * This program calculates new turtle position and draw line to that position
 * and remembers new position.
 * @author Daria Matković
 *
 */
public class DrawCommand implements Command{
	/** number of effective step length turtle should make **/
	private double step;
	
	/**
	 * Constructor that initialize step scaler
	 * should translate for.
	 * @param step scaler
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		float translationLength = 
				(float) (step * ctx.getCurrentState().getEffectiveStepLength());
		
		// remembers start position
		double x1 = ctx.getCurrentState().getCurrentVector().getX();
		double y1 = ctx.getCurrentState().getCurrentVector().getY();
		
		// translates current vector
		Vector2D helpVector = ctx.getCurrentState().getCurrentUnitVector()
				.scaled(translationLength);
		ctx.getCurrentState().getCurrentVector().translate(helpVector);
		
		// remembers new position
		double x2 = ctx.getCurrentState().getCurrentVector().getX();
		double y2 = ctx.getCurrentState().getCurrentVector().getY();
		
		// draw line
		painter.drawLine(x1, y1, x2, y2, ctx.getCurrentState().getColor(),
				translationLength);
	}
}
