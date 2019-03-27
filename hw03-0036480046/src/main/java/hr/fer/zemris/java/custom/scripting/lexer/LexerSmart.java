package hr.fer.zemris.java.custom.scripting.lexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	private String text;
	private boolean tagNameAdded = false;
	private boolean tagElementsAdded = false;
	private boolean escapeSequence = true;
	
	public static void main(String[] args) {
		String testString = ("This is sample text.\\n" + 
				"{$ FOR i 1 10 1 $}\\n" + 
				" This is \\{$= i $}-th time this message is generated.\\n" + 
				"{$END$}\\n" + 
				"{$FOR i-10 10 2 $}\\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \\\"0.000\\\" @decfmt $}\\n" + 
				"{$END$}");
		/*
		String filePath = args[0];
		String content = "";
		 
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
        LexerSmart lexer = new LexerSmart(testString);
        System.out.println();
        
        for(int i = 0; i < 33; i++) {
            System.out.println("Next token is:");
    		lexer.nextToken();
        }
	}
	
	/**
	 * Constructor get text that need to be analyzed.
	 * 
	 * @param text text need to be analyzed.
	 */
	public LexerSmart(String text) {
		if(text == null) {
			throw new NullPointerException("Input is null.");
		}
		//String reducedText = text.replaceAll("\\s+", " ");
		//data = reducedText.trim().toCharArray();
		this.text = text;
		data = text.toCharArray();
		for(int i = 0; i < data.length; i++)
			System.out.print(data[i]);
		currentIndex = 0;
		setState(LexerSmartState.BASIC);
	} 
	
	private String addTagName() {
		String tagName = "";
		// skip whitespace
		while(data[currentIndex] == '\r' || data[currentIndex] == '\t' || 
				data[currentIndex] == '\n' || data[currentIndex] == '\n' ||
				data[currentIndex] == ' ') {
			currentIndex++;
		}
		// build tag name while whitespace doesn't occur or while tag close 
		// doesn't occur
		while(data[currentIndex] != '\r' && data[currentIndex] != '\t' && 
				data[currentIndex] != '\n' && data[currentIndex] != '\n' &&
				data[currentIndex] != ' ' && data[currentIndex] != '$' && 
				data[currentIndex+1] != '}') {
			tagName += data[currentIndex];
			currentIndex++;
			
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
		
		if(currentIndex > data.length-1) {
			currentIndex++;
			token = new TokenSmart(TokenSmartType.EOF, null);
			return token;
		}
		
		// work in basic mode
		while(currentIndex <= data.length-1 && lexerState == LexerSmartState.BASIC) {
			// if tag occurs, break
			if(data[currentIndex] == '{' && data[currentIndex+1] == '$') {
				// if \ is before tag, continue building text, otherwise return token
				if(currentIndex-1 >= 0 && !escapeSequence) {
					token = new TokenSmart(TokenSmartType.TEXT, stringValue);
					setState(LexerSmartState.TAG);
					System.out.println(stringValue);
					return token;
				}
			// if end, return text
			} else if(currentIndex+1 > data.length-1) {
				token = new TokenSmart(TokenSmartType.TEXT, stringValue);
				currentIndex++;
				System.out.println(stringValue);
				return token;
			}
			// if basic mode continue adding text
			/*stringValue += data[currentIndex];*/
			// if escape sequence starts
			if(data[currentIndex] == '\\' && !escapeSequence) {
				// if escape sequence is valid
				if(currentIndex+1 < data.length &&
						(data[currentIndex] == '\\' || data[currentIndex] == '{')) {
					escapeSequence = true;
					currentIndex++;
				} else {
					throw new LexerSmartException("Invalid escaping.");
				}
				// escaping {
			} else if(data[currentIndex] == '{' && escapeSequence) {
				escapeSequence = false;
				lexerState = LexerSmartState.BASIC;
				stringValue += data[currentIndex];
				currentIndex++;
				// escaping /
			} else if(data[currentIndex] == '\\' && escapeSequence) {
				escapeSequence = false;
				stringValue += data[currentIndex];
				currentIndex++;
			} else {
				stringValue += data[currentIndex];
				currentIndex++;
			}
		} 
		// tag occurred
		if(currentIndex+1 <= data.length-1 && data[currentIndex] == '{' &&
				data[currentIndex+1] == '$' && lexerState == LexerSmartState.TAG) {
			// if tag open occurred go to tag state
			//setState(LexerState.TAG);
			token = new TokenSmart(TokenSmartType.TAG_OPEN, "{$");
			currentIndex += 2;
			System.out.println("{$");
			return token;
		} 
		// return tag name token
		if(lexerState == LexerSmartState.TAG && !tagNameAdded) {
			String tagName = addTagName();
			System.out.println("tag name: " + tagName);
			tagNameAdded = true;
			token = new TokenSmart(TokenSmartType.TAG_NAME, tagName);
			System.out.println(tagName);
			return token;
		}
		
		// add tag elements, only if tag name is not end
		// tag end doesn't have elements
		if(lexerState == LexerSmartState.TAG && tagNameAdded && !tagElementsAdded &&
				!"end".equalsIgnoreCase(getToken().getValue().toString())) {
			String tagElements = "";
			while(currentIndex+1 < data.length) {
				// if tag occurred, checks that before tag is no \
				if(currentIndex-1 >= 0 && !escapeSequence &&
						data[currentIndex] == '$' && data[currentIndex+1] == '}' ) {
					break;
				}
				tagElements += data[currentIndex];
				currentIndex++;
			}
			// no more elements, but tag didn't close
			if(currentIndex == data.length-1) {
				throw new LexerSmartException("Tag didn''t close.");
			}
			token = new TokenSmart(TokenSmartType.TAG_ELEMENT, tagElements);
			tagElementsAdded = true;
			System.out.println(tagElements);
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
			System.out.println("$}");
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
		if(state == null) {
			throw new NullPointerException("Lexer state can't be null.");
		}
		lexerState = state;
	}
}
