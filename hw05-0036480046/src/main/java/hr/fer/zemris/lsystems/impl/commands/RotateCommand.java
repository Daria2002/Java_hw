package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class implements interface Command and rotates turtle for given angle.
 * Rotation means changing unit vector.
 * @author Daria Matković
 *
 */
public class RotateCommand implements Command {
	/** rotation angle **/
	private double angle;
	
	/**
	 * Constructor for angle initialization
	 * @param angle rotation angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getCurrentUnitVector().rotate(angle);
	}
}
