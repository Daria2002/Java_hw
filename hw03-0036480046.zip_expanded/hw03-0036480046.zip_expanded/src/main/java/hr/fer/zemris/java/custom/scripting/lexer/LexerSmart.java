package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.NoSuchElementException;
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
	
	public LexerSmartState getLexerState() {
		return lexerState;
	}
	
	/**
	 * Generates and returns next token. Throws LexerException if error occurs.
	 * 
	 * @return next token
	 */
	public TokenSmart nextToken() {
		if(lexerState.equals(LexerSmartState.BASIC)) {
			token = getBasicToken();
			return token;
		}
		
		token = getTagToken();
		return token;
	}
	
	/**
	 * This method returns token when lexer is in tag node.
	 * In tag mode only escape in string is possible. Possible escapes are
	 * \\, \" 
	 * @return
	 */
	private TokenSmart getTagToken() {
		StringBuilder tokenValue = new StringBuilder();
		
		while (currentIndex < data.length) {
			// skip whitespace
			while(Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			}
			
			// if next token is tag close (first element is tag open)
			if(data[currentIndex] == '$'  && currentIndex + 1 < data.length 
					&& data[currentIndex + 1] == '}' && tokenValue.length() == 0) {
				currentIndex = currentIndex + 2;
				return new TokenSmart(TokenSmartType.TAG_CLOSE, "$}");
			}
			
			// if last token was TAG_OPEN, next token should be TAG_NAME
			else if(getToken().getType() == TokenSmartType.TAG_OPEN) {
				return new TokenSmart(TokenSmartType.TAG_NAME , getTagName());
			}
			
			// if last token was TAG_NAME or TAG_ELEMENT, next token can be TAG_ELEMENT or
			// TAG_CLOSE
			else if(getToken().getType() == TokenSmartType.TAG_ELEMENT || 
					getToken().getType() == TokenSmartType.TAG_NAME) {
				
				// if there are quotes, get string under quotes
				if(data[currentIndex] == '\"') {
					return getTokenUnderQuotes();
				}
				
				// if value is function
				if(currentIndex < data.length && data[currentIndex] == '@') {
					return getFunction();
				}
				
				// if token is operator
				if(currentIndex < data.length && data[currentIndex] == '*' ||
						data[currentIndex] == '^' || data[currentIndex] == '/' ||
						data[currentIndex] == '-' || data[currentIndex] == '+') {
					currentIndex++;
					return new TokenSmart(TokenSmartType.TAG_ELEMENT, data[currentIndex - 1]);
				}
				
				// if value is not under quotes, check if token is variable name
				if(currentIndex < data.length && Character.isAlphabetic(data[currentIndex])) {
					return getWordTagElement();
					
				} else if(currentIndex < data.length && (Character.isDigit(data[currentIndex]) ||
						data[currentIndex] == '-')) {
					return getNumberTagElement();
				}
			}
		}
		
		return null;
	}

	private TokenSmart getTokenUnderQuotes() {

		boolean escapeSequence = false;
		currentIndex++;
		StringBuilder tokenValue = new StringBuilder();
		
		// building token whose value is under string
		while(currentIndex < data.length) {
			// throw exception if escape sequence is on and invalid escaping occurs
			if(data[currentIndex] != '\\' && data[currentIndex] != '\"' && escapeSequence) {
				throw new IllegalArgumentException("Invalid escaping in quotes");
			}
			
			// if valid escape
			else if((data[currentIndex] == '\\' || data[currentIndex] == '\"') && escapeSequence) {
				escapeSequence = false;
				tokenValue.append(data[currentIndex++]);
				continue;
			}
				
			// if \ occurs, escape sequence is on
			else if(data[currentIndex] == '\\' && !escapeSequence) {
				escapeSequence = true;
				currentIndex++;
				continue;
			}
			
			// quoted value ends
			if(data[currentIndex] == '\"' && !escapeSequence) {
				currentIndex++;
				break;
			}
			
			tokenValue.append(data[currentIndex++]);
		}
		
		return new TokenSmart(TokenSmartType.TAG_ELEMENT,  "\"" + tokenValue + "\"");
	}
	
	private TokenSmart getFunction() {
		StringBuilder tokenValue = new StringBuilder();
		
		while(data[currentIndex] != ' ') {
			tokenValue.append(data[currentIndex++]);
		}
		
		return new TokenSmart(TokenSmartType.TAG_ELEMENT, tokenValue);
	}

	private TokenSmart getNumberTagElement() {
		StringBuilder tokenValue = new StringBuilder();
		
		if(currentIndex + 1 >= data.length) {
			throw new IllegalArgumentException("Illegal argument");
		}
		
		tokenValue.append(data[currentIndex++]);
		while(data[currentIndex] != ' ' && Character.isDigit(data[currentIndex])) {
			tokenValue.append(data[currentIndex++]);
		}
		
		return new TokenSmart(TokenSmartType.TAG_ELEMENT, tokenValue);
	}

	private TokenSmart getWordTagElement() {
		StringBuilder tokenValue = new StringBuilder();
		
		// build token value while current char is not operator, whitespace or quote
		while(!Character.isWhitespace(data[currentIndex]) &&
				data[currentIndex] != '\"' && data[currentIndex] != '-' &&
				data[currentIndex] != '+' && data[currentIndex] != '/' &&
				data[currentIndex] != '*' && data[currentIndex] != '^' && 
				currentIndex < data.length) {
			
			tokenValue.append(data[currentIndex++]);
			
			// if TAG_CLOSE occurs
			if(data[currentIndex] == '$' && currentIndex + 1 < data.length &&
				data[currentIndex + 1] == '}') {
				break;
			}
		}
		
		return new TokenSmart(TokenSmartType.TAG_ELEMENT, tokenValue);
	}

	/**
	 * This method returns tag name.
	 * @return
	 */
	private Object getTagName() {
		StringBuilder tagName = new StringBuilder();
		
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
		
		if(data[currentIndex] == '=') {
			currentIndex++;
			return "=";
		}
		
		while(!Character.isWhitespace(data[currentIndex])) {
			// if TAG_CLOSE occurs
			if(data[currentIndex] == '$' && currentIndex + 1 < data.length &&
				data[currentIndex + 1] == '}') {
				break;
			}
			
			tagName.append(data[currentIndex++]);
		}
		
		return tagName;
	}

	/**
	 * This method returns token when lexer is in basic mode. 
	 * In basic mode only \\ and \{ escapes are allowed.
	 * @return
	 */
	private TokenSmart getBasicToken() {
		StringBuilder tokenValue = new StringBuilder();
		// escape sequence is true if last char was \
		boolean escapeSequence = false;
		boolean stringSequence = false;
		
		// EOF
		if(currentIndex == data.length) {
			currentIndex++;
			return new TokenSmart(TokenSmartType.EOF, null);
		}
		
		// there are no more tokens
		if(currentIndex > data.length) {
			throw new NoSuchElementException("There are no more elements");
		}
		
		while(data[currentIndex] == ' ') {
			currentIndex++;
		}
		
		// tag close can occur after tag name or tag element
		if(getToken() != null && (getToken().getType() == TokenSmartType.TAG_ELEMENT
				|| getToken().getType() == TokenSmartType.TAG_NAME)&&
				data[currentIndex] == '$' && currentIndex+1 < data.length &&
				data[currentIndex+1] == '}') {
			currentIndex += 2;
			return new TokenSmart(TokenSmartType.TAG_CLOSE, "$}");
		}
		
		// check if token is string  expression
		// string expression ends with quote "
		if(data[currentIndex] == '"') {
			currentIndex++;
			tokenValue.append("\"");
			stringSequence = true;
		}
		
		while(currentIndex < data.length) {
			// check if end of quoted sequence
			if(stringSequence && !escapeSequence && data[currentIndex] == '"') {
				currentIndex++;
				return new TokenSmart(TokenSmartType.TAG_ELEMENT, String.valueOf(tokenValue.append("\"")));
			}
			
			// tag element stops when tag close occurs
			if(getToken() != null && (getToken().getType() == TokenSmartType.TAG_ELEMENT
					|| getToken().getType() == TokenSmartType.TAG_NAME)&&
					data[currentIndex] == '$' && currentIndex+1 < data.length &&
					data[currentIndex+1] == '}') {
				return new TokenSmart(TokenSmartType.TAG_ELEMENT, String.valueOf(tokenValue));
			}
			
			// tag element can occur after tag element or tag name
			if(getToken() != null && (getToken().getType() == TokenSmartType.TAG_ELEMENT
					|| getToken().getType() == TokenSmartType.TAG_NAME) &&
					tokenValue.length() > 0 && !stringSequence && (data[currentIndex] == '"' 
					|| data[currentIndex] == ' ' || data[currentIndex] == '}')) {
				return new TokenSmart(TokenSmartType.TAG_ELEMENT, String.valueOf(tokenValue));
			}
			
			// tag name should be after tag open token
			if(getToken() != null && getToken().getType() == TokenSmartType.TAG_OPEN &&
					(data[currentIndex] == ' ' || data[currentIndex] == '$')) {
				return new TokenSmart(TokenSmartType.TAG_NAME, String.valueOf(tokenValue));
			}
			
			// if next token is tag open (first element is tag open)
			if(data[currentIndex] == '{'  && currentIndex + 1 < data.length 
					&& data[currentIndex + 1] == '$' && tokenValue.length() == 0) {
				currentIndex += 2;
				return new TokenSmart(TokenSmartType.TAG_OPEN, "{$");
			}
			
			// return text if {$ occurs and it is not escape sequence
			else if(data[currentIndex] == '{' && currentIndex + 1 < data.length
					&& data[currentIndex + 1] == '$' && !escapeSequence) {
				return new TokenSmart(TokenSmartType.TEXT, String.valueOf(tokenValue));
			}
			
			// if \ occurs and escape sequence is off, turn escape sequence on
			else if(data[currentIndex] == '\\' && !escapeSequence) {
				escapeSequence = true;
				currentIndex++;
				continue;
			}
			
			// if escape sequence on and next char \ or {, escape \
			else if((data[currentIndex] == '\\' || data[currentIndex] == '{') && escapeSequence) {
				escapeSequence = false;
				tokenValue.append(data[currentIndex++]);
				continue;
			}
			
			// if escape sequence on and next char can't be escaped
			else if(data[currentIndex] != '\\' && data[currentIndex] != '{' && escapeSequence) {
				throw new IllegalArgumentException("It is possible to escape only { and \\ outside of tags");
			}
			
			// build token value
			tokenValue.append(data[currentIndex++]);
		}
		
		return new TokenSmart(TokenSmartType.TEXT, String.valueOf(tokenValue));
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
