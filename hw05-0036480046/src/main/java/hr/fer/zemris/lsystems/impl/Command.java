package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that implements command for turtle.
 * @author Daria Matković
 *
 */
public interface Command {

	/**
	 * Method for executing command
	 * @param ctx Context object
	 * @param painter Painter object
	 */
	void execute(Context ctx, Painter painter);
}
