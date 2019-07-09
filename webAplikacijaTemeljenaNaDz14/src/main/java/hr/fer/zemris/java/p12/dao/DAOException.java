package hr.fer.zemris.java.p12.dao;

/**
 * DAO Exception
 * @author Daria Matković
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that doesn't do anything
	 */
	public DAOException() {
		
	}

	/**
	 * Constructor that initialize message, cause, flag for enabling or
	 * disabling suppression and flag for writable stack trace
	 * @param message message
	 * @param cause cause
	 * @param enableSuppression enable suppression
	 * @param writableStackTrace writeble stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor that gets message and cause
	 * @param message message
	 * @param cause cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor that gets message and cause
 	 * @param message message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor that gets cause
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}