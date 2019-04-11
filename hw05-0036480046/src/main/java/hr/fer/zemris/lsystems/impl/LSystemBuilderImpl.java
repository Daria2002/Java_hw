package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * This class is used for managing properties for drawing fractals, implements
 * LSystemBuilder
 * @author Daria Matković
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/** registered commands **/
	private Dictionary<Character, Command> commandDictionary = new Dictionary<>();
	/** registered actions **/
	private Dictionary<Character, String> productionDictionary = new Dictionary<>();
	
	/**
	 * Default properties
	 */
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";
		
	/**
	 * Commands
	 */
	private final static String DRAW = "draw";
	private final static String SKIP = "skip";
	private final static String SCALE = "scale";
	private final static String ROTATE = "rotate";
	private final static String PUSH = "push";
	private final static String POP = "pop";
	private final static String COLOR = "color";
	
	/**
	 * Actions
	 */
	private final static String ORIGIN = "origin";
	private final static String ANGLE = "angle";
	private final static String UNIT_LENGTH = "unitLength";
	private final static String UNIT_LENGTH_DEGREE_SCALER = "unitLengthDegreeScaler";
	private final static String COMMAND = "command";
	private final static String AXIOM = "axiom";
	private final static String PRODUCTION = "production";
	
	/**
	 * nested class that implements LSystem and draw fractals
	 * @author Daria Matković
	 *
	 */
	private class NestedLSystem implements LSystem {

		/**
		 * Gets length
		 * @param d depth
		 * @return length
		 */
		private double getInitialEffectiveLength(int d) {
			return LSystemBuilderImpl.this.unitLength * 
					Math.pow(LSystemBuilderImpl.this.unitLengthDegreeScaler, d);
		}
		
		@Override
		public void draw(int depth, Painter painter) {
			System.out.println("Hello from draw()");
			System.out.println("Current origin is: " + LSystemBuilderImpl.this.origin.toString());
			
			Context newContext = new Context();
			TurtleState newState = new TurtleState(
					LSystemBuilderImpl.this.origin.copy(),
					new Vector2D(1, 0).rotated(LSystemBuilderImpl.this.angle * Math.PI/180),
					Color.BLACK, getInitialEffectiveLength(depth));
			
			newContext.pushState(newState);
			
			String generatedAxiom = generate(depth);
			
			for(int i = 0; i < generatedAxiom.length(); i++) {
				System.out.println("Getting command for: " + generatedAxiom.charAt(i));
				
				Command command = LSystemBuilderImpl.this.commandDictionary
						.get(generatedAxiom.charAt(i));
				
				// if command doesn't exist
				if(command == null) {
					continue;
				}
				
				System.out.println(command.toString());
				command.execute(newContext, painter);
				System.out.println();
			}
		}

		@Override
		public String generate(int depth) {
			String axiomString = String.valueOf(LSystemBuilderImpl.this.axiom);
			String axiomStringHelp = "";
			
			if(depth == 0) {
				axiomStringHelp = axiomString;
			}
			
			for(int i = 0; i < depth; i++) {
				if(i > 0) {
					axiomString = String.valueOf(axiomStringHelp);
					axiomStringHelp = "";
				}
				for(int k = 0; k < axiomString.length(); k++) {
					String value = LSystemBuilderImpl.this.productionDictionary
							.get(axiomString.charAt(k));
					
					if(value != null) {
						axiomStringHelp += axiomString.substring(k, k+1)
								.replaceAll(String.valueOf(axiomString.charAt(k)), value);
					} else {
						axiomStringHelp += axiomString.charAt(k);
					}
				}
			}
			
			System.out.println("depth="+depth+", axiom" + axiomStringHelp);
			return axiomStringHelp;
		}
	}
	
	@Override
	public LSystem build() {
		return new NestedLSystem();
	}

	@Override
	public LSystemBuilder configureFromText(String[] actions) {
		
		for(int i = 0; i < actions.length; i++) {
			if(actions[i].isEmpty()) {
				continue;
			}
			
			String[] array = actions[i].trim().split("\\s+");
			
			switch (array[0]) {
			case COMMAND:
				if(array[1].length() != 1) {
					throw new IllegalArgumentException("Illegal action arguments");
				}
				
				StringBuilder builderCommand = new StringBuilder();
				
				for(int l = 2; l < array.length; l++) {
					builderCommand.append(array[l]).append(" ");
				}
				
				registerCommand(array[1].charAt(0), builderCommand.toString());
				break;

			case ORIGIN:
				if(array.length != 3) {
					throw new IllegalArgumentException("Illegal action arguments");
				}
				try {
					this.setOrigin(Double.parseDouble(array[1]),
							Double.parseDouble(array[2]));
				} catch (Exception e) {
					throw new IllegalArgumentException("Illegal origin action");
				}
				break;	
				
			case ANGLE:
				if(array.length != 2) {
					throw new IllegalArgumentException("Illegal action arguments");
				}
				this.setAngle(parseArgument(array));
				break;	
				
			case UNIT_LENGTH:
				if(array.length != 2) {
					throw new IllegalArgumentException("Illegal action arguments");
				}
				this.setUnitLength(parseArgument(array));
				break;
			
			case UNIT_LENGTH_DEGREE_SCALER:
				if(array.length < 2) {
					throw new IllegalArgumentException("Illegal action arguments");
				} 
				
				if(array.length == 2) {
					setUnitLength(parseArgument(array));
					continue;
					
				}
				
				StringBuilder builder = new StringBuilder();
				for(int k = 1; k < array.length; k++) {
					builder.append(array[k]);
				}
				
				String[] arrayOperation = String.valueOf(builder).trim().split("/");
				if(arrayOperation.length != 2) {
					throw new IllegalArgumentException("Illegal unit length degree "
							+ " scaler action.");
				}
				
				try {
					setUnitLengthDegreeScaler(Double.parseDouble(arrayOperation[0]) / 
							Double.parseDouble(arrayOperation[1]));
				} catch (Exception e) {
					throw new IllegalArgumentException("Illegal unit length degree "
							+ " scaler action.");
				}
				break;	
			
			case AXIOM:
				if(array.length != 2) {
					throw new IllegalArgumentException("Illegal action arguments");
				}
				setAxiom(array[1]);
				break;
			
			case PRODUCTION:
				if(array.length != 3 || array[1].length() != 1) {
					throw new IllegalArgumentException("Illegal action arguments");
				}
				registerProduction(array[1].charAt(0), array[2]);
				break;	
				
			default:
				throw new IllegalArgumentException("Invalid action name.");
			}
		}
		
		return this;
	}

	/**
	 * Parse command argument
	 * @param commandArgument command argument
	 * @return parsed command argument
	 */
	private double parseArgument(String[] commandArgument) {
		try {
			return Double.parseDouble(commandArgument[1]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Illegal argument");
		}
	}
	
	/**
	 * Gets command
	 * @param commandString given string
	 * @return Command of given type
	 */
	private Command getCommand(String commandString) {
		if(commandString.isEmpty()) {
			throw new IllegalArgumentException("Command can't be empty string");
		}
		
		String[] array = commandString.trim().split("\\s+");
		if(array.length > 2) {
			throw new IllegalArgumentException("Illegal command");
		}
		
		double argument;
		switch (array[0].toLowerCase()) {
		case DRAW:
			argument = parseArgument(array);
			return new DrawCommand(argument);
			
		case SKIP:
			argument = parseArgument(array);
			return new SkipCommand(argument);
			
		case SCALE:
			argument = parseArgument(array);
			return new ScaleCommand(argument);
		
		case ROTATE:
			argument = parseArgument(array);
			return new RotateCommand(argument);
			
		case PUSH:
			if(array.length != 1) {
				throw new IllegalArgumentException("Illegal command push");
			}
			return new PushCommand();
			
		case POP:
			if(array.length != 1) {
				throw new IllegalArgumentException("Illegal command pop");
			}
			return new PopCommand();
		
		case COLOR:
			if(array.length != 2) {
				throw new IllegalArgumentException("Illegal command color");
			}
			return new ColorCommand(Color.decode("#" + array[1]));
		
		default:
			throw new IllegalArgumentException("Illegal command " + array[0]);
		}
	}
	
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		Command command = getCommand(arg1);
		commandDictionary.put(arg0, command);
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productionDictionary.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
}
