package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.StatusJLabel;

/**
 * This class represents Notepad++ program that has functionality to choose between
 * three languages(english, german, croatian). It is possible to add more tabs and 
 * edit text.
 * @author Daria Matković
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/** opened file path **/
	private Path openedFilePath;
	/** multiple document model **/
	private MultipleDocumentModel multiDocModel;
	/** buffer **/
    private String buffer = "";
    /** form localization provider **/
    private FormLocalizationProvider flp = new FormLocalizationProvider
    		(LocalizationProvider.getInstance(), this);
	
    /**
     * Constructor that is used for initializing window size, location and title
     */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(10, 10);
		setSize(700, 500);
		setTitle("JNotepad++");
		
		initGUI();
	}
	
	/**
	 * This method is executed when program is run
	 * @param args takes no arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{new JNotepadPP().setVisible(true);});
	}

	/**
	 * This method initialize gui components
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel status = new JPanel(new GridLayout(1, 3));
		JPanel helpLayout = new JPanel(new BorderLayout());
		
		LJLabel label1 = new LJLabel("length", flp);
		label1.setText(label1.getLocalizedText() + ": " + 0);
		
		StatusJLabel label2 = new StatusJLabel("statusInfo", flp);
		label2.setText(label2.getLocalizedLn() + ": " + 0 + " " + label2.getLocalizedCol()
		+ ": " + 0 + " " + label2.getLocalizedSel() + ": " + 0);
		
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		JLabel label3 = new JLabel(timeStamp);
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		
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

		DefaultMultipleDocumentModel def = new DefaultMultipleDocumentModel(flp, status);
		helpLayout.add(def, BorderLayout.CENTER);
		helpLayout.add(status, BorderLayout.PAGE_END);
		
		cp.add(helpLayout, BorderLayout.CENTER);
		
		multiDocModel = def; 
		createActions();
		createMenus();
		cp.add(createToolbar(), BorderLayout.PAGE_START);
	}	
	
	/**
	 * This class represents action for adding new document in tab
	 */
	private final Action newDocument = new LocalizableAction("new", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multiDocModel.createNewDocument();
			closeDocument.setEnabled(true);
			saveAsDocument.setEnabled(true);
			saveDocument.setEnabled(true);
			statisticalInfo.setEnabled(true);
			
			multiDocModel.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
				
				@Override
				public void caretUpdate(CaretEvent e) {
					JTextArea editArea = (JTextArea)e.getSource();
		            int selectedLength = Math.abs(editArea.getCaret().getDot()-
							editArea.getCaret().getMark());
		            if(selectedLength > 0) {
		            	cutSelectedPart.setEnabled(true);
		            	copySelectedPart.setEnabled(true);
		            	uppercase.setEnabled(true);
		    			lowercase.setEnabled(true);
		    			invertCase.setEnabled(true);
		    			asc.setEnabled(true);
		    			desc.setEnabled(true);
		    			unique.setEnabled(true);
		            } else {
		            	cutSelectedPart.setEnabled(false);
		            	copySelectedPart.setEnabled(false);
		            	uppercase.setEnabled(false);
		    			lowercase.setEnabled(false);
		    			invertCase.setEnabled(false);
		    			asc.setEnabled(false);
		    			desc.setEnabled(false);
		    			unique.setEnabled(false);
		            }
				}
			});
		}
	};
	
	/**
	 * This class represents action for opening document saved on disc
	 */
	private final Action openDocument = new LocalizableAction("open", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
			statisticalInfo.setEnabled(true);
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
		            
		            int selectedLength = Math.abs(editArea.getCaret().getDot()-
							editArea.getCaret().getMark());
		            if(selectedLength > 0) {
		            	cutSelectedPart.setEnabled(true);
		            	copySelectedPart.setEnabled(true);
		            	uppercase.setEnabled(true);
		    			lowercase.setEnabled(true);
		    			invertCase.setEnabled(true);
		    			asc.setEnabled(true);
		    			desc.setEnabled(true);
		    			unique.setEnabled(true);
		            } else {
		            	cutSelectedPart.setEnabled(false);
		            	copySelectedPart.setEnabled(false);
		            	uppercase.setEnabled(false);
		    			lowercase.setEnabled(false);
		    			invertCase.setEnabled(false);
		    			asc.setEnabled(false);
		    			desc.setEnabled(false);
		    			unique.setEnabled(false);
		            }
				}
			});
		}
	};

	/**
	 * This class represents action for saving document
	 */
	private final Action saveDocument = new LocalizableAction("save", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
	
	/**
	 * This class represents action for saving as
	 */
	private final Action saveAsDocument = new LocalizableAction("saveas", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
	
	/**
	 * This class represents action for printing statistical info
	 */
	private final Action statisticalInfo = new LocalizableAction("statistics", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options = { "OK" };
			
			long len = multiDocModel.getCurrentDocument().getTextComponent().getText().length();
			long nonBlank = multiDocModel.getCurrentDocument().getTextComponent()
					.getText().replaceAll("\\s+", "").length();
			long lines = multiDocModel.getCurrentDocument().getTextComponent()
					.getText().split("\r\n|\r|\n").length;
			
			JOptionPane.showOptionDialog(null, "Your document has " + len + " characters, " +
					nonBlank + " non-blank characters and " + lines + " lines.", "Statistical info",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, options, options[0]);
		}
	};
	
	/**
	 * This class represents action for closing document
	 */
	private final Action closeDocument = new LocalizableAction("close", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(multiDocModel.getCurrentDocument().getFilePath() == null) {
				JFileChooser jfc = new JFileChooser();
				
				if(JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save file "
						+ "before closing?", "Save or discard changes", JOptionPane.YES_NO_OPTION)
						!= JOptionPane.YES_OPTION) {
					multiDocModel.closeDocument(multiDocModel.getCurrentDocument());
					
					if(multiDocModel.getNumberOfDocuments() <= 0) {
						saveAsDocument.setEnabled(false);
						saveDocument.setEnabled(false);
						closeDocument.setEnabled(false);
						cutSelectedPart.setEnabled(false);
						copySelectedPart.setEnabled(false);
						pasteSelectedPart.setEnabled(false);
						statisticalInfo.setEnabled(false);
						uppercase.setEnabled(false);
						lowercase.setEnabled(false);
						invertCase.setEnabled(false);
						asc.setEnabled(false);
						desc.setEnabled(false);
						unique.setEnabled(false);
					}
					
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
			
			if(multiDocModel.getCurrentDocument().isModified()) {
				multiDocModel.saveDocument(multiDocModel.getCurrentDocument(), openedFilePath);
			}
			multiDocModel.closeDocument(multiDocModel.getCurrentDocument());
			
			if(multiDocModel.getNumberOfDocuments() <= 0) {
				saveAsDocument.setEnabled(false);
				saveDocument.setEnabled(false);
				closeDocument.setEnabled(false);
				cutSelectedPart.setEnabled(false);
				copySelectedPart.setEnabled(false);
				pasteSelectedPart.setEnabled(false);
				statisticalInfo.setEnabled(false);
				uppercase.setEnabled(false);
				lowercase.setEnabled(false);
				invertCase.setEnabled(false);
				asc.setEnabled(false);
				desc.setEnabled(false);
				unique.setEnabled(false);
			}

			return;
		}
	};
	
	/**
	 * This class represents action for cutting selected part
	 */
	private final Action cutSelectedPart = new LocalizableAction("cut", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
				doc.remove(start, len);
			} catch (Exception e2) {
			}
		}
	};
	
	/**
	 * This class represents action for pasting selected part
	 */
	private final Action pasteSelectedPart = new LocalizableAction("paste", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int start = Math.min(multiDocModel.getCurrentDocument().getTextComponent().getCaret().getDot(),
					multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark());
			
			Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
			
			try {
				doc.insertString(start, buffer, null);
			} catch (Exception e2) {
			}
		}
	};
	
	/*
	 * This class represents action for copying selected part
	 */
	private final Action copySelectedPart = new LocalizableAction("copy", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
	
	/*
	 * This class represents action for exiting
	 */
	private final Action exitAction = new LocalizableAction("exit", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	/**
	 * This method creates actions, adds keyboard shortcuts and sets enable status for
	 * each component
	 */
	private void createActions() {
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 0"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_0);
		
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.setEnabled(false);
		
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ALT+KeyEvent.VK_S);
		saveAsDocument.setEnabled(false);
		
		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutSelectedPart.setEnabled(false);
		
		pasteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteSelectedPart.setEnabled(false);
		
		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copySelectedPart.setEnabled(false);
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeDocument.setEnabled(false);
		
		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticalInfo.setEnabled(false);
		
		uppercase.setEnabled(false);
		lowercase.setEnabled(false);
		invertCase.setEnabled(false);
		asc.setEnabled(false);
		desc.setEnabled(false);
		unique.setEnabled(false);
	}
	
	/**
	 * This action sets language to english
	 */
	private final Action en = new LocalizableAction("eng", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * This class represents action that sets language to croatian
	 */
	private final Action hr = new LocalizableAction("hr", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	/**
	 * This class represents action that sets language to german
	 */
	private final Action de = new LocalizableAction("de", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
	
	/**
	 * This class represents action that sets uppercase for selected text
	 */
	private final Action uppercase = new LocalizableAction("uppercase", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
				doc.insertString(start, buffer.toUpperCase(), null);
				doc.remove(start+len, len);
			} catch (Exception e2) {
			}
		}
	};
	
	/**
	 * This class represents action that sets selected text to lowercase
	 */
	private final Action lowercase = new LocalizableAction("lowercase", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
				doc.insertString(start, buffer.toLowerCase(), null);
				doc.remove(start+len, len);
			} catch (Exception e2) {
			}
		}
	};
	
	/**
	 * This class represents action that inverts case of selected text
	 */
	private final Action invertCase = new LocalizableAction("invertCase", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

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
				StringBuilder help = new StringBuilder();
				
				for(int i = 0; i < buffer.length(); i++) {
					if(Character.isLowerCase(buffer.charAt(i))) {
						help.append(Character.toUpperCase(buffer.charAt(i)));
					} else {
						help.append(Character.toLowerCase(buffer.charAt(i)));
					}
				}
				
				doc.insertString(start, help.toString(), null);
				doc.remove(start+len, len);
			} catch (Exception e2) {
			}
		}
	};
	
	/**
	 * This class represents action for sorting selected lines in ascending order
	 */
	private final Action asc = new LocalizableAction("asc", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int caretpos = multiDocModel.getCurrentDocument().getTextComponent().getCaretPosition();
			int caretEnd = multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark();
			
            try {
				int lineend = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretpos);
				int linestart = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretEnd);
			
				String[] lines = multiDocModel.getCurrentDocument().getTextComponent().getText().split("\n");

				Locale hrLocale = new Locale("hr");
				Collator hrCollator = Collator.getInstance(hrLocale);
				
				int help = lineend;
				lineend = lineend > linestart ? lineend : linestart;
				linestart = help > linestart ? linestart : help;
				
				
				for(int i = linestart; i <= lineend; i++) {
					for (int j = i + 1; j <= lineend; j++) {
				        if (hrCollator.compare(lines[i], lines[j]) > 0) {
				        	String tmp = lines[i];
				        	lines[i] = lines[j];
				        	lines[j] = tmp;
				        }
				    }
				}

				Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
				doc.remove(0, doc.getLength());
				
				String joinedString = String.join("\n", lines);
				doc.insertString(0, joinedString, null);
				
				
			} catch (BadLocationException e1) {
			}
		}
	};
	
	/**
	 * This class represents action for sorting selected lines in descending order
	 */
	private final Action desc = new LocalizableAction("desc", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int caretpos = multiDocModel.getCurrentDocument().getTextComponent().getCaretPosition();
			int caretEnd = multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark();
			
            try {
				int lineend = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretpos);
				int linestart = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretEnd);
			
				String[] lines = multiDocModel.getCurrentDocument().getTextComponent().getText().split("\n");

				Locale hrLocale = new Locale("hr");
				Collator hrCollator = Collator.getInstance(hrLocale);
				
				int help = lineend;
				lineend = lineend > linestart ? lineend : linestart;
				linestart = help > linestart ? linestart : help;
				
				
				for(int i = linestart; i <= lineend; i++) {
					for (int j = i + 1; j <= lineend; j++) {
				        if (hrCollator.compare(lines[i], lines[j]) <= 0) {
				        	String tmp = lines[i];
				        	lines[i] = lines[j];
				        	lines[j] = tmp;
				        }
				    }
				}

				Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
				doc.remove(0, doc.getLength());
				
				String joinedString = String.join("\n", lines);
				doc.insertString(0, joinedString, null);
				
				
			} catch (BadLocationException e1) {
			}
		}
	};
	
	/**
	 * This class represents action for removing duplicated lines in selected lines
	 */
	private final Action unique = new LocalizableAction("unique", flp) {
		
		/**
		 * serial version
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int caretpos = multiDocModel.getCurrentDocument().getTextComponent().getCaretPosition();
			int caretEnd = multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark();
			
            try {
				int lineend = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretpos);
				int linestart = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretEnd);
			
				String[] lines = multiDocModel.getCurrentDocument().getTextComponent().getText().split("\n");

				int help = lineend;
				lineend = lineend > linestart ? lineend : linestart;
				linestart = help > linestart ? linestart : help;
				
				Set<String> set = new LinkedHashSet<String>();

				for(int i = linestart; i < lineend+1; i++){
				  set.add(lines[i]);
				}
				
				int newSize = lines.length - (lineend - linestart) + set.size()-1;
				String[] newLines = new String[newSize];
				
				for(int i = 0; i < linestart; i++) {
					newLines[i] = lines[i];
				}
				
				int index = linestart; 
				for(String el:set) {
					newLines[index++] = el;
				}
				
				for(int i = lineend+1; i < lines.length; i++) {
					newLines[index++] = lines[i]; 
				}
				
				Document doc = multiDocModel.getCurrentDocument().getTextComponent().getDocument();
				doc.remove(0, doc.getLength());
				
				String joinedString = String.join("\n", newLines);
				doc.insertString(0, joinedString, null);
				
				
			} catch (BadLocationException e1) {
			}
		}
	};
	
	/*
	 * This method creates menu
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		LJMenu file = new LJMenu("file", flp);
		mb.add(file);
		file.add(newDocument);
		file.add(openDocument);
		file.add(saveDocument);
		file.add(exitAction);
		file.add(saveAsDocument);
		file.add(closeDocument);
		
		LJMenu edit = new LJMenu("edit", flp);
		mb.add(edit);
		edit.add(cutSelectedPart);
		edit.add(pasteSelectedPart);
		edit.add(copySelectedPart);
		
		LJMenu languages = new LJMenu("languages", flp);
		mb.add(languages);
		languages.add(de);
		languages.add(hr);
		languages.add(en);
		
		LJMenu info = new LJMenu("info", flp);
		mb.add(info);
		info.add(statisticalInfo);
		
		LJMenu tools = new LJMenu("tools", flp);
		LJMenu changeCase = new LJMenu("changeCase", flp);
		tools.add(changeCase);
		changeCase.add(uppercase);
		changeCase.add(lowercase);
		changeCase.add(invertCase);
		
		LJMenu sort = new LJMenu("sort", flp);
		tools.add(sort);
		sort.add(asc);
		sort.add(desc);
		mb.add(tools);
		
		tools.add(unique);
		
		setJMenuBar(mb);
	}

	/**
	 * This method creates tool  bar with functionalities to make new document, open
	 * document, save document, save ad, close, exit, cut, copy, paste and statistical info.
	 * @return created tool bar
	 */
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
		tb.add(new JButton(statisticalInfo));
		return tb;
	}
}
