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
public class Lexer {
	// given text
	private char[] data;
	// current token
	private Token token;
	// index of first unanalyzed sign
	private int currentIndex;
	private LexerState lexerState;
	private String text;
	private boolean tagNameAdded = false;
	private boolean tagElementsAdded = false;
	
	public static void main(String[] args) {
		String testString = ("This is sample text.\\n" + 
				"{$ FOR i 1 10 1 $}\\n" + 
				" This is {$= i $}-th time this message is generated.\\n" + 
				"{$END$}\\n" + 
				"{$FOR i 0 10 2 $}\\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \\\"0.000\\\" @decfmt $}\\n" + 
				"{$END$}");
		
		String filePath = args[0];
		String content = "";
		 
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Lexer lexer = new Lexer(content);
        System.out.println();
        
        for(int i = 0; i < 40; i++) {
            System.out.println("Next token is:");
    		lexer.nextToken();
        }
	}
	
	/**
	 * Constructor get text that need to be analyzed.
	 * 
	 * @param text text need to be analyzed.
	 */
	public Lexer(String text) {
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
		setState(LexerState.BASIC);
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
	public Token nextToken() {
		String stringValue = "";
		
		// work in basic mode
		while(currentIndex <= data.length-1 && lexerState == LexerState.BASIC) {
			// if tag occurs, break
			if(data[currentIndex] == '{' && data[currentIndex+1] == '$') {
				token = new Token(TokenType.TEXT, stringValue);
				setState(LexerState.TAG);
				System.out.println(stringValue);
				return token;
			// if end, return text
			} else if(currentIndex+1 > data.length-1) {
				token = new Token(TokenType.TEXT, stringValue);
				currentIndex++;
				System.out.println(stringValue);
				return token;
			}
			// if basic mode continue adding text
			stringValue += data[currentIndex];
			currentIndex++;
		} 
		// tag occurred
		if(currentIndex+1 <= data.length-1 && data[currentIndex] == '{' &&
				data[currentIndex+1] == '$' && lexerState == LexerState.TAG) {
			// if tag open occurred go to tag state
			//setState(LexerState.TAG);
			token = new Token(TokenType.TAG_OPEN, "{$");
			currentIndex += 2;
			System.out.println("{$");
			return token;
		} 
		// return tag name token
		if(lexerState == LexerState.TAG && !tagNameAdded) {
			String tagName = addTagName();
			System.out.println("tag name: "+tagName);
			// throw exception if tag name is unknown
			if(!("end".equalsIgnoreCase(tagName) || "for".equalsIgnoreCase(tagName) ||
				"=".equalsIgnoreCase(tagName))) {
				throw new LexerException("Wrong tag name");
			}
			tagNameAdded = true;
			token = new Token(TokenType.TAG_NAME, tagName);
			System.out.println(tagName);
			return token;
		}
		
		// add tag elements, only if tag name is not end
		// tag end doesn't have elements
		if(lexerState == LexerState.TAG && tagNameAdded && !tagElementsAdded &&
				!"end".equalsIgnoreCase(getToken().getValue().toString())) {
			String tagElements = "";
			while(currentIndex+1 < data.length && data[currentIndex] != '$' && data[currentIndex+1] != '}') {
				tagElements += data[currentIndex];
				currentIndex++;
			}
			// no more elements, but tag didn't close
			if(currentIndex == data.length-1) {
				throw new LexerException("Tag didn''t close.");
			}
			token = new Token(TokenType.TAG_ELEMENT, tagElements);
			tagElementsAdded = true;
			System.out.println(tagElements);
			return token;
		}
		
		// if tag elements and tag name added, add tag close
		// tag elements added when close tag occurred, so next element is tag close
		if(lexerState == LexerState.TAG && tagNameAdded && (tagElementsAdded
				|| "end".equalsIgnoreCase(getToken().getValue().toString()))) {
			token = new Token(TokenType.TAG_CLOSE, "$}");
			currentIndex += 2;
			setState(LexerState.BASIC);
			tagElementsAdded = false;
			tagNameAdded = false;
			System.out.println("$}");
			return token;
		}
		throw new LexerException("No more token in given document.");
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
