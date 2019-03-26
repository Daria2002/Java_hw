package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmartException;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.hw03.prob1.LexerException;

public class SmartScriptParser {
	
	private String documentBody;
	
	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
	}
	
	private Object buildValue() {
		
	}
	
	private Object[] getForLoopArguments(Object arguments) {		
		Object[] array = new Object[4];
		int arrayCounter = 0;
		char[] charArray = arguments.toString().trim().toCharArray();
		
		// array of arguments with whitespace, "", etc.
		for(int i = 0; i < charArray.length; i++) {
			// if there are more than 4 arguments
			if(arrayCounter > 3) {
				throw new SmartScriptParserException();
			}
			// skip whitespace and ""
			if(charArray[i] == ' ' || charArray[i] == '"') {
				continue;
			} else {
				// build variable name, number, etc.
				array[arrayCounter] = buildValue();
				
				
			}
			
		}
		
		
		return array;
	}
	
	public DocumentNode getDocumentNode() {
		DocumentNode documentNode = new DocumentNode();
		LexerSmart lexer = new LexerSmart(documentBody);
		TokenSmart token;
		token = lexer.nextToken();
		
		try {
			// while token EOF don't occur call nextToken method
			while(token.getType() != TokenSmartType.EOF) {
				
				// take elements after tag
				if(lexer.getToken() != null &&
						lexer.getToken().getType() == TokenSmartType.TAG_NAME) {
					
					// if tag name was for
					if("for".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
						// take elements
						token = lexer.nextToken();
						Object[] forLoopArguments = getForLoopArguments(token.getValue());
						// make forLoopNode
						ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression)
						
					} else if("=".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
					// if tag name was =
						
					} else if("end".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
					// if tag name was end 
						
					}
				}
				
				// take token
				token = lexer.nextToken();
			}
		} catch (Exception e) {
			throw new LexerSmartException("Error message");
		}
		
		
		
		return documentNode;
	}
}
