package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

public class StatusJLabel extends JLabel {

	String key;
	ILocalizationProvider lp;
	String[] words;

	public StatusJLabel(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		words = translation.split(",");
		
		this.setText(translation);
		//this.putValue(Action.NAME, translation);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				words = translation.split(",");
				
				if("statusInfo".equals(key)) {
					String ln = StatusJLabel.this.getText().split(":")[1].trim().split(" ")[0];
					String col = StatusJLabel.this.getText().split(":")[2].trim().split(" ")[0];
					String sel = StatusJLabel.this.getText().split(":")[3].trim().split(" ")[0];
					StatusJLabel.this.setText(words[0] + ": " + ln + " " + words[1] + ": " + col + " "
							+ words[2] + ": " + sel);
					
				} else {
					StatusJLabel.this.setText(translation);
				}
			}
		});
	}
	
	public String getLocalizedLn() {
		return words[0];
	}
	
	public String getLocalizedCol() {
		return words[1];
	}
	
	public String getLocalizedSel() {
		return words[2];
	}
}
