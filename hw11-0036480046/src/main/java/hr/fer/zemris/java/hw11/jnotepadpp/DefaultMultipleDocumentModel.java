package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentListener {

	List<SingleDocumentModel> col = new ArrayList<SingleDocumentModel>();
	DefaultSingleDocumentModel currentSingleDocumentModel;
	private List<MultipleDocumentListener> listenerList;
	
	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		currentSingleDocumentModel = (DefaultSingleDocumentModel) currentModel;
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
		col.add(model);
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
		col.remove(model);
	}

	private void loadDocument(Path filePath) {
		Objects.requireNonNull(filePath, "File path can't be null");
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open file");
		if(jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		if(Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(this,
					"Datoteku" + filePath + " nije moguće čitati", 
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String text = null;
		try {
			text = Files.readString(filePath);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					this,
					"Došlo je do pogreške pri čitanju datoteke " + filePath, 
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		currentSingleDocumentModel = new DefaultSingleDocumentModel(filePath,
				text);
		currentSingleDocumentModel.getTextComponent().setText(text);
	}
	
	private void saveDocument(Path newPath) {
		if(newPath == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(this, "Ništa nije spremljeno.", 
						"Informacija", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			newPath = jfc.getSelectedFile().toPath();
		}
		try {
			Files.writeString(newPath, currentSingleDocumentModel.getTextComponent().getText());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Dogodila se greška pri spremanju!", 
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		//editor.getDocument().getText(0, editor.getDocument().getLength());
		
		JOptionPane.showMessageDialog(
				this,
				"Dokument je uredno spremljen", 
				"Informacija", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
	
	private void closeDocument() {
		currentSingleDocumentModel.editor.dispose();
	}
	
}
