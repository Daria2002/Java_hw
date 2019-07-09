package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This class represents listener for single document
 * @author Daria Matković
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Method for updating document's modify status
	 * @param model model whose status needs to be updated
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Method for updating document's file path
	 * @param model single document model whose file path needs to be updated
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
