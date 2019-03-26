package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.hw03.prob1.LexerException;

public class SmartScriptParser {
	
	private String documentBody;
	
	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
	}
	
	public DocumentNode getDocumentNode() {
		DocumentNode documentNode = new DocumentNode();
		LexerSmart lexer = new LexerSmart(documentBody);
		TokenSmart token;
		token = lexer.nextToken();
		
		// while token EOF don't occur call nextToken method
		while(token.getType() != TokenSmartType.EOF) {
			// take token
			token = lexer.nextToken();
			// if token contains values to add in node
			/*if(addNode(token)) {
				// add in node
			}*/
		}
		
		return documentNode;
	}
}
