package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration for token type.
 * @author Daria Matkovic
 *
 */
public enum TokenType {
	/** Last token in file. **/
	EOF,
	/** Token is string. **/
	WORD,
	/** Token is number **/
	NUMBER, 
	/**
	 * Token is symbol.
	 * Symbol is everything in text that is not number, word or any
	 * escape ('\r', '\n', '\t', ' ').
	 */
	SYMBOL
}
