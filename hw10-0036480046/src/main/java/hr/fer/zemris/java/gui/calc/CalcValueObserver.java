package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

public class CalcValueObserver implements CalcValueListener {

	Object value;
	
	@Override
	public void valueChanged(CalcModel model) {
		
		value = model.getValue();
		
	}

}
