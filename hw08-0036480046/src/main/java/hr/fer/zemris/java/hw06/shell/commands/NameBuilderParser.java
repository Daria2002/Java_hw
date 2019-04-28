package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents parser for building name for new dir
 * @author Daria Matković
 *
 */
public class NameBuilderParser {

	/**
	 * Method that build new object of NameBuilde instead of making new class
	 * that builds NameBuilder
	 * @param t
	 * @return
	 */
	static NameBuilder text(String t) { 
		return (result, sb) -> {sb.append(t);}; 
	}
	
	static NameBuilder group(int index) {
		return (result, sb) -> {sb.append(result.group(index));}; 
	}
	
	static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> {sb.append(String.format("%" + minWidth + "s", 
				result.group(index).replace(' ', padding)));};
	}
	
	private String expression;
	
	/**
	 * Constructor for parser that builds name of new file
	 * @param izraz expression that defines format of new file name
	 */
	public NameBuilderParser(String izraz) {
		this.expression = izraz;
	}
	
	private static class NameBuilderComposite implements NameBuilder {

		public List<NameBuilder> nameBuilderList = new ArrayList<NameBuilder>();
		
		public NameBuilderComposite(List<NameBuilder> nameBuilderList) {
			this.nameBuilderList = nameBuilderList;
		}
		
		@Override
		public void execute(FilterResult result, StringBuilder sb) {
			for(NameBuilder nameBuilder : nameBuilderList) {
				nameBuilder.execute(result, sb);
			}
		}
	}
	
	/**
	 * Gets name builder
	 * @return name builder
	 */
	public NameBuilder getNameBuilder() {
		List<NameBuilder> nameBuilderList = makeNameBuilders(expression);
		
		return new NameBuilderComposite(nameBuilderList);
	}

	private List<NameBuilder> makeNameBuilders(String expression) {
		
		
		
		return null;
	}
}
