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
	LexerState lexerState;
	String text;
	
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
		// if tag open occurred
		if(currentIndex+1 <= data.length-1 && data[currentIndex] == '{' &&
				data[currentIndex+1] == '$') {
			// go to tag state
			lexerState = LexerState.TAG;
			String tagName = "";
			boolean tagNameAdded = false;
			
			// while close tag doesn't occur analyze elements in tag
			while(currentIndex <= data.length-1 && data[currentIndex] != '$'
					&& lexerState == LexerState.TAG) {
				
				// skip whitespace in tag state
				if(data[currentIndex] == '\n' || data[currentIndex] == '\r' ||
						data[currentIndex] == '\t' || data[currentIndex] == ' ') {
					currentIndex++;
					continue;
					
				// if tagName is not returned, build tagName
				} else if(!tagNameAdded) {
					
					// build tagName while whitespace doesn't occur
					while(data[currentIndex] == '\n' || data[currentIndex] == '\r' ||
						data[currentIndex] == '\t' || data[currentIndex] == ' ') {
						tagName += data[currentIndex];
						currentIndex++;
					}
					// no more elements in data, but tag state didn't close
					if(currentIndex <= data.length-1) {
						throw new LexerException("TAG didn't close.");
					}
					token = new Token(TokenType.TAG_NAME, tagName);
					
				} else {
					// tagName is added and other elements can be added
					
				}
				
				
			}
		} else if(currentIndex == data.length-1 && lexerState == LexerState.BASIC) {
			// if no more elements left
			token = new Token(TokenType.TEXT, stringValue);
		} 
		System.out.print("\n\n" + stringValue);
		return new Token(TokenType.EOF, null);
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
