package hr.fer.zemris.java.hw11.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.NameList;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

public class Gui extends JFrame {
	
	List<String> dat = new ArrayList<String>();
	Container cp;
	JList<?> lis;
	
	JLabel lab2;
	JPanel pan;
	int sel;
	public Gui() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 500);
		setSize(700, 500);
		setTitle("Gui jr");
		initGui();
	}

	private final Action listNames = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if(jfc.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path filePath = jfc.getSelectedFile().toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						Gui.this,
						"Datoteku" + filePath + " nije moguće čitati", 
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
		
			try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath.toString())))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	if(line.isEmpty() || line.isBlank() || line.charAt(0) == '#') {
			    		continue;
			 
			    	}
			    	dat.add(line);
			    }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] arr1 = new String[dat.size()];
			for(int i = 0; i< dat.size(); i++) {
				arr1[i] = dat.get(i);
				System.out.println(dat.get(i));
			}
			pan = new JPanel(new GridLayout(1, 2));
			lis = new JList<Object>(arr1);
			lis.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					sel = lis.getSelectedIndex();
					String da = dat.get(sel);
					
					String[] dio = da.split("-");
					
					Calendar c = Calendar.getInstance();
					c.setTime(new Date(Integer.valueOf(dio[0])-1, Integer.valueOf(dio[1])-1, Integer.valueOf(dio[2])));
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					String hr = "";
					if(dayOfWeek == 1) {
						hr = " bila je nedjelja.";
					}
					if(dayOfWeek == 2) {
						hr = " bio je ponedjeljak.";
					}
					if(dayOfWeek == 3) {
						hr = " bio je utorak.";
					}
					if(dayOfWeek == 4) {
						hr = " bila je srijeda.";
					}
					if(dayOfWeek == 5) {
						hr = " bio je četvrtak.";
					}
					if(dayOfWeek == 6) {
						hr = " bio je petak.";
					}
					if(dayOfWeek == 7) {
						hr = " bila je subota.";
					}
					
					lab2.setText("Na datum "+ da + hr);
				}
			});
			pan.add(lis);
			
			lab2 = new JLabel();
			lab2.setText("book");
			
			pan.add(lab2);
			
			cp.add(pan, BorderLayout.CENTER);
			setContentPane(cp);
		}
	};
	
	private void initGui() {
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuInfo = new JMenu("File");
		JMenuItem listNamesItem = new JMenuItem(listNames);
		listNamesItem.setText("Open");
		menuInfo.add(listNamesItem);
		
		menuBar.add(menuInfo);
		
		pan = new JPanel(new BorderLayout());
		String[] arr1 = new String[dat.size()];
		for(int i = 0; i< dat.size(); i++) {
			arr1[i] = dat.get(i);
			System.out.println(dat.get(i));
		}
		
		lis = new JList<Object>(arr1);
		
		lab2 = new JLabel();
		lab2.setText("");
		
		setJMenuBar(menuBar);
		show();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {new Gui().setVisible(true);});
	}
	
}
