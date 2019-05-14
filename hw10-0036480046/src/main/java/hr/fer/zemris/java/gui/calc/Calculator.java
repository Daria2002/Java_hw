package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

	private static CalcModeImpl cmi = new CalcModeImpl();
	private static JLabel screen;
	
	private static ActionListener numberListener =
		(e) -> {
			JButton button = (JButton)e.getSource();
			cmi.insertDigit(Integer.valueOf(button.getText()));
			
			if(cmi.containsDot) {
				screen.setText(String.valueOf(cmi.getValue()));
			} else {
				// step 1: unboxing
				double dbl = cmi.getValue();

				// step 2: casting
				int intgr = (int) dbl;

				// step 3: boxing
				Integer val = Integer.valueOf(intgr);
				screen.setText(val.toString());
			}
		};
		
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(500, 500);
		pack();
	}
	
	String n = "";
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		screen = new JLabel("");
		cp.add(screen, new RCPosition(1, 1));
		
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
		button7.addActionListener(numberListener);
		
		JButton button8 = new JButton("8");
		cp.add(button8, new RCPosition(2, 4));
		button8.addActionListener(numberListener);
		
		cmi.addCalcValueListener(new CalcValueListener() {
			
			@Override
			public void valueChanged(CalcModel model) {
				// step 1: unboxing
				double dbl = model.getValue();

				// step 2: casting
				int intgr = (int) dbl;

				// step 3: boxing
				Integer val = Integer.valueOf(intgr);
				screen.setText(val.toString());
			}
		});
		
		JButton button9 = new JButton("9");
		cp.add(button9, new RCPosition(2, 5));
		button9.addActionListener(numberListener);
		
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
		button4.addActionListener(numberListener);
		
		JButton button5 = new JButton("5");
		cp.add(button5, new RCPosition(3, 4));
		button5.addActionListener(numberListener);
		
		JButton button6 = new JButton("6");
		cp.add(button6, new RCPosition(3, 5));
		button6.addActionListener(numberListener);
		
		JButton buttonMultiply = new JButton("*");
		cp.add(buttonMultiply, new RCPosition(3, 6));

		JButton buttonPush = new JButton("push");
		cp.add(buttonPush, new RCPosition(3, 7));
		
		JButton buttonEX = new JButton("e^x");
		cp.add(buttonEX, new RCPosition(4, 1));
		
		JButton buttonArctg = new JButton("arctan");
		cp.add(buttonArctg, new RCPosition(4, 2));
		
		JButton button1 = new JButton("1");
		cp.add(button1, new RCPosition(4, 3));
		button1.addActionListener(numberListener);
		
		JButton button2 = new JButton("2");
		cp.add(button2, new RCPosition(4, 4));
		button2.addActionListener(numberListener);
		
		JButton button3 = new JButton("3");
		cp.add(button3, new RCPosition(4, 5));
		button3.addActionListener(numberListener);
		
		JButton buttonMinus = new JButton("-");
		cp.add(buttonMinus, new RCPosition(4, 6));

		JButton buttonPop = new JButton("pop");
		cp.add(buttonPop, new RCPosition(4, 7));
		
		JButton buttonXInvN = new JButton("x^(1/n)");
		cp.add(buttonXInvN, new RCPosition(5, 1));
		
		JButton buttonArcctg = new JButton("arcctg");
		cp.add(buttonArcctg, new RCPosition(5, 2));
		
		JButton button0 = new JButton("0");
		cp.add(button0, new RCPosition(5, 3));
		
		JButton buttonSwapSign = new JButton("+/-");
		cp.add(buttonSwapSign, new RCPosition(5, 4));
		
		JButton buttonDot = new JButton(".");
		cp.add(buttonDot, new RCPosition(5, 5));
		
		JButton buttonPlus = new JButton("+");
		cp.add(buttonPlus, new RCPosition(5, 6));

		JCheckBox checkBox = new JCheckBox("Inv");
		cp.add(checkBox, new RCPosition(5, 7));
		
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
