package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This interface represents listener for multiple document
 * @author Daria Matković
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Method for executing commands after current document changed
	 * @param previousModel previous model
	 * @param currentModel current model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);
	
	/**
	 * Method executed when document is added
	 * @param model added SingleDocumentModel
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Method executed when document is removed
	 * @param model removed SingleDocumentModel
	 */
	void documentRemoved(SingleDocumentModel model);
}
