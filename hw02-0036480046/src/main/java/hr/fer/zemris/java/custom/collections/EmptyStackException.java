package hr.fer.zemris.java.custom.collections;

/**
 * This class implements RuntimeException used for object stack class.
 * @author Daria Matkovic
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public EmptyStackException() { 
		 
	}
	
	/**
	 * Constructor that sets message.
	 * @param message message is error message.
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Constructor that sets throwable object
	 * @param cause cause is throwable object
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor that sets throwable object and error message.
	 * @param message message is error message
	 * @param cause cause is throwable object
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
