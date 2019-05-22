package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JLabel;

public class LJLabel extends JLabel {

	String key;
	ILocalizationProvider lp;

	public LJLabel(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		
		this.setText(translation);
		//this.putValue(Action.NAME, translation);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				
				if("length".equals(key) || "col".equals(key) || "ln".equals(key) || "sel".equals(key)) {
					String str = LJLabel.this.getText().split(":")[1].trim();
					LJLabel.this.setText(translation + ": " + str);
					
				} else {
					LJLabel.this.setText(translation);
				}
			}
		});
	}
	
	public String getLocalizedText() {
		return lp.getString(key);
	}
	
}
