package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Interface for generation name of new dir
 * @author Daria Matković
 *
 */
public interface NameBuilder {
	/**
	 * This method writes in given string builder name of new dir
	 * @param result source dir
	 * @param sb string builder with name of new dir
	 */
	void execute(FilterResult result, StringBuilder sb);
}
