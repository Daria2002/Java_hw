package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class implements Command interface. This program copies last state from stack
 * and push copy on stack.
 * @author Daria Matković
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copyState = ctx.getCurrentState().copy();
		ctx.pushState(copyState);
	}
}
