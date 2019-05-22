package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * This class represents DefaultSingleDocumentModel that implements SingleDocumentModel
 * @author Daria Matković
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/** file path **/
	private Path filePath;
	/** text content **/
	private JTextArea textContent;
	/** flag for modification status **/
	private boolean isModified;
	/** listeners list **/
	private List<SingleDocumentListener> listenerList;

	/**
	 * This class represents constructor for DefaultSingleDocumentModel
	 * @param filePath file path
	 * @param textContent text content
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		this.textContent = new JTextArea(textContent);
		this.textContent.setCaret(new HighlightCaret());
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
	
	/**
	 * This method sets flag and notifies listeners
	 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		return true;
	}
	
	/**
	 * This class represents class has special properties set for caret
	 * @author Daria Matković
	 *
	 */
	class HighlightCaret extends DefaultCaret {
		/** unfocused painter **/
	    private final Highlighter.HighlightPainter unfocusedPainter = 
	    		new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
	    /** focused pointer **/
	    private final Highlighter.HighlightPainter focusedPainter = 
	    		new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
	    /** serial version **/
	    private static final long serialVersionUID = 1L;
	    /** this flag is used for checking focus status **/
	    private boolean isFocused;

	    @Override
	    protected Highlighter.HighlightPainter getSelectionPainter() {
	        setBlinkRate(500);
	        return isFocused ? focusedPainter : unfocusedPainter;
	    }

	    @Override
	    public void setSelectionVisible(boolean hasFocus) {
	        if (hasFocus != isFocused) {
	            isFocused = hasFocus;
	            super.setSelectionVisible(true);
	        }
	    }
	}
}
