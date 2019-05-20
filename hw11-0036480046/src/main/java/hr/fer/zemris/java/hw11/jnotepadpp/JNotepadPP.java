package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Document;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JNotepadPP extends JFrame {

	private Path openedFilePath;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private MultipleDocumentModel multiDocModel;
    private JTextArea statusPanel;
    private String buffer = "";
	

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(10, 10);
		setSize(700, 500);
		setTitle("JNotepad++");
		
		initGUI();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{new JNotepadPP().setVisible(true);});
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		DefaultMultipleDocumentModel def = new DefaultMultipleDocumentModel();
		
		cp.add(def);
		multiDocModel = def; 
		createActions();
		createMenus();
		cp.add(createToolbar(), BorderLayout.PAGE_START);
	}	
	
	private final Action newDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			multiDocModel.createNewDocument();
			closeDocument.setEnabled(true);
			saveAsDocument.setEnabled(true);
			saveDocument.setEnabled(true);
			multiDocModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
				
				@Override
				public void caretUpdate(CaretEvent e) {
					JTextArea editArea = (JTextArea)e.getSource();
		            int linenum = 1;
		            int columnnum = 1;
		            
		            try {
		                int caretpos = editArea.getCaretPosition();
		                linenum = editArea.getLineOfOffset(caretpos);
		                columnnum = caretpos - editArea.getLineStartOffset(linenum);
		                linenum += 1;
		            }
		            catch(Exception ex) { }
		            
		            int selectedLength = Math.abs(editArea.getCaret().getDot()-
							editArea.getCaret().getMark());
		            if(selectedLength > 0) {
		            	cutSelectedPart.setEnabled(true);
		            	copySelectedPart.setEnabled(true);
		            } else {
		            	cutSelectedPart.setEnabled(false);
		            	copySelectedPart.setEnabled(false);
		            }
				}
			});
			
			// ovo je moglo biti umjesto editor.setText(text)
//			int len = editor.getDocument().getLength();
//			editor.getDocument().remove(0, len);
//			editor.getDocument().insertString(0, text, null);
		}
	};
	
	private final Action openDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if(jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			closeDocument.setEnabled(true);
			saveAsDocument.setEnabled(true);
			saveDocument.setEnabled(true);
			Path filePath = jfc.getSelectedFile().toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this,
						"Datoteku" + filePath + " nije moguće čitati", 
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			openedFilePath = filePath;
			multiDocModel.loadDocument(openedFilePath);
			
			
			multiDocModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
				
				@Override
				public void caretUpdate(CaretEvent e) {
					JTextArea editArea = (JTextArea)e.getSource();
		            int linenum = 1;
		            int columnnum = 1;
		            
		            try {
		                int caretpos = editArea.getCaretPosition();
		                linenum = editArea.getLineOfOffset(caretpos);
		                columnnum = caretpos - editArea.getLineStartOffset(linenum);
		                linenum += 1;
		            }
		            catch(Exception ex) { }
		            
		            int selectedLength = Math.abs(editArea.getCaret().getDot()-
							editArea.getCaret().getMark());
		            if(selectedLength > 0) {
		            	cutSelectedPart.setEnabled(true);
		            	copySelectedPart.setEnabled(true);
		            } else {
		            	cutSelectedPart.setEnabled(false);
		            	copySelectedPart.setEnabled(false);
		            }
				}
			});
			
			// ovo je moglo biti umjesto editor.setText(text)
