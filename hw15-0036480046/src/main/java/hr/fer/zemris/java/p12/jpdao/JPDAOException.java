package hr.fer.zemris.java.p12.jpdao;

/**
 * DAO Exception
 * @author Daria Matković
 *
 */
public class JPDAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that doesn't do anything
	 */
	public JPDAOException() {
		
	}

	/**
	 * Constructor that initialize message, cause, flag for enabling or
	 * disabling suppression and flag for writable stack trace
	 * @param message message
	 * @param cause cause
	 * @param enableSuppression enable suppression
	 * @param writableStackTrace writeble stack trace
	 */
	public JPDAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor that gets message and cause
	 * @param message message
	 * @param cause cause
	 */
	public JPDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor that gets message and cause
 	 * @param message message
	 */
	public JPDAOException(String message) {
		super(message);
	}

	/**
	 * Constructor that gets cause
	 * @param cause
	 */
	public JPDAOException(Throwable cause) {
		super(cause);
	}
}