package hr.fer.zemris.java.hw03.prob1;

/** 
 * This program implements simple static lexical analyzer
 * Lexer is used as token producer. Token type is defined in enumeration TokenType.
 *         
 * @author Daria Matković
 *
 */
public class Lexer {
	// given text
	private char[] data;
	// current token
	private Token token;
	// index of first unanalyzed sign
	private int currentIndex;
	
	/**
	 * Constructor get text that need to be analyzed.
	 * 
	 * @param text text need to be analyzed.
	 */
	public Lexer(String text) {
		data = text.toCharArray();
	} 
	
	/**
	 * Generates and returns next token. Throws LexerException if error occurs.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		
		String buildValue = "";
		for(int i = currentIndex; i < data.length; i++) {
			// check if string needs to be skipped
			if('\n' == (data[i]) || '\t' == (data[i]) || 
					'\r' == (data[i]) || ' ' == (data[i])) {
				currentIndex = i+1; //TODO: provjeri da ne uzima currentIndex > size
				break;
			}
			if (i > 0) {
				if(getCharType(data[i]) != getCharType(data[i-1])) {
					currentIndex = i+1;
					break;
				}
			}
			buildValue += data[i];
		}
		
		TokenType type = getTokenType(buildValue);
		Object value = getObject(type, buildValue);
		
		return new Token(type, value);
	}
	
	/**
	 * Parse to long is value is number, otherwise returns given value
	 * 
	 * @param type
	 * @param value
	 * @return long number if value is number, null if value is EOF,
	 * otherwise given value
	 */
	private Object getObject(TokenType type, String value) {
		if(type == TokenType.EOF) {
			return null;
		} else if(type == TokenType.NUMBER) {
			return Long.parseLong(value);
		} else {
			return value;
		}
	}
	
	/**
	 * Analyzes given value to get token type.
	 * 
	 * @param value value to be analyzed
	 * @return TokenType of given value
	 */
	public TokenType getTokenType(String value) {
		if(isNumber(value)) {
			return TokenType.NUMBER;
		} else if(isWord(value)) {
			return TokenType.WORD;
		} else if(isSymbol(value)) {
			return TokenType.SYMBOL;
		} else if(isEOF(value)) {
			return TokenType.EOF;
		} else {
			throw new LexerException();
		}
	}
	
	/**
	 * Checks if charValue is number, letter, symbol or null.
	 * 
	 * @param charValue value to check what type it is
	 * @return 1 if charValue is number, 2 if charValue is letter, 3 if
	 * charValue is symbol and 4 if charValue id null
	 */
	private int getCharType(char charValue) {

	}
	
	/**
	 * Gets last generated token. Can be called more times and it doesn't runs
	 * generation of next token.
	 * 
	 * @return next token
	 */
	public Token getToken() {
		return null;
	}
	

	public boolean isSymbol(String value) {
		if(value == null) {
			return true;
		}
		return false;
	}
	
	public boolean isEOF(String value) {
		if(value == null) {
			return true;
		}
		return false;
	}
	
	public boolean isWord(String value) {
		return true;
	}
	
	public boolean isNumber(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
