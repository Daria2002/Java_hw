package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception that occurs when exception happens in nextToken method.
 * 
 * @author Daria Matkovic
 *
 */
public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	 
	/**
	 * Default constructor.
	 */
	public LexerException() {
		
	}
	
	/**
	 * Constructor that gets error message.
	 * 
	 * @param message message to be printed when error happens.
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor that gets description of exception.
	 * 
	 * @param cause cause is simple description of exception.
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor that gets error message and cause of exception.
	 * 
	 * @param message error message that prints when error happens.
	 * @param cause cause of exception
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
