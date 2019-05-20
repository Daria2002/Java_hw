package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JNotepadPP extends JFrame {

	private Path openedFilePath;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private MultipleDocumentModel multiDocModel;
    private JTextArea statusPanel;
	

	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(10, 10);
		setSize(500, 500);
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
	
	
	private final Action toggleSelectedPart = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int start = Math.min(editor.getCaret().getDot(),
					editor.getCaret().getMark());
			int len = Math.abs(editor.getCaret().getDot()-
							editor.getCaret().getMark());
			
			if(len < 1) {
				return;
			}
			Document doc = editor.getDocument();
			
			try {
				String text = doc.getText(start, len);
				text = toggleCase(text);
				doc.remove(start, len);
				doc.insertString(start, text, null);
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
	*/
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
		/*
		deleteSelectedPart.putValue(Action.NAME, "Delete selected part");
		deleteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Delete selection from document if selection exists");
		deleteSelectedPart.setEnabled(false);
		
		toggleSelectedPart.putValue(Action.NAME, "Toggle case in selection");
		toggleSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
		toggleSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Toggles character casing in selection, if"
				+ " selection exists.");
		toggleSelectedPart.setEnabled(false);
		*/
		exitAction.putValue(Action.NAME, "Exit action");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exits editor.");
		
		closeDocument.putValue(Action.NAME, "Close action");
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeDocument.putValue(Action.SHORT_DESCRIPTION, "Closes tab.");
		
	}
	
	
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(exitAction));
		file.add(new JMenuItem(closeDocument));
		
		JMenu edit = new JMenu("Edit");
		mb.add(edit);
		//edit.add(new JMenuItem(deleteSelectedPart));
		//edit.add(new JMenuItem(toggleSelectedPart));
		
		setJMenuBar(mb);
	}

	private JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		tb.add(new JButton(newDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(exitAction));
		//tb.add(new JButton(toggleSelectedPart));
		return tb;
	}
	
	
	
}
