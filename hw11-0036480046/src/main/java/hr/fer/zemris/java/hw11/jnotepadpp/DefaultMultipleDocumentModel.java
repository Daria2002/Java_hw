package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Component;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	List<SingleDocumentModel> col = new ArrayList<SingleDocumentModel>();
	SingleDocumentModel currentSingleDocumentModel;
	private int currentIndex = 0;
	private List<MultipleDocumentListener> listenerList;
	private Map<JTextArea, JPanel> componentDictionary = new HashMap<>();
	private JTabbedPane tabbedPane = new JTabbedPane();
	String pathToGreen = "icons/green.png";
	String pathToRed = "icons/red.png";
	ImageIcon imageIconGreen;
	ImageIcon imageIconRed;
	
	public DefaultMultipleDocumentModel() {
		imageIconGreen = getIcon(pathToGreen);
		imageIconRed = getIcon(pathToRed);
	}
	
	private ImageIcon getIcon(String path) {
		InputStream is = this.getClass().getResourceAsStream(path);
		if(is==null) {
			throw new IllegalArgumentException("Icon doesn't exists at given path.");
		}
		
		byte file[];
		
		try {

			byte[] buffer = new byte[8192];
		    int bytesRead;
		    ByteArrayOutputStream output = new ByteArrayOutputStream();
		    while ((bytesRead = is.read(buffer)) != -1) {
		        output.write(buffer, 0, bytesRead);
		    }
		    file = output.toByteArray();
		    is.close();
		    output.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Can't convert image to byte array.");
		}

		return new ImageIcon(file);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<SingleDocumentModel>() {

			@Override
			public boolean hasNext() {
				return currentIndex < col.size()-1;
			}

			@Override
			public SingleDocumentModel next() {
				currentIndex++;
				return col.get(currentIndex);
			}
		};
	}
	
	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel newSingleDoc = new DefaultSingleDocumentModel(null, "");
		col.add(newSingleDoc);
		currentSingleDocumentModel = newSingleDoc;
		addTab("(unnamed)", imageIconGreen, newSingleDoc.getTextComponent(), "(unnamed)");
		
		currentSingleDocumentModel.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), imageIconRed);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setSelectedIndex(col.size()-1);
		
		
		
		
		return newSingleDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return col.get(getSelectedIndex());
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		String text = null;
		try {
			text = Files.readString(path);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					this,
					"Došlo je do pogreške pri čitanju datoteke " + path, 
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		currentSingleDocumentModel = new DefaultSingleDocumentModel(path,
				text);
		
		JPanel panel = new JPanel();
		panel.add(new JScrollPane(currentSingleDocumentModel.getTextComponent()));
		componentDictionary.put(currentSingleDocumentModel.getTextComponent(), panel);
		tabbedPane.add(panel);
		
		return currentSingleDocumentModel;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		try {
			Files.writeString(newPath, col.get(getSelectedIndex()).getTextComponent().getText());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Dogodila se greška pri spremanju!", 
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			System.out.println("Desila se pogreska kod pisanja");
			//e.printStackTrace();
			return;
		}
		
		model.setFilePath(newPath);
		setIconAt(getSelectedIndex(), imageIconGreen);
		setTitleAt(getSelectedIndex(), getDocument(getSelectedIndex())
				.getFilePath().getFileName().toString());
		
		JOptionPane.showMessageDialog(
				this,
				"Dokument je uredno spremljen", 
				"Informacija", JOptionPane.INFORMATION_MESSAGE);
		return;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		tabbedPane.remove(componentDictionary.remove(model.getTextComponent()));
		col.remove(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listenerList.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listenerList.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return col.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return col.get(index);
	}
	
}
