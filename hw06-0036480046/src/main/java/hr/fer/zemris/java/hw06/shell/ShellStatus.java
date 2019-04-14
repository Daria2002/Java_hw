package hr.fer.zemris.java.hw06.shell;

/**
 * Enum that describes shell status, depending on given command.
 * @author Daria Matković
 *
 */
public enum ShellStatus {
	/** Continue means that shell needs to continue command executing **/
	CONTINUE,
	/** Terminate means that shell needs to print error message and stop executing **/
	TERMINATE
}
