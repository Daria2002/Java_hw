package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

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
	LexerState lexerState;
	
	boolean escapeSequence = false;
	
	/**
	 * Constructor get text that need to be analyzed.
	 * @param text text need to be analyzed.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text, "Input is null.");
		
		String reducedText = text.replaceAll("\\s+", " ");
		data = reducedText.trim().toCharArray();
		
		currentIndex = 0;
		setState(LexerState.BASIC);
	} 
	
	/**
	 * Generates and returns next token. Throws LexerException if error occurs.
	 * @return next token
	 */
	public Token nextToken() {
		if(data.length == 1 && data[0] == '\\') {
			throw new LexerException("EOF can't be WORD");
		}
		
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
		
		// return token with symbol
		if(mode == 3) {
			// if symbol need to be word
			// if sign before is ignored so symbol is not word
			if((currentIndex-1 >= 0  && !escapeSequence && 
					data[currentIndex] != '\\') ||
					(currentIndex == 0 && data[currentIndex] != '\\')) {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, data[currentIndex-1]);
				return token;
			}
		}
		
		if(mode == 0) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		// if there is \\ on first place
		if(data[currentIndex] == '\\') {
			if(currentIndex+1 < data.length && 
					!(Character.isDigit(data[currentIndex+1]) ||
							data[currentIndex+1] == '\\')) {
				throw new LexerException("invalid escaping");
			}
			escapeSequence = true;
			mode = 2;
			currentIndex++;
		}
		
		int checkMode = 0;
		String stringValue = "";
		stringValue += data[currentIndex];

		TokenType type;
		for(int i = currentIndex + 1; i <= data.length - 1; i++) {
			checkMode = checkMode(data, i);
			// checking range if mode is for digit
			if(mode == 1) {
				try {
					Long.parseLong(stringValue);
				} catch (Exception e) {
					throw new LexerException("Too big number");
				}
			}
			
			// if mode changed, etc.before letters, now numbers
			if(mode == checkMode) {
				// don't add \\ if it is used for escaping
				if(data[i] == '\\' && !escapeSequence && lexerState.equals(LexerState.BASIC)) {
					escapeSequence = true;
					continue;
				} else {
					if(data[i] == ' ' && lexerState.equals(LexerState.EXTENDED)) {
						currentIndex = i+1;
						token = new Token(TokenType.WORD, stringValue);
						return token;
					} else if(data[i] == '#') {
						currentIndex = i;
						setState(LexerState.BASIC);
						token = new Token(TokenType.WORD, stringValue);
						return token;
					}
					stringValue += data[i];
					escapeSequence = false;
				}
			}
			
			if(mode == 2 && escapeSequence && checkMode == 3) {
				if(data[i] != '\\' && !Character.isDigit(data[i])) {
					throw new LexerException("invalid escape sequence");
				}
				escapeSequence = false;
				stringValue += data[i];
			}
			
			if(mode != checkMode || i == data.length-1) {
				if(i == data.length-1) {
					currentIndex = i+1;
				} else {
					currentIndex = i;
				}
				
				switch (mode) {
				case 1:
					token = new Token(TokenType.NUMBER, Long.parseLong(stringValue));
					return token;
					
				case 2:
					token = new Token(TokenType.WORD, stringValue);
					return token;
					
				case 3:
					token = new Token(TokenType.SYMBOL, data[currentIndex]);
					return token;
					
				default:
					token = new Token(TokenType.EOF, null);
					return token;
				}
			}
		}
		
		currentIndex++;
		switch (mode) {
		case 1:
			token = new Token(TokenType.NUMBER, Long.parseLong(stringValue));
			return token;
			
		case 2:
			token = new Token(TokenType.WORD, stringValue);
			return token;
			
		case 3:
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			return token;
			
		default:
			token = new Token(TokenType.EOF, null);
			return token;
		}
	}
	
	private int checkMode(char[] data, int index) {
		char c = data[index];
		
		// if \ before data[index] is escaped in the middle
		// if lexer state is extended everything is word
		if(lexerState.equals(LexerState.EXTENDED) || 
				index-2 >= 0 && data[index] == '\\' && !escapeSequence) {
			return 2;
			
		} else if(Character.isDigit(c)) {
			if(index-1 > 0 && escapeSequence) {
				return 2;
			}
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
	 * Gets last generated token. Can be called more times and it doesn't runs
	 * generation of next token.
	 * @return next token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Set lexer state
	 * @param state state to set lexer on
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state, "Lexer state can't be null.");
		
		lexerState = state;
	}
}
