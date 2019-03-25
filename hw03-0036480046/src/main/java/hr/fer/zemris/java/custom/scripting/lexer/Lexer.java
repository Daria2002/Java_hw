package hr.fer.zemris.java.custom.scripting.lexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		lexer.nextToken();
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
		while(data[currentIndex] != '\r' && data[currentIndex] != '\t' && 
				data[currentIndex] != '\n' && data[currentIndex] != '\n') {
			tagName += data[currentIndex];
			currentIndex++;
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
			if(currentIndex+1 <= data.length-1 && data[currentIndex] != '{' &&
					data[currentIndex+1] != '$') {
				break;
			}
			// if basic mode continue adding text
			stringValue += data[currentIndex];
			currentIndex++;
		} 
		// if no more elements left while basic text was building
		if(currentIndex == data.length-1 && lexerState == LexerState.BASIC) {
			token = new Token(TokenType.TEXT, stringValue);
			currentIndex++;
			return token;
			
		} else if(currentIndex+1 <= data.length-1 && data[currentIndex] == '{' &&
				data[currentIndex+1] == '$' && lexerState == LexerState.BASIC) {
			// if tag open occurred go to tag state
			setState(LexerState.TAG);
			token = new Token(TokenType.TAG_OPEN, "{$");
			currentIndex += 2;
			return token;
		} 
		// return tag name token
		if(lexerState == LexerState.TAG && !tagNameAdded) {
			String tagName = addTagName();
			if(tagName != "" || tagName != "" || tagName != "") {
				throw new LexerException("Wrong tag name");
			}
			tagNameAdded = true;
			token = new Token(TokenType.TAG_NAME, tagName);
			return token;
		}
		
		// add tag elements
		if(lexerState == LexerState.TAG && tagNameAdded && !tagElementsAdded) {
			String tagElements = "";
			while(data[currentIndex] != '{' && data[currentIndex+1] != '$') {
				tagElements += data[currentIndex];
				currentIndex++;
			}
			token = new Token(TokenType.TAG_ELEMENT, tagElements);
		}
		
		// if tag elements and tag name added, add tag close
		if(lexerState == LexerState.TAG && tagNameAdded && !tagElementsAdded) {
			token = new Token(TokenType.TAG_CLOSE, "$}");
			currentIndex += 2;
			setState(LexerState.BASIC);
			tagElementsAdded = false;
			tagNameAdded = false;
			return token;
		}
		return token;
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
