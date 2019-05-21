package hr.fer.zemris.java.hw11.jnotepadpp.local;

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
		String description = lp.getString("desc-" + key);
		
		this.putValue(Action.NAME, translation);
		this.putValue(Action.SHORT_DESCRIPTION, description);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				String description = lp.getString("desc-" + key);
				LocalizableAction.this.putValue(Action.NAME, translation);
				LocalizableAction.this.putValue(Action.SHORT_DESCRIPTION, description);
			}
		});
	}
	
}
