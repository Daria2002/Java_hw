package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Demo program for showing prime numbers in two lists. New prime number is 
 * added every time button is clicked.
 * @author Daria Matković
 *
 */
public class PrimDemo extends JPanel {

	/**
	 * Constructor that adds components to window, sets layout and other
	 * properties.
	 */
	public PrimDemo() {
		setLayout(new GridLayout(2, 1));
	  
		JFrame frame = new JFrame();
		Container cont = frame.getContentPane();
		cont.setLayout(new GridLayout(1, 2));
	  
		DefaultListModel<Integer> model = new DefaultListModel<Integer>();
		JList<Integer> list = new JList<Integer>(model);
		JList<Integer> list1 = new JList<Integer>(model);
	  
		PrimListModel plm = new PrimListModel();
	  
		JScrollPane pane = new JScrollPane(list);
		JScrollPane pane1 = new JScrollPane(list1);
  
		cont.add(pane);
		cont.add(pane1);
  
		JButton addButton = new JButton("Sljedeći");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plm.next();
				model.addElement(plm.prim.get(plm.getSize()-1));
			}
		});
	  
		add(cont);
		add(addButton);
	}
  
	/**
	 * This method is executed when program is run
	 * @param s takes no arguments
	 */
	public static void main(String s[]) {
		JFrame frame = new JFrame("List Model Example");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setContentPane(new PrimDemo());
	    frame.setSize(500, 500);
	    frame.setVisible(true);
	}
}
