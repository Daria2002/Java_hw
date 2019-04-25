package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception that occurs when exception happens in nextToken method.
 * 
 * @author Daria Matkovic
 *
 */
public class LexerSmartException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	 
	/**
	 * Default constructor.
	 */
	public LexerSmartException() {
		
	}
	
	/**
	 * Constructor that gets error message.
	 * 
	 * @param message message to be printed when error happens.
	 */
	public LexerSmartException(String message) {
		super(message);
	}
	
	/**
	 * Constructor that gets description of exception.
	 * 
	 * @param cause cause is simple description of exception.
	 */
	public LexerSmartException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor that gets error message and cause of exception.
	 * 
	 * @param message error message that prints when error happens.
	 * @param cause cause of exception
	 */
	public LexerSmartException(String message, Throwable cause) {
		super(message, cause);
	}
}
