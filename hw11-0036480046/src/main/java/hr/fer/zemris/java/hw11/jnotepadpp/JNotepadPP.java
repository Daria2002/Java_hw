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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.AbstractDocument.Content;

public class JNotepadPP extends JFrame {

	private Path openedFilePath;
	private JTextArea editor;
	private JTabbedPane tabbedPane = new JTabbedPane();
	DefaultMultipleDocumentModel defMultiDocModel;
    private JTextField status;
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(10, 10);
		setSize(500, 500);
		
		initGUI();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{new JNotepadPP().setVisible(true);});
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		defMultiDocModel = new DefaultMultipleDocumentModel();
		
		editor = new JTextArea();
		cp.add(new JScrollPane(editor), BorderLayout.CENTER);
		
		editor.getCaret().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
			}
		});
		
		createActions();
		createMenus();
		
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		/*
		// create the status bar panel and shove it down the bottom of the frame
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel("status");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		cp.add(statusPanel, BorderLayout.PAGE_END);
		*/
		
		// Add a caretListener to the editor. This is an anonymous class because it is inline and has no specific name.
        editor.addCaretListener(new CaretListener() {
            // Each time the caret is moved, it will trigger the listener and its method caretUpdate.
            // It will then pass the event to the update method including the source of the event (which is our textarea control)
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
                updateStatus(linenum, columnnum);
            }
        });

        // Add the fields to the layout, the editor in the middle and the status at the bottom.
        add(editor, BorderLayout.CENTER);

        status = new JTextField();
        add(status, BorderLayout.SOUTH);

        // Give the status update value
        updateStatus(1,1);
	}	
	
	private void updateStatus(int linenumber, int columnnumber) {
		status.setText("Line: " + linenumber + " Column: " + columnnumber);
    }
	
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
			String text = null;
			try {
				text = Files.readString(filePath);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this,
						"Došlo je do pogreške pri čitanju datoteke " + filePath, 
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			openedFilePath = filePath;
			editor.setText(text);
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
			try {
				Files.writeString(openedFilePath, editor.getText());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Dogodila se greška pri spremanju!", 
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			//editor.getDocument().getText(0, editor.getDocument().getLength());
			
			JOptionPane.showMessageDialog(
					JNotepadPP.this,
					"Dokument je uredno spremljen", 
					"Informacija", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	};
	
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
		
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document form disk");
		
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
		
		exitAction.putValue(Action.NAME, "Exit action");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exits editor.");
		
		
		
	}

	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));
		
		JMenu edit = new JMenu("Edit");
		mb.add(edit);
		edit.add(new JMenuItem(deleteSelectedPart));
		edit.add(new JMenuItem(toggleSelectedPart));
		
		setJMenuBar(mb);
	}

	private JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(exitAction));
		tb.add(new JButton(toggleSelectedPart));
		return tb;
	}
	
	
	
}
