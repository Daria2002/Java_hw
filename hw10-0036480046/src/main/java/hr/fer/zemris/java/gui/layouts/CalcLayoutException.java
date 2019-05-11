package hr.fer.zemris.java.gui.layouts;

/**
 * Exception that thrown in RCPosition class when error occurs.
 * 
 * @author Daria Matkovic
 *
 */
public class CalcLayoutException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	 
	/**
	 * Default constructor.
	 */
	public CalcLayoutException() {
		
	}
	
	/**
	 * Constructor that gets error message.
	 * 
	 * @param message message to be printed when error happens.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * Constructor that gets description of exception.
	 * 
	 * @param cause cause is simple description of exception.
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor that gets error message and cause of exception.
	 * 
	 * @param message error message that prints when error happens.
	 * @param cause cause of exception
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
	
}