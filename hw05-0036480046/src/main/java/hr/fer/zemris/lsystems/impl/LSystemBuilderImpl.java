package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;

public class LSystemBuilderImpl implements LSystemBuilder {
	/** registered commands **/
	private Dictionary<Character, String> registeredCommands;
	/** registered actions **/
	private Dictionary<Character, String> registeredProduction;
	
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	private class NestedLSystem extends LSystemBuilderImpl implements LSystem {
		private double unitLength;
		private double unitLengthDegreeScaler;
		private Vector2D origin;
		private double angle;
		private String axiom;
		
		public NestedLSystem() {
			/** default properties if dictionary is empty **/
			if(LSystemBuilderImpl.this.registeredCommands.isEmpty()) {
				this.unitLength = 0.1;
				this.unitLengthDegreeScaler = 1;
				this.origin = new Vector2D(0, 0);
				this.angle = 0;
				this.axiom = "";
			} else {
				this.unitLength = LSystemBuilderImpl.this.unitLength;
				this.unitLengthDegreeScaler = LSystemBuilderImpl.this.unitLengthDegreeScaler;
				this.origin = LSystemBuilderImpl.this.origin;
				this.angle = LSystemBuilderImpl.this.angle;
				this.axiom = LSystemBuilderImpl.this.axiom;
			}
		}
		
		@Override
		public void draw(int depth, Painter arg1) {
			Context newContext = new Context();/*
			TurtleState newState = new TurtleState(
					currentVector, currentUnitVector, color, stepLength);*/
			generate(depth);
		}

		@Override
		public String generate(int depth) {
			char nekiAksiom = 'F';
			String aksiomString = String.valueOf(nekiAksiom);
			String aksiomHelp = String.valueOf(nekiAksiom);
			String production = "F+F--F+F";
			
			for(int i = 0; i < depth; i++) {
				aksiomString = aksiomString.replaceAll(aksiomHelp, production);
				System.out.println("Novi vrijednost aksiomStringa: " + aksiomString);
			}
			
			return aksiomString;
		}
	}
	
	@Override
	public LSystem build() {
		return new NestedLSystem();
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(int i = 0; i < arg0.length; i++) {
			if(arg0[i] == "") {
				continue;
			} else {
				// TODO: parse
			}
		}
		
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		registeredCommands.put(arg0, arg1);
		return null;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		registeredProduction.put(arg0, arg1);
		return null;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return null;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return null;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return null;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return null;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return null;
	}
}
