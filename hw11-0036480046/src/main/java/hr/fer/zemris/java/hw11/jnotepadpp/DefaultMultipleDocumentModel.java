package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.BorderFactory;
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
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;

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
	FormLocalizationProvider flp;
	
	public DefaultMultipleDocumentModel(FormLocalizationProvider flp) {
		imageIconGreen = getIcon(pathToGreen);
		imageIconRed = getIcon(pathToRed);
		this.flp = flp;
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
		
		JToolBar toolbar = new JToolBar();
		
		JPanel status = new JPanel(new GridLayout(1, 3));
		
		LJLabel labelHelp = new LJLabel("length", flp);
		labelHelp.setText(labelHelp.getLocalizedText() + ": " + 0);
		//JLabel label1 = new JLabel(": " + String.valueOf(0));
		JLabel label2 = new JLabel("Ln: " + 0 + "     Col: " + 0 + "     Sel: " + 0);
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JLabel label3 = new JLabel(timeStamp);
		label3.setHorizontalAlignment(RIGHT);
		
		Timer t = new Timer(1000, new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // TODO Auto-generated method stub
		        label3.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

		        }

		    });

		 t.start();
		
		labelHelp.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		status.add(labelHelp);
		//status.add(label1);
		status.add(label2);
		status.add(label3);

		toolbar.add(status);
		toolbar.setFloatable(false);
		this.currentSingleDocumentModel.getTextComponent().addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea)e.getSource();
                int linenum = 1;
                int columnnum = 1;
                
                try {
                    int caretpos = editArea.getCaretPosition();
                    linenum = editArea.getLineOfOffset(caretpos);
                    columnnum = caretpos - editArea.getLineStartOffset(linenum)+1;
                    linenum += 1;
                }
                catch(Exception ex) { }
                labelHelp.setText(labelHelp.getLocalizedText() + ": " + editArea.getText().length());
                //label1.setText(": " + editArea.getText().length());
                
                int selectedLength = Math.abs(editArea.getCaret().getDot()-
						editArea.getCaret().getMark());
                
                
                label2.setText("Ln: " + linenum + "     Col: " + columnnum + "     Sel: " + selectedLength);

                label3.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			}
			
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(currentSingleDocumentModel.getTextComponent(), BorderLayout.CENTER);
		panel.add(toolbar, BorderLayout.PAGE_END);
		
		addTab("(unnamed)", imageIconGreen, panel, "(unnamed)");
		
		setSelectedIndex(col.size()-1);
		return newSingleDoc;
	}
	

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return col.get(getSelectedIndex());
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		for(int i = 0; i < getNumberOfDocuments(); i++) {
			SingleDocumentModel document = getDocument(i);
			if(path.equals(document.getFilePath())) {
				currentSingleDocumentModel = col.get(i);
				setSelectedIndex(i);
				return currentSingleDocumentModel;
			}
		}
		
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
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(currentSingleDocumentModel.getTextComponent()),
				BorderLayout.CENTER);
		componentDictionary.put(currentSingleDocumentModel.getTextComponent(), panel);

		addTab(currentSingleDocumentModel.getFilePath().getFileName().toString(),
				imageIconGreen, panel, 
				currentSingleDocumentModel.getFilePath().getFileName().toString());
		col.add(currentSingleDocumentModel);
		setSelectedIndex(col.size()-1);
		
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
		if(model.getFilePath() == null) {
			model.setFilePath(newPath);
		}
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
		componentDictionary.remove(model.getTextComponent());
		remove(getSelectedIndex());
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