//			int len = editor.getDocument().getLength();
//			editor.getDocument().remove(0, len);
//			editor.getDocument().insertString(0, text, null);
		}
	};

	private final Action saveDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(multiDocModel.getCurrentDocument().getFilePath() == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Ništa nije spremljeno.", 
							"Informacija", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			else {
				openedFilePath = multiDocModel.getCurrentDocument().getFilePath();
			}


			if(Files.exists(openedFilePath) && openedFilePath != multiDocModel.getCurrentDocument().getFilePath()) {
				int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
						"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
	            switch(result){
	                case JOptionPane.YES_OPTION:
	                    break;
	                case JOptionPane.NO_OPTION:
	                    return;
	                case JOptionPane.CLOSED_OPTION:
	                    return;
	                case JOptionPane.CANCEL_OPTION:
	                    return;
	            }
			}
			
			multiDocModel.saveDocument(multiDocModel.getCurrentDocument(), openedFilePath);
			return;
		}
	};
	
	private final Action saveAsDocument = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save as document");if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Ništa nije spremljeno.", 
						"Informacija", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();

			if(Files.exists(openedFilePath) && openedFilePath != multiDocModel.getCurrentDocument().getFilePath()) {
				int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
						"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
	            switch(result){
	                case JOptionPane.YES_OPTION:
	                    break;
	                case JOptionPane.NO_OPTION:
	                    return;
	                case JOptionPane.CLOSED_OPTION:
	                    return;
	                case JOptionPane.CANCEL_OPTION:
	                    return;
	            }
			}
			
			multiDocModel.saveDocument(multiDocModel.getCurrentDocument(), openedFilePath);
			
			return;
		}
	};
	
	private final Action closeDocument = new AbstractAction() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(multiDocModel.getCurrentDocument().getFilePath() == null) {
				JFileChooser jfc = new JFileChooser();
				
				if(JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save file "
						+ "before closing?", "Save or discard changes", JOptionPane.YES_NO_OPTION)
						!= JOptionPane.YES_OPTION) {
					multiDocModel.closeDocument(multiDocModel.getCurrentDocument());
					return;
				}
				
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Ništa nije spremljeno.", 
							"Informacija", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			else {
				openedFilePath = multiDocModel.getCurrentDocument().getFilePath();
			}

			multiDocModel.closeDocument(multiDocModel.getCurrentDocument());
			
			if(multiDocModel.getNumberOfDocuments() <= 0) {
				saveAsDocument.setEnabled(false);
				saveDocument.setEnabled(false);
				closeDocument.setEnabled(false);
			}
			
			return;
		}
	};
	
	/*
	private final Action deleteSelectedPart = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				editor.getDocument().remove(Math.min(editor.getCaret().getDot(),
						editor.getCaret().getMark()), Math.abs(editor.getCaret().getDot()-
								editor.getCaret().getMark()));
			} catch (BadLocationException e1) {
				
			}
		}
	};
	*/
	
	private final Action cutSelectedPart = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int start = Math.min(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot(),
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			int len = Math.abs(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot()-
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			
			if(len < 1) {
				return;
			}
			
			Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
			
			try {
				buffer = doc.getText(start, len);
				pasteSelectedPart.setEnabled(true);
				//text = toggleCase(text);
				doc.remove(start, len);
				//doc.insertString(start, text, null);
			} catch (Exception e2) {
			}
		}

		private String toggleCase(String text) {
			char[] chars = text.toCharArray();
			for(int i = 0; i < chars.length; i++) {
				if(Character.isUpperCase(chars[i])) {
					chars[i] = Character.toLowerCase(chars[i]);
				} else if(Character.isLowerCase(chars[i])) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
			}
			return new String(chars);
		}
	};
	
	private final Action pasteSelectedPart = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int start = Math.min(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot(),
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			int len = Math.abs(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot()-
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			
			Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
			
			try {
				doc.insertString(start, buffer, null);
			} catch (Exception e2) {
			}
		}

		private String toggleCase(String text) {
			char[] chars = text.toCharArray();
			for(int i = 0; i < chars.length; i++) {
				if(Character.isUpperCase(chars[i])) {
					chars[i] = Character.toLowerCase(chars[i]);
				} else if(Character.isLowerCase(chars[i])) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
			}
			return new String(chars);
		}
	};
	
	private final Action copySelectedPart = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int start = Math.min(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot(),
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			int len = Math.abs(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot()-
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			
			Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
			
			try {
				buffer = doc.getText(start, len);
				pasteSelectedPart.setEnabled(true);
			} catch (Exception e2) {
			}
		}
	};
	
	private final Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	private void createActions() {
		openDocument.putValue(Action.NAME, "Open");
		//openDocument.putValue(Action.NAME, tr.getTranspation("main_open"));
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 0"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_0);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open document form disk");
		
		newDocument.putValue(Action.NAME, "New");
		//newDocument.putValue(Action.NAME, tr.getTranspation("main_open"));
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Create new document");
		
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document form disk");
		saveDocument.setEnabled(false);
		
		saveAsDocument.putValue(Action.NAME, "Save as");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ALT+KeyEvent.VK_S);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save as document form disk");
		saveAsDocument.setEnabled(false);
		
		/*
		deleteSelectedPart.putValue(Action.NAME, "Delete selected part");
		deleteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Delete selection from document if selection exists");
		deleteSelectedPart.setEnabled(false);
		*/
		
		cutSelectedPart.putValue(Action.NAME, "Cut");
		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control x"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Cut selected text");
		cutSelectedPart.setEnabled(false);
		
		pasteSelectedPart.putValue(Action.NAME, "Paste");
		pasteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control v"));
		pasteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Paste text from buffer");
		pasteSelectedPart.setEnabled(false);
		
		copySelectedPart.putValue(Action.NAME, "Copy");
		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control c"));
		copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copySelectedPart.putValue(Action.SHORT_DESCRIPTION, "Copy selected text");
		copySelectedPart.setEnabled(false);
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exits editor.");
		
		closeDocument.putValue(Action.NAME, "Close tab");
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Closes tab.");
		closeDocument.setEnabled(false);
	}
	
	
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(exitAction));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(closeDocument));
		file.add(new JMenuItem(cutSelectedPart));
		file.add(new JMenuItem(pasteSelectedPart));
		file.add(new JMenuItem(copySelectedPart));
		JMenu edit = new JMenu("Edit");
		mb.add(edit);
		//edit.add(new JMenuItem(deleteSelectedPart));
		edit.add(new JMenuItem(cutSelectedPart));
		
		setJMenuBar(mb);
	}

	private JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		tb.add(new JButton(newDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(exitAction));
		tb.add(new JButton(cutSelectedPart));
		tb.add(new JButton(pasteSelectedPart));
		tb.add(new JButton(copySelectedPart));
		return tb;
	}
	
	
	
}
