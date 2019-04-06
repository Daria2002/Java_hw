package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * This program is used for moving turtle for given length.
 * @author Daria Matković
 *
 */
public class SkipCommand implements Command {
	/** step scaler **/
	private double step;
	
	/**
	 * Constructor that initialize step scaler.
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		float translationLength = 
				(float) (step * ctx.getCurrentState().getEffectiveStepLength());
		
		// translates current vector
		Vector2D helpVector = ctx.getCurrentState().getCurrentUnitVector()
				.scaled(translationLength);
		ctx.getCurrentState().getCurrentVector().translate(helpVector);
	}
}
