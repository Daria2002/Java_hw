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
		if(text == null) {
			throw new NullPointerException("Input is null.");
		}
		String reducedText = text.replaceAll("\\s+", " ");
		data = reducedText.trim().toCharArray();
		for(int i = 0; i < data.length; i++)
			System.out.println(data[i]);
		currentIndex = 0;
	} 
	
	/**
	 * Generates and returns next token. Throws LexerException if error occurs.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		
		// check if token called after EOF
		// first check if token != null because token.getType() throws exception 
		// if token is null
		if(token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("Called token after EOF");
		} 

		// check if next token is EOF
		if(currentIndex > data.length-1) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return token;
		}

		if(data[currentIndex] == ' ') {
			currentIndex++;
		}
		
		int mode = checkMode(data, currentIndex);
		// if there is \\ on first place
		if(data[currentIndex] == '\\') {
			mode = 2;
			currentIndex++;
		}
		
		int checkMode = 0;
		String stringValue = "";
		stringValue += data[currentIndex];
		
		for(int i = currentIndex+1; i <= data.length-1; i++) {
			checkMode = checkMode(data, i);
			// if mode changed, etc.before letters, now numbers
			if(mode == checkMode) {
				stringValue += data[i];
			}
			if(mode != checkMode || i == data.length-1) {
				currentIndex = i;
				TokenType type;
				switch (mode) {
				case 1:
					type = TokenType.NUMBER;
					Long value = Long.parseLong(stringValue);
					return new Token(type, value);
				case 2:
					type = TokenType.WORD;
					return new Token(type, stringValue);
				case 3:
					type = TokenType.SYMBOL;
					Character valueChar = data[currentIndex];
					return new Token(type, valueChar);
				default:
					type = TokenType.EOF;
					return new Token(type, null);
				}
			}
		}
		return new Token(TokenType.EOF, null);
	}
	
	private int checkMode(char[] data, int index) {
		char c = data[index];
		// if \\ in the middle
		if(index-2 >= 0 && data[index-1] == '\\' && data[index-2] != '\\') {
			return 2;
		}
		if(Character.isDigit(c)) {
			return 1;
		} else if(Character.isAlphabetic(c)) {
			return 2;
		} else if(data[index] == ' ') {
			return 4;
		} else if(index <= data.length-1) {
			// Symbol
			return 3;
		}
		return 0;
	}

	/**
	 * Parse to long if value is number, otherwise returns given value
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
		return 0;
	}
	
	/**
	 * Gets last generated token. Can be called more times and it doesn't runs
	 * generation of next token.
	 * 
	 * @return next token
	 */
	public Token getToken() {
		return token;
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
