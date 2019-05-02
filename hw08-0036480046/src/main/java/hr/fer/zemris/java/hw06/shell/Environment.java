package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This interface will be passed to each defined command. The each
 * implemented command communicates with user only through this interface.
 * @author Daria Matković
 *
 */
public interface Environment {
	
	/**
	 * Reads whole command (all lines of one command)
	 * @return String that represents whole command
	 * @throws ShellIOException throws if reading fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes given text to user(console)
	 * @param text text to write to console
	 * @throws ShellIOException throws if writing fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes given text to user(console) and a new line
	 * @param text text to write to console
	 * @throws ShellIOException throws if writeln fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method returns unmodifiable map, so that the client can not delete
	 * commands by clearing the map;
	 * @return Sorted map where key is String and value is ShellCommand
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns symbol for multiline.
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets multiline symbol. Multiline symbol is symbol at beginning,
	 * when command on morelines is written.
	 * @param symbol new multiline symbol value
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns prompt symbol
	 * @return prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets prompt symbol
	 * @param symbol new prompt symbol value
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Gets morelines symbol. Morelines symbol is at the end, when command on
	 * morelines is written.
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets morelines symbol
	 * @param symbol new value for morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Gets path to current directory
	 * @return path to current directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets current directory
	 * @param path path to new current directory
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * This method returns saved data for given key
	 * @param key key for required data
	 * @return data if given key exists, otherwise null
	 */
	Object getSharedData(String key);
	
	/**
	 * This method sets shared data.
	 * @param key key for data to save
	 * @param value to save for given key
	 */
	void setSharedData(String key, Object value);
}
