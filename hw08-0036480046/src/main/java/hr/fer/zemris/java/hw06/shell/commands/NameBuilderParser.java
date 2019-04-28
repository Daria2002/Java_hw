package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Class that represents parser for building name for new dir
 * @author Daria Matković
 *
 */
public class NameBuilderParser {

	private String expression;
	
	/**
	 * Constructor for parser that builds name of new file
	 * @param izraz expression that defines format of new file name
	 */
	public NameBuilderParser(String izraz) {
		this.expression = izraz;
	}
	
	/**
	 * Gets name builder
	 * @return name builder
	 */
	public NameBuilder getNameBuilder() {
		return new NameBuilder() {
			
			@Override
			public void execute(FilterResult result, StringBuilder sb) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
