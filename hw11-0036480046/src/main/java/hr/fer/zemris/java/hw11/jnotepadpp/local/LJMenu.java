package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

public class LJMenu extends JMenu {

	String key;
	ILocalizationProvider lp;

	public LJMenu(String key, ILocalizationProvider lp) {
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
				LJMenu.this.setText(translation);
			}
		});
	}
}
