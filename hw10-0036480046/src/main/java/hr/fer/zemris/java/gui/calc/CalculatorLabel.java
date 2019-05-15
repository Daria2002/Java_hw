package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * This class represents calculator label that implements CalcValueListener
 * because label should change every time when current value change.
 * @author Daria Matković
 *
 */
public class CalculatorLabel extends JLabel implements CalcValueListener {

	/**
	 * This method represents constructor for calculator label, that initialize
	 * color for calculator label.
	 */
	public CalculatorLabel() {
		setOpaque(true);
        setBackground(Color.YELLOW);
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		// step 1: unboxing
		double dbl = model.getValue();
		
		if(!(model.getValue() == Math.floor(model.getValue()) && 
		!Double.isInfinite(model.getValue()))) {
			setText(String.valueOf(model.getValue()));
			return;
		}
		
		// step 2: casting
		int intgr = (int) dbl;

		// step 3: boxing
		Integer val = Integer.valueOf(intgr);
		setText(val.toString());
	}

}
