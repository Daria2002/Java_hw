package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.DemoFrame1;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {

	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/*
	JFrame frame = new JFrame("Frame Title");
	final JTextArea comp = new JTextArea();
	JButton btn = new JButton("click");
	frame.getContentPane().add(comp, BorderLayout.CENTER);
	frame.getContentPane().add(btn, BorderLayout.SOUTH);

	btn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			comp.setText("Button has been clicked");
		}
	});

	int width = 300;
	int height = 300;
	frame.setSize(width, height);

	frame.setVisible(true);
	*/
	String n = "";
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		CalcModeImpl cmi = new CalcModeImpl();
		
		/*
		JLabel screen = new JLabel("");
		cp.add(screen, new RCPosition(1, 1));
		*/
		JButton buttonEquals = new JButton("=");
		cp.add(buttonEquals, new RCPosition(1, 6));
		
		JButton buttonClr = new JButton("clr");
		cp.add(buttonClr, new RCPosition(1, 7));
		
		JButton buttonInverse = new JButton("1/x");
		cp.add(buttonInverse, new RCPosition(2, 1));
		
		JButton buttonSin = new JButton("sin");
		cp.add(buttonSin, new RCPosition(2, 2));
		
		JButton button7 = new JButton("7");
		cp.add(button7, new RCPosition(2, 3));
		
		
		JButton button8 = new JButton("8");
		cp.add(button8, new RCPosition(2, 4));
		button8.addActionListener(new CalcValueListener() {
			
			@Override
			public void valueChanged(CalcModel model) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JButton button9 = new JButton("9");
		cp.add(button9, new RCPosition(2, 5));
		
		JButton buttonDivide = new JButton("/");
		cp.add(buttonDivide, new RCPosition(2, 6));
		
		JButton buttonReset = new JButton("reset");
		cp.add(buttonReset, new RCPosition(2, 7));
		
		JButton buttonlog = new JButton("log");
		cp.add(buttonlog, new RCPosition(3, 1));
		
		JButton buttonCos = new JButton("sin");
		cp.add(buttonCos, new RCPosition(3, 2));
		
		JButton button4 = new JButton("4");
		cp.add(button4, new RCPosition(3, 3));
		
		JButton button5 = new JButton("5");
		cp.add(button5, new RCPosition(3, 4));
		
		JButton button6 = new JButton("6");
		cp.add(button6, new RCPosition(3, 5));
		
		cmi.addCalcValueListener(new CalcValueListener() {
			
			@Override
			public void valueChanged(CalcModel model) {
				System.out.println("tu je");
				
			}
		});
		
		JButton buttonMultiply = new JButton("*");
		cp.add(buttonMultiply, new RCPosition(3, 6));

		
		
		JButton buttonPush = new JButton("push");
		cp.add(buttonPush, new RCPosition(3, 7));
		
		
		
		
		/*
		cp.add(l("tekst 1"), new RCPosition(1,1));
		cp.add(l("tekst 2"), new RCPosition(2,3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
		cp.add(l("tekst kraći"), new RCPosition(4,2));
		cp.add(l("tekst srednji"), new RCPosition(4,5));
		cp.add(l("tekst"), new RCPosition(4,7));
		cp.add(l("tekst test"), new RCPosition(5,6));
		*/
	}
	
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
	
}
