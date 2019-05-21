package hr.fer.zemris.java.hw08.vjezba;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {

	String key;
	ILocalizationProvider lp;
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		
		this.putValue(Action.NAME, translation);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				LocalizableAction.this.putValue(Action.NAME, translation);
			}
		});
	}
	
}
