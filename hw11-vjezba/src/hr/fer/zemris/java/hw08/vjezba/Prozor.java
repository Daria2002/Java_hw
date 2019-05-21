package hr.fer.zemris.java.hw08.vjezba;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;

public class Prozor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JButton button;
	
	public Prozor() throws HeadlessException {
		LocalizationProvider.getInstance().addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				button.setText(LocalizationProvider.getInstance().getString("login"));
			}
		});
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setTitle("Demo");
		initGUI();
		pack();
	}
	
	/*
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		JButton gumb = new JButton(
		language.equals("hr") ? "Prijava" : "Login"
		);
		getContentPane().add(gumb, BorderLayout.CENTER);
		gumb.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		// Napravi prijavu...
		}
		});
	}
	*/
	
	private final Action de = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
	
	private final Action hr = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	private final Action en = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		button = new JButton(
				LocalizationProvider.getInstance().getString("login")
				);
		
		JMenuBar mb = new JMenuBar();
		
		JMenu edit = new JMenu("Languages");
		mb.add(edit);
		edit.add(new JMenuItem(en));
		edit.add(new JMenuItem(hr));
		edit.add(new JMenuItem(de));
		
		setJMenuBar(mb);
		
		getContentPane().add(button, BorderLayout.CENTER);
		
		createActions();
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	private void createActions() {
		en.putValue(Action.NAME, "en");
		en.putValue(Action.SHORT_DESCRIPTION, "English");
		en.setEnabled(true);
		
		de.putValue(Action.NAME, "de");
		de.putValue(Action.SHORT_DESCRIPTION, "German");
		de.setEnabled(true);
		
		hr.putValue(Action.NAME, "hr");
		hr.putValue(Action.SHORT_DESCRIPTION, "Croatian");
		hr.setEnabled(true);
	}

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Očekivao sam oznaku jezika kao argument!");
			System.err.println("Zadajte kao parametar hr ili en.");
			System.exit(-1);
			}
			final String jezik = args[0];
			SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			LocalizationProvider.getInstance().setLanguage(jezik);
			new Prozor().setVisible(true);
			}
			});
	}
}

