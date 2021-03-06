package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/** 
 * This program implements simple static lexical analyzer
 * Lexer is used as token producer. Token type is defined in enumeration TokenType.
 *         
 * @author Daria Matković
 *
 */
public class LexerSmart {
	// given text
	private char[] data;
	// current token
	private TokenSmart token;
	// index of first unanalyzed sign
	private int currentIndex;
	private LexerSmartState lexerState;
	private boolean tagNameAdded = false;
	private boolean tagElementsAdded = false;
	private boolean escapeSequence = false;
	
	/**
	 * Constructor get text that need to be analyzed.
	 * 
	 * @param text text need to be analyzed.
	 */
	public LexerSmart(String text) {
		Objects.requireNonNull(text, "Input is null");
		
		data = text.toCharArray();
		currentIndex = 0;
		setState(LexerSmartState.BASIC);
	} 
	
	private String addTagName() {
		String tagName = "";
		
		// skip whitespace
		while(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == ' ') {
			currentIndex++;
		}
		
		// build tag name while whitespace doesn't occur or while tag close 
		// doesn't occur
		while(!Character.isWhitespace(data[currentIndex]) &&
				data[currentIndex] != '$' && data[currentIndex+1] != '}') {
			tagName += data[currentIndex++];
			
			// if tag name is = break after = is added in tag name
			if(data[currentIndex-1] == '=') {
				break;
			}
		}
		
		return tagName;
	}
	
	/**
	 * Generates and returns next token. Throws LexerException if error occurs.
	 * 
	 * @return next token
	 */
	public TokenSmart nextToken() {
		String stringValue = "";
		
		// EOF
		if(currentIndex == data.length) {
			currentIndex++;
			token = new TokenSmart(TokenSmartType.EOF, null);
			return token;
		}
		
		// call after EOF
		if(currentIndex > data.length) {
			throw new LexerSmartException();
		}
		
		// work in basic mode
		while(currentIndex <= data.length-1 && lexerState == LexerSmartState.BASIC) {
			// if tag occurs, break
			if(data[currentIndex] == '{' && data[currentIndex+1] == '$' && !escapeSequence) {
				// if \ is before tag, continue building text, otherwise return token
				setState(LexerSmartState.TAG);
				
				// if value is not empty make token and return it
				if(!stringValue.isEmpty()) {
					token = new TokenSmart(TokenSmartType.TEXT, stringValue);
					currentIndex++;
					return token;
				}
				
			// if end, return text
			} else if(currentIndex + 1 > data.length-1) {
				stringValue += data[currentIndex++];
				token = new TokenSmart(TokenSmartType.TEXT, stringValue);
				
				return token;
			}
			// if basic mode continue adding text
			// if escape sequence starts
			if(data[currentIndex] == '\\' && !escapeSequence) {
				// if escape sequence is valid
				if(currentIndex + 1 < data.length && (data[currentIndex + 1] == '\\'
						|| data[currentIndex + 1] == '{')) {
					escapeSequence = true;
					currentIndex++;
					continue;
					
				} else {
					throw new LexerSmartException("Invalid escaping.");
				}
				
			} else if(data[currentIndex] == '{' && escapeSequence) {
				// escaping {
				escapeSequence = false;
				lexerState = LexerSmartState.BASIC;
				
				// escaping /
			} else if(data[currentIndex] == '\\' && escapeSequence) {
				escapeSequence = false;
				// add basic element
			}
			stringValue += data[currentIndex++];
		} 
		// tag occurred
		if(currentIndex-1 >= 0 && data[currentIndex-1] == '{' &&
				data[currentIndex] == '$' && lexerState == LexerSmartState.TAG
				&& !escapeSequence) {
			// if tag open occurred go to tag state
			setState(LexerSmartState.TAG);
			token = new TokenSmart(TokenSmartType.TAG_OPEN, "{$");
			currentIndex ++;
			
			return token;
		} 
		// return tag name token
		if(lexerState == LexerSmartState.TAG && !tagNameAdded) {
			tagNameAdded = true;
			token = new TokenSmart(TokenSmartType.TAG_NAME, addTagName());
			
			return token;
		}
		
		// add tag elements, only if tag name is not end
		// tag end doesn't have elements
		if(lexerState == LexerSmartState.TAG && tagNameAdded && !tagElementsAdded &&
				!"end".equalsIgnoreCase(getToken().getValue().toString())) {
			String tagElements = "";
			
			// flag used for detecting string, because only in strings (in tag)
			// should use escaping
			boolean inString = false;
			
			// build tagElements value
			while(currentIndex + 1 < data.length) {
				char current = data[currentIndex];
				
				// if tag close occurred, checks that escapeSequence is off
				if(!escapeSequence && data[currentIndex] == '$' &&
						data[currentIndex+1] == '}' ) {
					break;
					
					// if illegal escape occurred
				} else if(escapeSequence && !(data[currentIndex] == '\\' ||
						data[currentIndex] == '"')) {
					throw new LexerSmartException("Invalid escape.");
					
					//escapeSequence on
				} else if(!escapeSequence && data[currentIndex] == '\\') {
					escapeSequence = true;
					
					// add element and turn escapeSequence off
				} else {
					
					if(!(Character.isDigit(current) || Character.isAlphabetic(current)
							|| current == '_' || current == '*' || current == '+'
							|| current == '/' || current == '^' || current == '-'
							|| current == ' ' || current == '@' || current == '\"'
							|| current == '.' || Character.isWhitespace(current) 
							|| current == '\\') && !inString) {
						throw new LexerSmartException("Invalid expression");
					}
					
					// if string occurred and escapeSequence is off change state of inString
					if(data[currentIndex] == '\"' && !escapeSequence) {
						inString = !inString;
						
					// if string occurred and escapeSequense is on, don't change state of inString
					// change escapeSequence to false, if " escaped
					} else if((Character.isWhitespace(data[currentIndex]) ||
							data[currentIndex] == '\"' || data[currentIndex] == '\\') &&
							escapeSequence) {
						tagElements += '\\';
						escapeSequence = false; 
					}
					
					tagElements += data[currentIndex];
					escapeSequence = false;
				}

				currentIndex++;
			}
			// no more elements, but tag didn't close
			if(currentIndex == data.length-1) {
				throw new LexerSmartException("Tag didn''t close.");
			}
			
			token = new TokenSmart(TokenSmartType.TAG_ELEMENT, tagElements);
			tagElementsAdded = true;
			
			return token;
		}
		
		// if tag elements and tag name added, add tag close
		// tag elements added when close tag occurred, so next element is tag close
		if(lexerState == LexerSmartState.TAG && tagNameAdded && (tagElementsAdded
				|| "end".equalsIgnoreCase(getToken().getValue().toString()))) {
			
			token = new TokenSmart(TokenSmartType.TAG_CLOSE, "$}");
			currentIndex += 2;
			setState(LexerSmartState.BASIC);
			
			tagElementsAdded = false;
			tagNameAdded = false;
			
			return token;
		}
		throw new LexerSmartException("No more token in given document.");
	}
	
	/**
	 * Gets last generated token. Can be called more times and it doesn't runs
	 * generation of next token.
	 * 
	 * @return next token
	 */
	public TokenSmart getToken() {
		return token;
	}
	
	/**
	 * Set lexer state.
	 * @param state new state
	 */
	public void setState(LexerSmartState state) {
		Objects.requireNonNull(state, "Lexer state can't be null.");
		
		lexerState = state;
	}
}
