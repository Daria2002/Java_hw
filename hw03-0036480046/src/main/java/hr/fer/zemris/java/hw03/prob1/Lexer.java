package hr.fer.zemris.java.hw03.prob1;

import java.util.ConcurrentModificationException;

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
		setState(LexerState.BASIC);
	} 
	
	/**
	 * Generates and returns next token. Throws LexerException if error occurs.
	 * 
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
			if((currentIndex-1 >= 0  && data[currentIndex-1] != '\\' && 
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
			if(Character.isAlphabetic(data[currentIndex+1])) {
				throw new LexerException("\\ before WORD");
			}
			mode = 2;
			currentIndex++;
		}
		
		int checkMode = 0;
		String stringValue = "";
		stringValue += data[currentIndex];

		TokenType type;
		for(int i = currentIndex+1; i <= data.length-1; i++) {
			checkMode = checkMode(data, i);
			// checking range if mode is for digit
			if(mode == 1) {
				try {
					long valueLong = Long.parseLong(stringValue);
					if(valueLong < Long.MIN_VALUE || valueLong > Long.MAX_VALUE) {
						throw new LexerException("Too big nuber.");
					}
				} catch (Exception e) {
					throw new LexerException("Too big number");
				}
			}
			
			// if mode changed, etc.before letters, now numbers
			if(mode == checkMode) {
				// don't add \\ if it is used for escaping
				if(data[i] == '\\' && data[i-1] != '\\' && lexerState.equals(LexerState.BASIC)) {
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
				}
			}
			
			if(mode == 2 && data[i-1] == '\\' && checkMode == 3) {
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
					type = TokenType.NUMBER;
					Long value = Long.parseLong(stringValue);
					token = new Token(type, value);
					return token;
				case 2:
					type = TokenType.WORD;
					token = new Token(type, stringValue);
					return token;
				case 3:
					type = TokenType.SYMBOL;
					Character valueChar = data[currentIndex];
					token = new Token(type, valueChar);
					return token;
				default:
					type = TokenType.EOF;
					token = new Token(type, null);
					return token;
				}
			}
		}
		currentIndex++;
		switch (mode) {
		case 1:
			type = TokenType.NUMBER;
			Long value = Long.parseLong(stringValue);
			token = new Token(type, value);
			return token;
		case 2:
			type = TokenType.WORD;
			token = new Token(type, stringValue);
			return token;
		case 3:
			type = TokenType.SYMBOL;
			Character valueChar = data[currentIndex];
			token = new Token(type, valueChar);
			return token;
		default:
			type = TokenType.EOF;
			token = new Token(type, null);
			return token;
		}
		//return new Token(TokenType.EOF, null);
	}
	
	private int checkMode(char[] data, int index) {
		char c = data[index];
		// if \ before data[index] is escaped in the middle
		// if lexer state is extended everything is word
		if(lexerState.equals(LexerState.EXTENDED) || 
				index-2 >= 0 && data[index] == '\\' && data[index-1] != '\\') {
			return 2;
		} else if(Character.isDigit(c)) {
			if(index-1 > 0 && data[index-1] == '\\') {
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
	 * 
	 * @return next token
	 */
	public Token getToken() {
		return token;
	}
	
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Lexer state can't be null.");
		}
		lexerState = state;
	}
}
