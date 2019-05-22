package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that represents single document model
 * @author Daria Matković
 *
 */
public interface SingleDocumentModel {
	/**
	 * Gets text component
	 * @return text component
	 */
	JTextArea getTextComponent();
	
	/**
	 * Gets file path
	 * @return file path
	 */
	Path getFilePath();
	
	/**
	 * Sets file path
	 * @param path file path
	 */
	void setFilePath(Path path);
	
	/**
	 * Gets modify status
	 * @return true if file is modified, otherwise false
	 */
	boolean isModified();
	
	/**
	 * Sets modify status
	 * @param modified new value of modified status
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds single document listener
	 * @param l listener to add 
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes single document listener
	 * @param l listener to remove
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
