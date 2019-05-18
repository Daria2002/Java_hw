package hr.fer.zemris.java.hw11.jnotepadpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentListener {

	List<SingleDocumentModel> col = new ArrayList<SingleDocumentModel>();
	SingleDocumentModel currentSingleDocumentModel;
	private List<MultipleDocumentListener> listenerList;
	
	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		currentSingleDocumentModel = currentModel;
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
		col.add(model);
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
		col.remove(model);
	}

	private void loadDocument() {
		
	}
	
}
