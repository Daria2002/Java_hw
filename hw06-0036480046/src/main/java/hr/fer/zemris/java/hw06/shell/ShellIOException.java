package hr.fer.zemris.java.hw06.shell;

/**
 * Exception that happens when no communication is possible with the user.
 * This exception throws only if reading and writing using Environment.write(), 
 * Environment.writeln() or Environment.readLine() is not possible.
 * @author Daria Matković
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public ShellIOException() { 
		  
	}
	 
	/**
	 * Constructor that sets message.
	 * @param message message is error message.
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * Constructor that sets throwable object
	 * @param cause cause is throwable object
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor that sets throwable object and error message.
	 * @param message message is error message
	 * @param cause cause is throwable object
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
