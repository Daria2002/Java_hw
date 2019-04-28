package hr.fer.zemris.java.hw06.shell.commands;

import java.util.regex.Matcher;

/**
 * This class represents file with info about number of groups and method for
 * getting group at given index
 * @author Daria Matković
 *
 */
public class FilterResult {
	/** file name **/
	private String fileName;
	/** matcher **/
	private Matcher matcher;
	
	/**
	 * Constructor that initialize file name and matcher
	 * @param fileName file name
	 * @param m matcher
	 */
	public FilterResult(String fileName, Matcher m) {
		this.fileName = fileName;
		this.matcher = m;
	}
	
	/**
	 * Returns file name
	 */
	public String toString() {
		return fileName;
	}
	
	/**
	 * Gets number of groups
	 * @return number of groups
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}
	
	/**
	 * Gets group at given index
	 * @param index group index
	 * @return group at given index
	 */
	public String group(int index) {
		return matcher.group(index);
	}
}
