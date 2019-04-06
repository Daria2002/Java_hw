package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This program scales current vector for given factor.
 * @author Daria Matković
 *
 */
public class ScaleCommand implements Command {
	/** scale factor**/
	private double factor;
	
	/**
	 * Constructor that initialize scale factor
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getCurrentVector().scale(factor);
	}	
}
