package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class MyCalcModel implements CalcModel {

	boolean editableModel;
	boolean positiveNumber;
	String enteredNumberString;
	Double enteredNumberDecimal;
	Double activeOperand;
	
	public MyCalcModel() {

		enteredNumberString = "";
		enteredNumberDecimal = Double.valueOf(0);
	
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getValue() {
		return enteredNumberDecimal;
	}

	@Override
	public void setValue(double value) {
		enteredNumberDecimal = value;
		
		if(Double.isInfinite(value)) {
			enteredNumberString = "Infinity";
			
		} else if(Double.isNaN(value)) {
			enteredNumberString = "NaN";
			
		} else if(Double.isInfinite(-value)) {
			enteredNumberString = "-Inifinty";
			
		} else {
			enteredNumberString = String.valueOf(value);
		}
		
		editableModel = false;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editableModel) {
			throw new CalculatorInputException("Value is not editable.");
		}
		
		positiveNumber = !positiveNumber;
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editableModel) {
			throw new CalculatorInputException("Value is not editable.");
		}
		
		if(enteredNumberString.contains(".")) {
			throw new CalculatorInputException("Decimal point is already in value.");
		}
		
		enteredNumberString += ".";
		enteredNumberDecimal = Double.valueOf(enteredNumberString);
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editableModel) {
			throw new CalculatorInputException("Model is not editable.");
		}
		
		try {
			Double help = Double.valueOf(enteredNumberString + String.valueOf(digit));
			enteredNumberString = String.valueOf(help);
			enteredNumberDecimal = Double.valueOf(enteredNumberString);
		} catch (Exception e) {
			throw new CalculatorInputException("This digit is not valid value.");
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(String.valueOf(activeOperand).isEmpty()) {
			throw new IllegalStateException("Active operand doesn't exists.");
		}
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearActiveOperand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		if("NaN".equals(enteredNumberString) || "Infinity".equals(enteredNumberString) ||
				"-Infinity".equals(enteredNumberString)) {
			return enteredNumberString;
		}
		
		if(enteredNumberString.isEmpty()) {
			return positiveNumber ? "0" : "-0";
		}
		
		return (positiveNumber ? "" : "-") + enteredNumberString;
	}
	
}
