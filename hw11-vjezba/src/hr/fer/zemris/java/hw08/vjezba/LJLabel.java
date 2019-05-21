package hr.fer.zemris.java.hw08.vjezba;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JLabel;

public class LJLabel extends JLabel {

	String key;
	ILocalizationProvider lp;

	public LJLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		
		this.setText(translation);
		//this.putValue(Action.NAME, translation);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				LJLabel.this.setText(translation);
			}
		});
	}
	
}
