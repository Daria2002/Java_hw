package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP extends JFrame {
	private Path openedFilePath;
	private MultipleDocumentModel multiDocModel;
    private String buffer = "";
    private FormLocalizationProvider flp = new FormLocalizationProvider
    		(LocalizationProvider.getInstance(), this);
	
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
		DefaultMultipleDocumentModel def = new DefaultMultipleDocumentModel(flp);
		
		cp.add(def);
		multiDocModel = def; 
		createActions();
		createMenus();
		cp.add(createToolbar(), BorderLayout.PAGE_START);
	}	
	
	private final Action newDocument = new LocalizableAction("new", flp) {
		
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
		            	uppercase.setEnabled(true);
		    			lowercase.setEnabled(true);
		    			invertCase.setEnabled(true);
		            } else {
		            	cutSelectedPart.setEnabled(false);
		            	copySelectedPart.setEnabled(false);
		            	uppercase.setEnabled(false);
		    			lowercase.setEnabled(false);
		    			invertCase.setEnabled(false);
		            }
				}
			});
		}
	};
	
	private final Action openDocument = new LocalizableAction("open", flp) {
		
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
		            	uppercase.setEnabled(true);
		    			lowercase.setEnabled(true);
		    			invertCase.setEnabled(true);
		            } else {
		            	cutSelectedPart.setEnabled(false);
		            	copySelectedPart.setEnabled(false);
		            	uppercase.setEnabled(false);
		    			lowercase.setEnabled(false);
		    			invertCase.setEnabled(false);
		            }
				}
			});
		}
	};

	private final Action saveDocument = new LocalizableAction("save", flp) {
		
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
	
	private final Action saveAsDocument = new LocalizableAction("saveas", flp) {
		
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
	
	private final Action statisticalInfo = new LocalizableAction("statistics", flp) {
		
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
	
	private final Action closeDocument = new LocalizableAction("close", flp) {
		
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
						statisticalInfo.setEnabled(false);
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

			multiDocModel.closeDocument(multiDocModel.getCurrentDocument());
			
			if(multiDocModel.getNumberOfDocuments() <= 0) {
				saveAsDocument.setEnabled(false);
				saveDocument.setEnabled(false);
				closeDocument.setEnabled(false);
				statisticalInfo.setEnabled(false);
			}
			
			return;
		}
	};
	
	private final Action cutSelectedPart = new LocalizableAction("cut", flp) {
		
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
	
	private final Action pasteSelectedPart = new LocalizableAction("paste", flp) {
		
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
	};
	
	private final Action copySelectedPart = new LocalizableAction("copy", flp) {
		
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
	
	private final Action exitAction = new LocalizableAction("exit", flp) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	private void createActions() {
		//openDocument.putValue(Action.NAME, tr.getTranspation("main_open"));
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 0"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_0);
		
		//newDocument.putValue(Action.NAME, tr.getTranspation("main_open"));
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.setEnabled(false);
		
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ALT+KeyEvent.VK_S);
		saveAsDocument.setEnabled(false);
		
		cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control x"));
		cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutSelectedPart.setEnabled(false);
		
		pasteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control v"));
		pasteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteSelectedPart.setEnabled(false);
		
		copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control c"));
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
	}
	
	private final Action en = new LocalizableAction("eng", flp) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	private final Action hr = new LocalizableAction("hr", flp) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	private final Action de = new LocalizableAction("de", flp) {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
	
	private final Action uppercase = new LocalizableAction("uppercase", flp) {
		
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
	
	private final Action lowercase = new LocalizableAction("lowercase", flp) {
		
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
	
	private final Action invertCase = new LocalizableAction("invertCase", flp) {
		
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
	
	private final Action asc = new LocalizableAction("asc", flp) {
		
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
	
	private final Action desc = new LocalizableAction("desc", flp) {
		
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
	
	private final Action unique = new LocalizableAction("unique", flp) {
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int caretpos = multiDocModel.getCurrentDocument().getTextComponent().getCaretPosition();
			int caretEnd = multiDocModel.getCurrentDocument().getTextComponent().getCaret().getMark();
			
            try {
				int lineend = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretpos);
				int linestart = multiDocModel.getCurrentDocument().getTextComponent().getLineOfOffset(caretEnd);
			
				String[] lines = multiDocModel.getCurrentDocument().getTextComponent().getText().split("\n");

				int firstSize = lines.length;
				
				Locale hrLocale = new Locale("hr");
				Collator hrCollator = Collator.getInstance(hrLocale);
				
				int help = lineend;
				lineend = lineend > linestart ? lineend : linestart;
				linestart = help > linestart ? linestart : help;
				
				Set<String> set = new LinkedHashSet<String>();

				for(int i = linestart; i < lineend; i++){
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
