package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path filePath;
	private JTextArea textContent;
	private boolean isModified;
	private List<SingleDocumentListener> listenerList;
	public JNotepadPP editor = new JNotepadPP();

	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		this.textContent = new JTextArea(textContent);
		listenerList = new ArrayList<SingleDocumentListener>();
		this.getTextComponent().setText(textContent);
		
		this.textContent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setFlagAndNotifyListeners();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setFlagAndNotifyListeners();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setFlagAndNotifyListeners();
			}
		});
	}

	private void setFlagAndNotifyListeners() {
		for(SingleDocumentListener l : listenerList) {
			l.documentModifyStatusUpdated(this);
		}
		
		isModified = true;
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textContent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		filePath = path;
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		isModified = modified;
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listenerList.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listenerList.remove(l);
	}
}
