package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

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
	private static Container cp;
	private static boolean invMode = false;

	private static JButton buttonSin = new JButton("sin");
	private static JButton buttonLog = new JButton("log");
	private static JButton buttonCos = new JButton("cos");
	private static JButton buttonLn = new JButton("ln");
	private static JButton buttonTan = new JButton("tan");
	private static JButton buttonXN = new JButton("x^n");
	private static JButton buttonCtg = new JButton("ctg");
	
	private static Stack<Double> stack = new Stack<Double>();
	
	private static ActionListener checkBoxListener = 
			new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			invMode = !invMode;
			if(!invMode) {
				buttonSin.setText("sin");
				buttonLog.setText("log");
				buttonCos.setText("cos");
				buttonLn.setText("ln");
				buttonTan.setText("tan");
				buttonXN.setText("x^n");
				buttonCtg.setText("ctg");
			
			} else {
				buttonSin.setText("arcsin");
				buttonLog.setText("10^x");
				buttonCos.setText("arccos");
				buttonLn.setText("e^x");
				buttonTan.setText("arctan");
				buttonXN.setText("x^(1/n)");
				buttonCtg.setText("arcctg");
			}
		}
	};
	
	private static ActionListener numberListener =
		(e) -> {
			JButton button = (JButton)e.getSource();
			cmi.insertDigit(Integer.valueOf(button.getText()));
		};
		
	private static ActionListener sinListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(Math.asin(cmi.getValue()));
			} else {
				cmi.setValue(Math.sin(cmi.getValue()));
			}
	};
	
	private static ActionListener logListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(Math.pow(10, cmi.getValue()));
			} else {
				cmi.setValue(Math.log10(cmi.getValue()));
			}
	};
			
	private static ActionListener cosListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(Math.acos(cmi.getValue()));
			} else {
				cmi.setValue(Math.cos(cmi.getValue()));
			}
	};
	
	private static ActionListener lnListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(Math.pow(Math.E, cmi.getValue()));
			} else {
				cmi.setValue(Math.log(cmi.getValue()));
			}
	};

	private static ActionListener tanListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(Math.tan(cmi.getValue()));
			} else {
				cmi.setValue(Math.atan(cmi.getValue()));
			}
	};		
	
	private static ActionListener xNListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(Math.pow(Math.E, cmi.getValue()));
			} else {
				cmi.setValue(Math.log(cmi.getValue()));
			}
	};

	private static ActionListener ctgListener = 
		(e) -> {
			if(invMode) {
				cmi.setValue(1.0 / Math.tan(cmi.getValue()));
			} else {
				cmi.setValue(Math.PI / 2 - Math.atan(cmi.getValue()));
			}
	};
	
	private static ActionListener equalsListener = (e) -> {cmi.setValue(
			cmi.pendingOperation.applyAsDouble(cmi.activeOperand, cmi.getValue()));};
	
	private static ActionListener clrListener = (e) -> {cmi.clear();};
	
	private static ActionListener inverseListener = (e) -> {cmi.setValue(1./cmi.getValue());}; 
	
	private static ActionListener divideListener = (e) -> {
		cmi.setActiveOperand(cmi.getValue());
		cmi.setPendingBinaryOperation(new DoubleBinaryOperator() {
			
			@Override
			public double applyAsDouble(double left, double right) {
				return left/right;
			}
		});
	};
	
	private static ActionListener resetListener = (e) -> {cmi.clearAll();};
	
	private static ActionListener multiplyListener = (e) -> {
		cmi.setActiveOperand(cmi.getValue());
		cmi.setPendingBinaryOperation(new DoubleBinaryOperator() {
			
			@Override
			public double applyAsDouble(double left, double right) {
				return left*right;
			}
		});
	};
	
	private static ActionListener pushListener = (e) -> {stack.add(cmi.getValue());};
	private static ActionListener popListener = (e) -> {
		if(stack.isEmpty()) {
			System.out.println("Stack is empty");
			System.exit(1);
		}
		
		cmi.setValue(stack.pop());
	};
	
	private static ActionListener minusListener = (e) -> {
		cmi.setActiveOperand(cmi.getValue());
		cmi.setPendingBinaryOperation(new DoubleBinaryOperator() {
			
			@Override
			public double applyAsDouble(double left, double right) {
				return left-right;
			}
		});
	};
	
	private static ActionListener swapSignListener = (e) -> {
		cmi.setValue(cmi.getValue()*(-1));};
	
	private static ActionListener dotListener = (e) -> {
		cmi.insertDecimalPoint();};
		
	private static ActionListener plusListener = (e) -> {
		cmi.setActiveOperand(cmi.getValue());
		cmi.setPendingBinaryOperation(new DoubleBinaryOperator() {
			
			@Override
			public double applyAsDouble(double left, double right) {
				return left+right;
			}
		});
	};
	
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(500, 500);
		pack();
	}
	
	String n = "";
	private void initGUI() {
		cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		CalculatorLabel screen = new CalculatorLabel();
		cp.add(screen, new RCPosition(1, 1));
		cmi.addCalcValueListener(screen);
		
		JButton buttonEquals = new JButton("=");
		cp.add(buttonEquals, new RCPosition(1, 6));
		buttonEquals.addActionListener(equalsListener);
		
		JButton buttonClr = new JButton("clr");
		cp.add(buttonClr, new RCPosition(1, 7));
		buttonClr.addActionListener(clrListener);
		
		JButton buttonInverse = new JButton("1/x");
		cp.add(buttonInverse, new RCPosition(2, 1));
		buttonInverse.addActionListener(inverseListener);
		
		cp.add(buttonSin, new RCPosition(2, 2));
		buttonSin.addActionListener(sinListener);
		
		JButton button7 = new JButton("7");
		cp.add(button7, new RCPosition(2, 3));
		button7.addActionListener(numberListener);
		
		JButton button8 = new JButton("8");
		cp.add(button8, new RCPosition(2, 4));
		button8.addActionListener(numberListener);
		
		JButton button9 = new JButton("9");
		cp.add(button9, new RCPosition(2, 5));
		button9.addActionListener(numberListener);
		
		JButton buttonDivide = new JButton("/");
		cp.add(buttonDivide, new RCPosition(2, 6));
		buttonDivide.addActionListener(divideListener);
		
		JButton buttonReset = new JButton("reset");
		cp.add(buttonReset, new RCPosition(2, 7));
		buttonReset.addActionListener(resetListener);
		
		cp.add(buttonLog, new RCPosition(3, 1));
		buttonLog.addActionListener(logListener);
		
		cp.add(buttonCos, new RCPosition(3, 2));
		buttonCos.addActionListener(cosListener);
		
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
		buttonMultiply.addActionListener(multiplyListener);
		
		JButton buttonPush = new JButton("push");
		cp.add(buttonPush, new RCPosition(3, 7));
		buttonPush.addActionListener(pushListener);
		
		cp.add(buttonLn, new RCPosition(4, 1));
		buttonLn.addActionListener(lnListener);
		
		cp.add(buttonTan, new RCPosition(4, 2));
		buttonTan.addActionListener(tanListener);
		
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
		buttonMinus.addActionListener(minusListener);

		JButton buttonPop = new JButton("pop");
		cp.add(buttonPop, new RCPosition(4, 7));
		buttonPop.addActionListener(popListener);
		
		cp.add(buttonXN, new RCPosition(5, 1));
		buttonXN.addActionListener(xNListener);
		
		cp.add(buttonCtg, new RCPosition(5, 2));
		buttonCtg.addActionListener(ctgListener);
		
		JButton button0 = new JButton("0");
		cp.add(button0, new RCPosition(5, 3));
		button0.addActionListener(numberListener);
		
		JButton buttonSwapSign = new JButton("+/-");
		cp.add(buttonSwapSign, new RCPosition(5, 4));
		buttonSwapSign.addActionListener(swapSignListener);
		
		JButton buttonDot = new JButton(".");
		cp.add(buttonDot, new RCPosition(5, 5));
		buttonDot.addActionListener(dotListener);
		
		JButton buttonPlus = new JButton("+");
		cp.add(buttonPlus, new RCPosition(5, 6));
		buttonPlus.addActionListener(plusListener);

		JCheckBox checkBox = new JCheckBox("Inv");
		cp.add(checkBox, new RCPosition(5, 7));
		checkBox.addActionListener(checkBoxListener);
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
