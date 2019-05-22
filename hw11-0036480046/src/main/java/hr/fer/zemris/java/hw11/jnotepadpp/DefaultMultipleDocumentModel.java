package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.StatusJLabel;

/**
 * This class represents default multiple document model that extends JTabbedPane
 * and implements MultipleDocumentModel.
 * @author Daria Matković
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/** single document model collection **/
	List<SingleDocumentModel> col = new ArrayList<SingleDocumentModel>();
	/** current single document model**/
	SingleDocumentModel currentSingleDocumentModel;
	/** current index **/
	private int currentIndex = 0;
	/** list of multiple document listeners **/
	private List<MultipleDocumentListener> listenerList;
	/** component dictionary **/
	private Map<JTextArea, JPanel> componentDictionary = new HashMap<>();
	/** path to green icon **/
	String pathToGreen = "icons/green.png";
	/** path to red icon **/
	String pathToRed = "icons/red.png";
	/** green image icon **/
	ImageIcon imageIconGreen;
	/** red image icon **/
	ImageIcon imageIconRed;
	/* form localization provider **/
	FormLocalizationProvider flp;
	
	/**
	 * Constructor for DefaultMultipleDocumentModel gets icons and initialize flp
	 * @param flp FormLocalizationProvider
	 */
	public DefaultMultipleDocumentModel(FormLocalizationProvider flp) {
		imageIconGreen = getIcon(pathToGreen);
		imageIconRed = getIcon(pathToRed);
		this.flp = flp;
	}

	/**
	 * This method gets icon from given path
	 * @param path icon's path
	 * @return image icon from given path
	 */
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
		
		currentSingleDocumentModel.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), imageIconRed);
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
			}
		});
		
		JToolBar toolbar = new JToolBar();
		JPanel status = new JPanel(new GridLayout(1, 3));
		
		LJLabel label1 = new LJLabel("length", flp);
		label1.setText(label1.getLocalizedText() + ": " + 0);
		
		StatusJLabel label2 = new StatusJLabel("statusInfo", flp);
		label2.setText(label2.getLocalizedLn() + ": " + 0 + " " + label2.getLocalizedCol()
		+ ": " + 0 + " " + label2.getLocalizedSel() + ": " + 0);
		
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JLabel label3 = new JLabel(timeStamp);
		label3.setHorizontalAlignment(RIGHT);
		
		Timer t = new Timer(1000, new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        label3.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
		    }

		});

		t.start();
		 
		label1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		status.add(label1);
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
                label1.setText(label1.getLocalizedText() + ": " + editArea.getText().length());
                
                int selectedLength = Math.abs(editArea.getCaret().getDot()-
						editArea.getCaret().getMark());
                label2.setText(label2.getLocalizedLn() + ": " + linenum + " " + label2.getLocalizedCol()
                		+ ": " + columnnum + " " + label2.getLocalizedSel() + ": " + selectedLength);
                
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
			return;
		}
		
		if(model.getFilePath() == null) {
			model.setFilePath(newPath);
		}

		// if file is saved as existing file opened in other tab, current document should
		// be saved and tab should be closed 
		for(int i = 0; i < getNumberOfDocuments(); i++) {
			if(i == currentIndex) {
				continue;
			}
			
			SingleDocumentModel document = getDocument(i);
			if(newPath.equals(document.getFilePath())) {
				componentDictionary.remove(model.getTextComponent());
				remove(getSelectedIndex());
				currentSingleDocumentModel = col.get(i);
				col.remove(model);
				
				JOptionPane.showMessageDialog(
						this,
						"Dokument je uredno spremljen i vec je bio otvoren.", 
						"Informacija", JOptionPane.INFORMATION_MESSAGE);
				
				return;
			}
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
