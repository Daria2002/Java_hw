package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * This interface represents multiple document model that extends Iterable<SngleDocumentModel>
 * @author Daria Matković
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	/** 
	 * This method creates new document
	 * @return new created document
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * This method gets current document
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads document at given path
	 * @param path file path
	 * @return document at given path
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves given document at given path
	 * @param model document to save
	 * @param newPath path where given document need to be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes given document
	 * @param model document to close
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds listener
	 * @param l listener to add 
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes listener
	 * @param l listener to remove
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Gets number of documents
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**
	 * Gets document at given index
	 * @param index document's index
	 * @return document at given index
	 */
	SingleDocumentModel getDocument(int index);
}
