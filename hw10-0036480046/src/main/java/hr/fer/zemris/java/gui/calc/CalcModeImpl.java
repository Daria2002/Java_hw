package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModeImpl implements CalcModel {

	boolean editableModel;
	boolean positiveNumber;
	String enteredNumberString;
	Double enteredNumberDecimal;
	Double activeOperand;
	DoubleBinaryOperator pendingOperation;
	boolean containsDot;
	
	public CalcModeImpl() {

		activeOperand = null;
		enteredNumberString = "";
		enteredNumberDecimal = Double.valueOf(0);
		pendingOperation = null;
		editableModel = true;
		containsDot = false;
		positiveNumber = true;
		
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
		return ((positiveNumber ? 1 : (-1)) * enteredNumberDecimal);
	}

	@Override
	public void setValue(double value) {
		enteredNumberDecimal = value;
		enteredNumberString = String.valueOf(value);
		
		editableModel = false;
	}

	@Override
	public boolean isEditable() {
		return editableModel;
	}

	@Override
	public void clear() {
		enteredNumberString = "";
		enteredNumberDecimal = null;
	}

	@Override
	public void clearAll() {
		enteredNumberString = "";
		enteredNumberDecimal = null;
		activeOperand = null;
		pendingOperation = null;
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
		
		if(containsDot) {
			throw new CalculatorInputException("Decimal point is already in value.");
		}
		
		if("".equals(enteredNumberString)) {
			throw new CalculatorInputException("It is not possible to add decimal point.");
		}
		
		containsDot = true;
		
		// step 1: unboxing
		double dbl = enteredNumberDecimal.doubleValue();

		// step 2: casting
		int intgr = (int) dbl;

		// step 3: boxing
		Integer val = Integer.valueOf(intgr);
		
		enteredNumberString = Integer.valueOf(val) + ".";
		enteredNumberDecimal = Double.valueOf(enteredNumberString);
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editableModel) {
			throw new CalculatorInputException("Model is not editable.");
		}
		
		try {

			if(Double.isInfinite(Double.valueOf(enteredNumberString +
					String.valueOf(digit)))) {
				throw new CalculatorInputException("Number is infinite.");
			}
			
			enteredNumberString = String.valueOf(enteredNumberString + String.valueOf(digit));
			enteredNumberDecimal = Double.valueOf(enteredNumberString);
			
		} catch (Exception e) {
			throw new CalculatorInputException("This digit is not valid value.");
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		if(activeOperand == null) {
			return false;
		}
		return true;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == null) {
			throw new IllegalStateException("Active operand doesn't exists.");
		}
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	@Override
	public String toString() {
		if("NaN".equals(enteredNumberString) || "Infinity".equals(enteredNumberString) ||
				"-Infinity".equals(enteredNumberString)) {
			return enteredNumberString;
		}
		
		if(enteredNumberString.isEmpty() || enteredNumberDecimal == 0.0) {
			return positiveNumber ? "0" : "-0";
		}
		
		String value = "";
		
		try {
			value = Integer.valueOf(enteredNumberString).toString();
		} catch (Exception e) {
			value = Double.valueOf(enteredNumberString).toString();
		}
		
		return (positiveNumber ? "" : "-") + value;
	}
}
	