package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class represents demo program for gui.
 * @author Daria Matković
 *
 */
public class DemoFrame1 extends JFrame {
	
	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that doesn't set size of window
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}
	/*
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}
	*/
	
	/**
	 * This method adds compoenents to window
	 */
	private void initGUI() {
		Container cp = getContentPane();	
		((JPanel)cp).setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1,1));
		cp.add(l("tekst 2"), new RCPosition(2,3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
		cp.add(l("tekst kraći"), new RCPosition(4,2));
		cp.add(l("tekst srednji"), new RCPosition(4,5));
		cp.add(l("tekst"), new RCPosition(4,7));
		cp.add(l("tekst test"), new RCPosition(5,6));
	}
	
	/**
	 * This method initialize color and text of label
	 * @param text label text
	 * @return label with given text
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new DemoFrame1().setVisible(true);
		});
	}
}
