package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
		//addTab("(unnamed)", imageIconGreen, newSingleDoc.getTextComponent(), "(unnamed)");
		
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
		JLabel status = new JLabel();
		this.currentSingleDocumentModel.getTextComponent().addCaretListener(new CaretListener() {

	        
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea)e.getSource();

                // Lets start with some default values for the line and column.
                int linenum = 1;
                int columnnum = 1;

                // We create a try catch to catch any exceptions. We will simply ignore such an error for our demonstration.
                try {
                    // First we find the position of the caret. This is the number of where the caret is in relation to the start of the JTextArea
                    // in the upper left corner. We use this position to find offset values (eg what line we are on for the given position as well as
                    // what position that line starts on.
                    int caretpos = editArea.getCaretPosition();
                    linenum = editArea.getLineOfOffset(caretpos);

                    // We subtract the offset of where our line starts from the overall caret position.
                    // So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
                    // we know that we must be on column 6 of line 5.
                    columnnum = caretpos - editArea.getLineStartOffset(linenum);

                    // We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
                    linenum += 1;
                }
                catch(Exception ex) { }

                // Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
                status.setText("Line: " + linenum + " Column: " + columnnum);
			}
			
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(currentSingleDocumentModel.getTextComponent(), BorderLayout.CENTER);
		panel.add(status, BorderLayout.PAGE_END);
		addTab("(unnamed)", imageIconGreen, panel, "(unnamed)");
		
        // Give the status update value
		status.setText("Line: " + 1 + " Column: " + 1);
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
