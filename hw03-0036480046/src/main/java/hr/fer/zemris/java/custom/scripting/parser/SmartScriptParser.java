package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Represents smart parser
 * @author Daria Matković
 *
 */
public class SmartScriptParser {
	/** Document text **/
	private String documentBody;
	/** Node from document **/
	private DocumentNode documentNode;
	/** Stack for nodes, so nesting is possible **/
	private ObjectStack stack;
	
	/**
	 * Sets document node
	 */
	private void makeDocumentNode() {
		LexerSmart lexer = new LexerSmart(documentBody);
		TokenSmart token;

		token = lexer.nextToken();
		// while token EOF don't occur call nextToken method
		while(token.getType() != TokenSmartType.EOF) {
			
			// take elements after tag
			// this is used for reading loop arguments
			if(lexer.getToken().getType() == TokenSmartType.TAG_NAME) {
				
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
					
					stack.push(forLoopNode);
					
				} else if("=".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
				// if tag name was =
					// get arguments in = tag
					token = lexer.nextToken();
					EchoNode echoNode = getEchoNode(token.getValue());
					Node parent = (Node)stack.peek();
					parent.addChildNode(echoNode);
					
				} else if("end".equalsIgnoreCase(lexer.getToken().getValue().toString())) {
				// if tag name was end 
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
			}
			
			token = lexer.nextToken();
		}
	}
	
	/**
	 * Default constructor, delegate to method that creates document node
	 * @param documentBody document text
	 */
	public SmartScriptParser(String documentBody) {
		
		try {
			Objects.requireNonNull(documentBody, "Document body is null");
			
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
			
		} catch (Exception e) {
			throw new SmartScriptParserException("Invalid data.");
		}
	}
	
	/**
	 * Separates echo node elements
	 * @param value value with all arguments together
	 * @return separated elements
	 */
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
		boolean escapeSequence = false;
		
		for(int i = 0; i < valueArray.length; i++) {
			
			// if prefix @ than set makeFunction flag
			if(valueArray[i] == '@') {
				if(!Character.isLetter(valueArray[i+1])) {
					throw new IllegalArgumentException("Function must start with letter.");
				}
				makeFunction = true;
				continue;
				
			} else if(valueArray[i] == '*' || valueArray[i] == '+' ||
					valueArray[i] == '-' || valueArray[i] == '/' || valueArray[0] == '^') {
				buildValue += valueArray[i];
				elements.add(new ElementOperator(buildValue));
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
				char currChar = valueArray[i];
				
				if (escapeSequence && ( currChar == '\\' || currChar == '"' ||
						currChar == '\n' || currChar == '\t' || currChar == '\r')) {
					buildValue += '\\' + currChar;
					escapeSequence = false;
					continue;
				}

				if(valueArray[i] == '\\' && !escapeSequence) {
					escapeSequence = true;
					continue;
				}
				
				// save value and stop building string
				if(valueArray[i] == '"' && !escapeSequence) {
					makeString = false;
					ElementString elementString = new ElementString(
							buildValue.replace("\\\\", "\\").replace("\\n", "\n")
								.replace("\\r", "\r").replace("\\t", "\t")
								.replace("\\\"", "\""));
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
						buildValue = String.valueOf(valueArray[i]);
						
						//throw exception if ',' occurs in number
					} else if(makeNumber && valueArray[i] == ',') {
						throw new SmartScriptParserException();
						
					} else {
						buildValue += valueArray[i];
					}
					// stop building variable
				} else if((valueArray[i] == ' ' || valueArray[i] == '"') && makeVariable) {
					makeVariable = false;
					elements.add(new ElementVariable(buildValue));
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
				elements.add(new ElementString(buildValue));
				
			} else if(makeNumber) {
				elements.add(rightNumber(buildValue));
				
			} else if(makeFunction) {
				elements.add(new ElementFunction(buildValue));
				
			} else if(makeVariable) {
				elements.add(new ElementVariable(buildValue));
				
			} else if(buildValue == "*" || buildValue == "+" ||
					buildValue == "-" || buildValue == "/" || buildValue == "^") {
				elements.add(new ElementOperator(buildValue));
			}
		}
		Element[] elementsArray = new Element[elements.size()];
		
		for(int i = 0; i < elements.size(); i++) {
			elementsArray[i] = (Element)elements.get(i);
		}
		
		return elementsArray;
	}
	
	/**
	 * Returns right Element object that depends on buildValue
	 * @param buildValue given value
	 * @return Element from buildValue
	 */
	private Element rightNumber(String buildValue) {
		try {
			return new ElementConstantInteger(Integer.parseInt(buildValue));
			
		} catch (Exception e) {
			// if exception occurred parse to double
			try {
				return new ElementConstantDouble(Double.parseDouble(buildValue));
				
			} catch (Exception e2) {
				throw new SmartScriptParserException("Invalid expression");
			}
		}
	}
	
	/**
	 * Returns echo node
	 * @param value value to set in echo node
	 * @return echo node
	 */
	private EchoNode getEchoNode(Object value) {
		return new EchoNode(makeElementsForEchoNode(value));
	}
	
	/**
	 * Analyze arguments and separates them in array
	 * @param arguments from for loop node
	 * @return array of arguments in for loop 
	 */
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
					array[arrayCounter++] = buildValue.toString();
					buildValue = "";
					
				} else if(charArray[i] == '"' && buildValue == "" && !inQuotation) {
					// if " occurs and nothing is in buildValue that means that
					// inQuotation mode is on
					inQuotation = true;
					continue;
					
				} else if(charArray[i] == '"' && buildValue != "" && inQuotation) {
					// if " occurs and something is in buildValue that means that
					// inQuotation mode is off and value stops
					inQuotation = false;
					array[arrayCounter++] = buildValue;
					buildValue = "";
					
				} else if(charArray[i] == '"' && buildValue != "" && !inQuotation) {
					array[arrayCounter] = buildValue;
					buildValue = "";
					inQuotation = true;
					arrayCounter++;
				}
			} else {
				// build variable name, number, etc.
				buildValue += charArray[i];
			}
		}
		
		if(buildValue != "") {
			array[arrayCounter] = buildValue;
		}
		
		if(arrayCounter != 3 && arrayCounter != 4) {
			throw new SmartScriptParserException("For loop can have 3 or 4 args.");
		}
		return array;
	}
	
	/**
	 * Checks type of value (string, double, int) and convert it to righ element
	 * @param value value to check
	 * @return Element object from given value
	 */
	private Element initializeElement(String value) {
		// check if value if integer
		try {
			return new ElementConstantInteger(Integer.parseInt(value));
			
		} catch (Exception e) {
			// exception occurs if given value is not int
			// check if given value is double
			try {
				return new ElementConstantDouble(Double.parseDouble(value));
				
			} catch (Exception e2) {
				//exception occurs if given value is not int and double
				return new ElementString(value);
			}
		}
	}
	
	/**
	 * Returns document node
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
