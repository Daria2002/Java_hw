package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This program is used for color management.
 * @author Daria Matković
 *
 */
public class ColorCommand implements Command{
	/** drawing color **/
	private Color color;
	
	/**
	 * Constructor that initialize color
	 * @param color new color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}
	
}
