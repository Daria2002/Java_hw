package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that occurs when exception happens in nextToken method.
 * 
 * @author Daria Matkovic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	 
	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {
		
	}
	
	/**
	 * Constructor that gets error message.
	 * 
	 * @param message message to be printed when error happens.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor that gets description of exception.
	 * 
	 * @param cause cause is simple description of exception.
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor that gets error message and cause of exception.
	 * 
	 * @param message error message that prints when error happens.
	 * @param cause cause of exception
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
