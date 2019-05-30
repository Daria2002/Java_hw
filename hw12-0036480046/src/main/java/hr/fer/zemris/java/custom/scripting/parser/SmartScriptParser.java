package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmart;
import hr.fer.zemris.java.custom.scripting.lexer.LexerSmartState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmart;
import hr.fer.zemris.java.custom.scripting.lexer.TokenSmartType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Represents smart parser for given script. There are
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
	/** lexer **/
	LexerSmart lexer;
	
	/**
	 * Parser+s mode
	 * @author Daria Matković
	 *
	 */
	enum ParserMode {
		FOR_LOOP_TAG,
		ECHO_TAG, 
		END_TAG, 
		TEXT
	}
	
	/**
	 * This method makes document node for given document body
	 */
	private void buildDocumentModel() {
		
		lexer = new LexerSmart(documentBody);
		TokenSmart token = lexer.nextToken();
		ParserMode parserMode = ParserMode.TEXT;
		
		while(token.getType() != TokenSmartType.EOF) {

			// next 4 if/else sets lexer state
			if(lexer.getToken().getType() == TokenSmartType.TAG_OPEN && 
					lexer.getLexerState() == LexerSmartState.BASIC) {
				
				lexer.setState(LexerSmartState.TAG);
				
			} else if(lexer.getToken().getType() == TokenSmartType.TAG_OPEN && 
					lexer.getLexerState() != LexerSmartState.BASIC) {
				
				throw new IllegalArgumentException("Tag open occured before last "
						+ "tag sequence didn't close.");
				
			} else if(lexer.getToken().getType() == TokenSmartType.TAG_CLOSE && 
					lexer.getLexerState() == LexerSmartState.TAG) {
				lexer.setState(LexerSmartState.BASIC);
				
			} else if(lexer.getToken().getType() == TokenSmartType.TAG_CLOSE &&
					lexer.getLexerState() != LexerSmartState.TAG) {
				
				throw new IllegalArgumentException("There is no tag open element, "
						+ "but tag close occured");
				
			}
			
			// if token type is tag open, get tag name, and set parser mode
			if(token.getType() == TokenSmartType.TAG_OPEN) {
				token = lexer.nextToken();
				
				if(token.getType() != TokenSmartType.TAG_NAME) {
					throw new IllegalArgumentException("After tag open token type tag name "
							+ "should occur.");
				}
				
				parserMode = setParserMode(token.getValue().toString());
				
				switch (parserMode) {
				case END_TAG:
					endTag();
					
					if(lexer.nextToken().getType() != TokenSmartType.TAG_CLOSE) {
						throw new IllegalArgumentException("In tags with END arguments "
								+ "should not appear.");
					}
					
					continue;
					
				case FOR_LOOP_TAG:
					// if there are not four arguments, that means that current token
					// is token after for loop tag, so no need for getting argument
					// once more
					if(!forTag()) {
						continue;
					}
					token = lexer.nextToken();
					if(token.getType() == TokenSmartType.TAG_ELEMENT) {
						throw new SmartScriptParserException("For loop can have only "
								+ "3 or 4 arguments"); 
					}
					continue;

				case ECHO_TAG:
					echoTag();
					continue;
					
				default:
					throw new IllegalArgumentException("Exception occured");
				}
				
			} else if(token.getType() == TokenSmartType.TEXT) {
				TextNode textNode = new TextNode(removeQuotes(token.getValue().toString()));
				Node parent = (Node)stack.peek();
				parent.addChildNode(textNode);
			}
			
			
			token = lexer.nextToken();
			
		}
		
	}

	/**
	 * Formats echo node
	 */
	private void echoTag() {
		List<Element> elementList = new ArrayList<Element>();
		
		while(lexer.getToken().getType() != TokenSmartType.EOF && lexer.nextToken().getType() != TokenSmartType.TAG_CLOSE) {
			
			String tokenValue = removeQuotes(lexer.getToken().getValue().toString());
			String tokenValueWithQuotes = lexer.getToken().getValue().toString();
			
			if(tokenValue.charAt(0) == '@') {
				elementList.add(new ElementFunction(tokenValue.substring(1)));
			
			} else if(isOperator(tokenValue)) {
				elementList.add(new ElementOperator(tokenValue));
				
			} else {
				elementList.add(getValue(tokenValueWithQuotes));
			}
		}

		Element[] elArray = new Element[elementList.size()];
		for(int i = 0; i < elementList.size(); i++) {
			elArray[i] = elementList.get(i);
		}
		
		EchoNode echoNode = new EchoNode(elArray);
		Node parent = (Node)stack.peek();
		parent.addChildNode(echoNode);
	}

	/**
	 * This method removes quotes if there are quotes, otherwise returns given string
	 * @param tokenValue string value to check
	 * @return string without quotes
	 */
	private String removeQuotes(String tokenValue) {
		if(tokenValue.indexOf("\"") == 0 && tokenValue.charAt(tokenValue.length()-1) == '"') {
			return tokenValue.substring(1, tokenValue.length()-1);
		}
		
		return tokenValue;
	}

	/**
	 * Checks if given value is operator
	 * @param str value to check
	 * @return true if value is operator, otherwise false
	 */
	private boolean isOperator(String str) {
		if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")
				|| str.equals("^")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if given string is number
	 * @param str value to check
	 * @return true if given value is number, otherwise false
	 */
	private boolean isNumeric(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
			
		} catch(NumberFormatException e){  
			return false;  
		}  
	}
	
	/**
	 * This method format for loop node
	 * @return returns true if for loop node has four elements and false if it has three elements
	 */
	private boolean forTag() {
		if(isNumeric(lexer.nextToken().getValue().toString())) {
			throw new SmartScriptParserException("Variable name can't be number");
		}
		
		ElementVariable variable = new ElementVariable(
				removeQuotes(lexer.getToken().getValue().toString()));
		Element startExpression = getIntegerDoubleOrString(
				removeQuotes(lexer.nextToken().getValue().toString()));
		Element endExpression =  getIntegerDoubleOrString(
				removeQuotes(lexer.nextToken().getValue().toString()));
		Element stepExpression = null;
		
		boolean fourArgs = false;
		
		if(lexer.nextToken().getType() == TokenSmartType.TAG_ELEMENT) {
			stepExpression = getIntegerDoubleOrString(
					removeQuotes(lexer.getToken().getValue().toString()));
			fourArgs = true;
		}
		
		stack.push(new ForLoopNode(variable, startExpression, endExpression, stepExpression));
		return fourArgs;
	}

	/**
	 *	Removes top value from stack, and adds that value like child to next top value
	 */
	private void endTag() {
		Node child = (Node)stack.pop();
		Node parent = (Node)stack.peek();
		parent.addChildNode(child);
	}

	/**
	 * Sets parser mode depending on given value
	 * @param value value
	 * @return parser mode
	 */
	private ParserMode setParserMode(String value) {
		switch (value.toUpperCase()) {
			case "END":
				return ParserMode.END_TAG;
				
			case "FOR":
				return ParserMode.FOR_LOOP_TAG;
	
			case "=":
				return ParserMode.ECHO_TAG;
				
			default:
				throw new IllegalArgumentException("Tag name can be =, END, FOR");
		}
	}

	/**
	 * Checks type of value (string, double, int) and convert it to right element
	 * @param value value to check
	 * @return Element object from given value
	 */
	private Element getValue(String value) {
		// check if value if integer
		try {
			return new ElementConstantInteger(Integer.parseInt(value));
			
		} catch (Exception e) {
			// exception occurs if given value is not int
			// check if given value is double
			try {
				return new ElementConstantDouble(Double.parseDouble(value));
				
			} catch (Exception e2) {
				// exception occurs if given value is not int and double
				if(value.indexOf('"') == 0 && value.substring(1).indexOf('"') == value.length()-2) {
					return new ElementString(removeQuotes(value));
				}
				
				return new ElementVariable(removeQuotes(value));
			}
		}
	}
	
	/**
	 * Checks type of value (string, double, int) and convert it to right element
	 * @param value value to check
	 * @return Element object from given value
	 */
	private Element getIntegerDoubleOrString(String value) {
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
			
			//makeDocumentNode();
			
			buildDocumentModel();
			
			Node node = (Node)stack.peek();
			if(!(node instanceof DocumentNode)) {
				throw new SmartScriptParserException("Expression is incompleted.");
			}
			
			documentNode = (DocumentNode)stack.pop();
			
		} catch (Exception e) {
			throw new SmartScriptParserException("Invalid data.");
		}
	}
}
