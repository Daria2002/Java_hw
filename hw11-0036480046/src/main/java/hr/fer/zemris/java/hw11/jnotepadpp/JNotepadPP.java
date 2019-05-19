package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class JNotepadPP extends JFrame {

	private Path openedFilePath;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private MultipleDocumentModel multiDocModel;
    private JTextField status;
	
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
		
//        editor.addCaretListener(new CaretListener() {
//            // Each time the caret is moved, it will trigger the listener and its method caretUpdate.
//            // It will then pass the event to the update method including the source of the event (which is our textarea control)
//            public void caretUpdate(CaretEvent e) {
//                JTextArea editArea = (JTextArea)e.getSource();
//
//                int linenum = 1;
//                int columnnum = 1;
//                
//                try {
//                    int caretpos = editArea.getCaretPosition();
//                    linenum = editArea.getLineOfOffset(caretpos);
//                    columnnum = caretpos - editArea.getLineStartOffset(linenum);
//                    linenum += 1;
//                }
//                catch(Exception ex) { }
//
//                // Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
//                updateStatus(linenum, columnnum);
//            }
//        });
//
//        // Add the fields to the layout, the editor in the middle and the status at the bottom.
//        add(editor, BorderLayout.CENTER);
//
//        status = new JTextField();
//        add(status, BorderLayout.SOUTH);
//
//        // Give the status update value
//        updateStatus(1,1);
	}	
//	
//	private void updateStatus(int linenumber, int columnnumber) {
//		status.setText("Line: " + linenumber + " Column: " + columnnumber);
//    }
	
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
			if(Files.isReadable(filePath)) {
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
			if(openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Ništa nije spremljeno.", 
							"Informacija", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			multiDocModel.saveDocument(multiDocModel.getCurrentDocument(), openedFilePath);
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
		
	}

	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));
		
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
		tb.add(new JButton(exitAction));
		//tb.add(new JButton(toggleSelectedPart));
		return tb;
	}
	
	
	
}
