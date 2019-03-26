package hr.fer.zemris.java.custom.scripting.parser;

import javax.xml.parsers.ParserConfigurationException;

import hr.fer.zemris.java.custom.collections.Tester;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmartException;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.prob1.LexerException;

public class SmartScriptParser {
	
	private String documentBody;
	DocumentNode documentNode;
	boolean forLoop = false;
	
	
	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
		documentNode = new DocumentNode();
	}
	
	private EchoNode getEchoNode(Object value) {
		return new EchoNode(null);
	}
	
	private Object[] getForLoopArguments(Object arguments) {		
		Object[] array = new Object[4];
		int arrayCounter = 0;
		char[] charArray = arguments.toString().trim().replace("\\s+", "").toCharArray();
		String buildValue = "";
		boolean inQuotation = false;
		
		// array of arguments with whitespace, "", etc.
		for(int i = 0; i < charArray.length; i++) {
			// if there are more than 4 arguments
			if(arrayCounter > 3) {
				throw new SmartScriptParserException("For loop can have 3 or 4 arguments");
			}
			
			// if - occurs
			if(charArray[i] == '-') {
				// if nothing in buildValue and after - comes number add - 
				if(i+1 <= charArray.length-1 && Character.isDigit(charArray[i+1]) && buildValue == "") {
					buildValue += charArray[i];
	
				} else if(i+1 <= charArray.length-1 && Character.isDigit(charArray[i+1]) && buildValue != "") {
				// if something in buildValue and after - is number,
				// add buildValue to array and initialize buildValue to start building new variable
					array[arrayCounter] = buildValue;
					arrayCounter++;
					buildValue = "";
					buildValue += charArray[i];
				}
				continue;
			}
			
			// skip whitespace and "", set next element if this occurs
			if(charArray[i] == ' ' || charArray[i] == '"') {
				// if something in build value and than whitespace occurs, that
				// means that argument need to be saved
				if(buildValue != "" && charArray[i] == ' ') {
					array[arrayCounter] = buildValue.toString();
					buildValue = "";
					arrayCounter++;
				} else if(charArray[i] == '"' && buildValue == "" && !inQuotation) {
					// if " occurs and nothing is in buildValue that means that
					// inQuotation mode is on
					inQuotation = true;
				} else if(charArray[i] == '"' && buildValue != "" && inQuotation) {
					// if " occurs and something is in buildValue that means that
					// inQuotation mode is off and value stops
					inQuotation = false;
					array[arrayCounter] = buildValue.toString();
					arrayCounter++;
				} else if(charArray[i] == '"' && buildValue != "" && !inQuotation) {
					// if " occurs and something is in buildValue and inQuotation in off, 
					// it means error occurred because " can't be part of variable name
					throw new SmartScriptParserException("\" can't be part of variable name.");
				}
			} else {
				// build variable name, number, etc.
				buildValue += charArray[i];
			}
		}
		
		if(buildValue != "") {
			array[arrayCounter] = buildValue;
		}
		
		if(arrayCounter < 2 || arrayCounter > 3) {
			throw new SmartScriptParserException("For loop can have 3 or 4 args.");
		}
		return array;
	}
	
	
	private Element initializeElement(String value) {
		// check if value if integer
		try {
			Integer intValue = Integer.parseInt(value);
			ElementConstantInteger intElement = new ElementConstantInteger(intValue);
			return intElement;
		} catch (Exception e) {
			// exception occurs if given value is not int
			// check if given value is double
			try {
				Double doubleValue = Double.parseDouble(value);
				ElementConstantDouble doubleElement = new ElementConstantDouble(doubleValue);
				return doubleElement;
			} catch (Exception e2) {
				//exception occurs if given value is not int and double
				ElementString stringValue = new ElementString(value);
				return stringValue;
			}
		}
	}
	
	public DocumentNode getDocumentNode() {
		LexerSmart lexer = new LexerSmart(documentBody);
		TokenSmart token;
		
		try {
			token = lexer.nextToken();
			// while token EOF don't occur call nextToken method
			while(token.getType() != TokenSmartType.EOF) {
				
				// take elements after tag
				// this is used for reading loop arguments
				if(lexer.getToken() != null &&
						lexer.getToken().getType() == TokenSmartType.TAG_NAME) {
					
					// if tag name was for
					if("for".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
						// take elements
						token = lexer.nextToken();
						Object[] forLoopArguments = getForLoopArguments(token.getValue());
						
						// make forLoopNode
						ElementVariable variable = new ElementVariable(forLoopArguments[0].toString());
						
						Element startExpression = initializeElement(forLoopArguments[1].toString());
						Element endExpression =  initializeElement(forLoopArguments[2].toString());
						Element stepExpression = null;
						if(forLoopArguments.length == 4) {
							stepExpression =  initializeElement(forLoopArguments[3].toString());;
						}
						
						ForLoopNode forLoopNode = new ForLoopNode(variable, 
								startExpression, endExpression, stepExpression);
						
						// add forLoopNode to parent documentNode, forLoop node
						// is now open and till end doesn't occur every node is 
						// child of for loop
						documentNode.addChildNode(forLoopNode);
						forLoop = true;
						
					} else if("=".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
					// if tag name was =
						// get arguments in = tag
						token = lexer.nextToken();
						EchoNode echoNode = getEchoNode(token.getValue());
						
						// if tag = was in for loop add echoNode as child of forLoopNode
						if(forLoop) {
							documentNode.getChild(documentNode.numberOfChildren()-1).addChildNode(echoNode);;
						} else if(!forLoop) {
							// if tag = occurred in outside of for loop node add echoNode as documentNode's child
							documentNode.addChildNode(echoNode);
						}
						
					} else if("end".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
					// if tag name was end 
						forLoop = false;
					}
					
				// this is used for:
				// a)reading text and adding text node on documentNode or forLoopNode
				// b)for reading end tag and changing forLoop flag, so it doesn't store
				// nodes like forLoopNode's children any more 
				} else if(lexer.getToken() != null && lexer.getToken().getType() == TokenSmartType.TEXT) {
					// if token type is text, make text node
					TextNode textNode = new TextNode(lexer.getToken().getValue().toString());
					//add textNode to documentNode if forLoop is not opened
					if(forLoop) {
						documentNode.getChild(documentNode.numberOfChildren()-1).addChildNode(textNode);;
					} else {
						documentNode.addChildNode(textNode);
					}
				}
				lexer.nextToken();
			}
		} catch (Exception e) {
			throw new LexerSmartException("Error message");
		}
		
		return documentNode;
	}
}
