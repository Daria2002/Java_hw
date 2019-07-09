package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * This class represents status label that is used for displaying current caret line,
 * column and selected length 
 * @author Daria Matković
 *
 */
public class StatusJLabel extends JLabel {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/** key **/
	String key;
	/** ILocalizationProvider **/
	ILocalizationProvider lp;
	/** array of translated words **/
	String[] words;

	/**
	 * Constructor that initialize key and ILocalizationProvider. It also adds 
	 * ILocalizationListener to ILocalizationProvider
	 * @param key key
	 * @param lp ILocalizationProvider
	 */
	public StatusJLabel(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		words = translation.split(",");
		
		this.setText(translation);
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
	
	/**
	 * Gets translation for line
	 * @return translated word for line
	 */
	public String getLocalizedLn() {
		return words[0];
	}
	
	/**
	 * Gets translation for column
	 * @return translated word for column
	 */
	public String getLocalizedCol() {
		return words[1];
	}
	
	/**
	 * Gets translation for selected
	 * @return translation for selected
	 */
	public String getLocalizedSel() {
		return words[2];
	}
}
