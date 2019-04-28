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
		int i = 0;
		char[] commandCharArray = expression.trim().toCharArray();
		
		boolean stringSequence = false;
		boolean escapeSequence = false;
		
		if(commandCharArray[0] == '"' && commandCharArray[commandCharArray.length] == '"') {
			stringSequence = true;
		}
		
		List<NameBuilder> nameBuilderList = new ArrayList<NameBuilder>();
		StringBuilder buildArgument = new StringBuilder();
		
		do {
			// escape if escaping sequence occurs
			if(escapeSequence && stringSequence && (commandCharArray[i] == '\\' || 
					commandCharArray[i] == '"')) {
				escapeSequence = false;
				buildArgument.append(commandCharArray[i]);
			}
			
			// write two chars if not escape sequence
			else if(escapeSequence && (commandCharArray[i] != '\\' || commandCharArray[i] != '"')) {
				buildArgument.append('\\');
				
				if(commandCharArray[i] == '$' && commandCharArray[i+1] == '{') {
					// if group sequence occurs, save built text
					nameBuilderList.add(text(buildArgument.toString()));
					buildArgument = new StringBuilder();
					
					IndexAndNameBuilder helpResult = addGroup(i, commandCharArray);
					nameBuilderList.add(helpResult.nameBuilder);
					i = helpResult.index;
					
					buildArgument = new StringBuilder();
					
				} else {
					buildArgument.append(commandCharArray[i]);
				}
				escapeSequence = false;
			}
			
			// if escapeSequence occurs outside of string sequence return null	
			else if(commandCharArray[i] == '\\' && !stringSequence) {
				return null;
			}
			
			// escapeSequence starts if \ occurred in quotes and escapeSequence was false
			else if(commandCharArray[i] == '\\' && stringSequence && !escapeSequence) {
				escapeSequence = true;
			}
			
			else {
				if(commandCharArray[i] == '$' && commandCharArray[i+1] == '{') {
					// if group sequence occurs, save built text
					nameBuilderList.add(text(buildArgument.toString()));
					buildArgument = new StringBuilder();
					
					IndexAndNameBuilder helpResult = addGroup(i, commandCharArray);
					nameBuilderList.add(helpResult.nameBuilder);
					i = helpResult.index;
					
				} else {
					buildArgument.append(commandCharArray[i]);
				}
			}
			
		} while(i++ < commandCharArray.length-1);

		nameBuilderList.add(text(buildArgument.toString()));
		
		return nameBuilderList;
	}
	
	private static class IndexAndNameBuilder {
		int index;
		NameBuilder nameBuilder;
	}
	
	private IndexAndNameBuilder addGroup(int i, char[] commandCharArray) {
		
		IndexAndNameBuilder result = new IndexAndNameBuilder();
		
		StringBuilder buildArgument = new StringBuilder();
		// take group sequence
		i += 2;
		while(commandCharArray[i] != '}') {
			buildArgument.append(commandCharArray[i++]);
		}
		
		if(buildArgument.toString().contains(",")) {
			String help = buildArgument.toString().replace("\\s+", "");
			
			String[] helpArray = help.split(",");
			
			result.index = i;
			result.nameBuilder = group(Integer.valueOf(helpArray[0]), helpArray[1].charAt(0),
					Integer.valueOf(helpArray[1].substring(1, helpArray[1].length())));
			
			return result;
			
		} else {
			result.index = i;
			result.nameBuilder = group(Integer.valueOf(buildArgument.toString()));
			
			return result;
		}
	}
}
