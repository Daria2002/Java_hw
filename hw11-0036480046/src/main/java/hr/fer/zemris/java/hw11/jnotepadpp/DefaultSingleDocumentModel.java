package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path filePath;
	private JTextArea textContent;
	private boolean isModified;
	private List<SingleDocumentListener> listenerList;


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
	
	class HighlightCaret extends DefaultCaret {

	    private final Highlighter.HighlightPainter unfocusedPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
	    private final Highlighter.HighlightPainter focusedPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
	    private static final long serialVersionUID = 1L;
	    private boolean isFocused;

	    @Override
	    protected Highlighter.HighlightPainter getSelectionPainter() {
	        setBlinkRate(500); // otherwise is disabled, stopped
	        return isFocused ? focusedPainter/*super.getSelectionPainter()*/ : unfocusedPainter;
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
