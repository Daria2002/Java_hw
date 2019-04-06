package hr.fer.zemris.java.hw05.demo;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;

public class Demo {
	public static void main(String[] args) {

		LSystemViewer.showLSystem((LSystem) new LSystem() {
			@Override
			public String generate(int level) {
				return ""; // totalno ignoriramo u ovom primjeru...
			}
			@Override
			public void draw(int level, Painter painter) {
				painter.drawLine(0.1, 0.1, 0.9, 0.1, Color.RED, 1f);
				painter.drawLine(0.9, 0.1, 0.9, 0.9, Color.GREEN, 1f);
				painter.drawLine(0.9, 0.9, 0.1, 0.1, Color.BLUE, 1f);
			}
		});
	}
	
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "F+F--F+F")
		.setAxiom("F")
		.build();
	}
}
