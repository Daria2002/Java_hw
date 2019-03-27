package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.Tester;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
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
	private DocumentNode documentNode;
	private ObjectStack stack;
	
	private void makeDocumentNode() {
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
					
					String tagName = lexer.getToken().getValue().toString();
					
					// throw exception if tag name is unknown
					if(!("end".equalsIgnoreCase(tagName) || "for".equalsIgnoreCase(tagName) ||
						"=".equalsIgnoreCase(tagName))) {
						throw new SmartScriptParserException("Wrong tag name");
					}
					
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
						//documentNode.addChildNode(forLoopNode);
						//forLoop = true;
						stack.push(forLoopNode);
						
					} else if("=".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
					// if tag name was =
						// get arguments in = tag
						token = lexer.nextToken();
						EchoNode echoNode = getEchoNode(token.getValue());
						/*
						// if tag = was in for loop add echoNode as child of forLoopNode
						if(forLoop) {
							documentNode.getChild(documentNode.numberOfChildren()-1).addChildNode(echoNode);;
						} else if(!forLoop) {
							// if tag = occurred in outside of for loop node add echoNode as documentNode's child
							documentNode.addChildNode(echoNode);
						}
						*/
						Node parent = (Node)stack.peek();
						parent.addChildNode(echoNode);
					} else if("end".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
					// if tag name was end 
						//forLoop = false;
						Node child = (Node)stack.pop();
						Node parent = (Node)stack.peek();
						parent.addChildNode(child);
					}
					
				// this is used for:
				// a)reading text and adding text node on documentNode or forLoopNode
				// b)for reading end tag and changing forLoop flag, so it doesn't store
				// nodes like forLoopNode's children any more 
				} else if(lexer.getToken() != null && lexer.getToken().getType() == TokenSmartType.TEXT) {
					// if token type is text, make text node
					TextNode textNode = new TextNode(lexer.getToken().getValue().toString());
					//add textNode to documentNode if forLoop is not opened
					Node parent = (Node)stack.peek();
					parent.addChildNode(textNode);
					/*
					if(forLoop) {
						documentNode.getChild(documentNode.numberOfChildren()-1).addChildNode(textNode);;
					} else {
						documentNode.addChildNode(textNode);
					}
					*/
				}
				token = lexer.nextToken();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new LexerSmartException("Error message");
		}
	}
	
	public SmartScriptParser(String documentBody) {
		this.documentBody = documentBody;
		documentNode = new DocumentNode();
		stack = new ObjectStack();
		stack.push(documentNode);
		makeDocumentNode();
		
		Node node = (Node)stack.peek();
		if(!(node instanceof DocumentNode)) {
			throw new SmartScriptParserException("Expression is incompleted.");
		}
		documentNode = (DocumentNode)stack.pop();
	}
	
	private Element[] makeElementsForEchoNode(Object value) {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		char[] valueArray = value.toString().toCharArray(); 
		// flag for making function element
		boolean makeFunction = false;
		// flag for making string element
		boolean makeString = false;
		// flag for making number (int or double)
		boolean makeNumber = false;
		// flag for making variable
		boolean makeVariable = false;
		String buildValue = "";
		
		for(int i = 0; i < valueArray.length; i++) {
			
			// if prefix @ than set makeFunction flag
			if(valueArray[i] == '@') {
				makeFunction = true;
				continue;
				
			} else if(valueArray[i] == '*' || valueArray[i] == '+' ||
					valueArray[i] == '-' || valueArray[i] == '/' || valueArray[0] == '^') {
				buildValue += valueArray[i];
				ElementOperator operator = new ElementOperator(buildValue);
				elements.add(operator);
				buildValue = "";
				continue;
				
			// if next element is function
			} else if(makeFunction && valueArray[i] != ' ' && valueArray[i] != '"') {
				// if value number, letter or underscore
				if(Character.isAlphabetic(valueArray[i]) || Character.isDigit(valueArray[i]) 
						|| valueArray[i] == '_') {
					buildValue += valueArray[i];
				} else {
					// value is not number, letter or underscore
					throw new SmartScriptParserException("Invalid function name");
				}
				
				// if building function is turned on and ' ' or '\"'  sign occurs
			} else if(makeFunction && !(valueArray[i] != ' ' && valueArray[i] != '"')) {
				// check that function name is not empty 
				if(buildValue == "") {
					throw new SmartScriptParserException("Invalid expression.");
				}
				ElementFunction function = new ElementFunction(buildValue);
				elements.add(function);
				buildValue = "";
				makeFunction = false;
				
				// if next element needs to be string set string flag
				if(valueArray[i] == '"') {
					makeString = true;
				}
			// if building string
			} else if(makeString) {
				// save value and stop building string
				if(valueArray[i] == '"') {
					makeString = false;
					ElementString elementString = new ElementString(buildValue);
					elements.add(elementString);
					buildValue = "";
				} else {
					buildValue += valueArray[i];
				}
			
				// if element is not function, then check if element is variable or number(int, double)
			} else {
				// if buildValue starts to build
				if(buildValue == ""  && valueArray[i] != ' ' && valueArray[i] != '"') {
					buildValue += valueArray[i];
					// if next element is number
					if(Character.isDigit(valueArray[i]) || valueArray[i] == '-') {
						makeNumber = true;
						//if next element is word
					} else if(Character.isAlphabetic(valueArray[i])) {
						makeVariable = true;
					} else {
						throw new SmartScriptParserException();
					}

					// continue building value
				} else if(valueArray[i] != ' ' && valueArray[i] != '"') {
					// is letter occurred stop building number
					if(makeNumber && Character.isAlphabetic(valueArray[i])) {
						// check if number is int or double 
						elements.add(rightNumber(buildValue));
						
						makeNumber = false;
						buildValue = "";
						buildValue += valueArray[i];
						
						//throw exception if ',' occurs in number
					} else if(makeNumber && valueArray[i] == ',') {
						throw new SmartScriptParserException();
						
					} else {
						buildValue += valueArray[i];
					}
					// stop building variable
				} else if((valueArray[i] == ' ' || valueArray[i] == '"') && 
						makeVariable) {
					makeVariable = false;
					ElementVariable variableElement = new ElementVariable(buildValue);
					elements.add(variableElement);
					buildValue = "";
				} else if (!(makeFunction && makeNumber && makeString && makeVariable) &&
						valueArray[i] == '"') {
					makeString = true;
				}
			}
		}
		// if buildValue is not empty
		if(buildValue != "") {
			if(makeString) {
				ElementString stringElement = new ElementString(buildValue);
				elements.add(stringElement);
			} else if(makeNumber) {
				elements.add(rightNumber(buildValue));
			} else if(makeFunction) {
				ElementFunction functionElement = new ElementFunction(buildValue);
				elements.add(functionElement);
			} else if(makeVariable) {
				ElementVariable variableElement = new ElementVariable(buildValue);
				elements.add(variableElement);
			} else if(buildValue == "*" || buildValue == "+" ||
					buildValue == "-" || buildValue == "/" || buildValue == "^") {
				ElementOperator operator = new ElementOperator(buildValue);
				elements.add(operator);
			}
		}
		Element[] elementsArray = new Element[elements.size()];
		
		for(int i = 0; i < elements.size(); i++) {
			elementsArray[i] = (Element)elements.get(i);
		}
		
		return elementsArray;
	}
	
	private Element rightNumber(String buildValue) {
		try {
			Integer intValue = Integer.parseInt(buildValue);
			return new ElementConstantInteger(intValue);
		} catch (Exception e) {
			// if exception occurred parse to double
			try {
				Double doubleValue = Double.parseDouble(buildValue);
				return new ElementConstantDouble(doubleValue);
			} catch (Exception e2) {
				throw new SmartScriptParserException("Invalid expression");
			}
		}
	}
	
	private EchoNode getEchoNode(Object value) {
		Element[] elements = makeElementsForEchoNode(value);
		EchoNode echoNode = new EchoNode(elements);
		
		return echoNode;
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
			if(charArray[i] == '"' || Character.isWhitespace(charArray[i])) {
				// if something in build value and than whitespace occurs, that
				// means that argument need to be saved
				if(!buildValue.isEmpty() && Character.isWhitespace(charArray[i])) {
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
					buildValue = "";
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
		
		if(arrayCounter-1 < 2 || arrayCounter-1 > 3) {
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
		return documentNode;
	}
}
