package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This program loads configurations for drawing fractals from file and shows 
 * loaded fractals. Some configuration files are saved in src/main/resources
 * @author Daria Matković
 *
 */
public class Glavni3 {

	/**
	 * Method that executes when program is run.
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
