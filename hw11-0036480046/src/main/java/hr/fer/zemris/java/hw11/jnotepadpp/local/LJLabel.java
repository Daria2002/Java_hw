package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * This class represents localized label
 * @author Daria Matković
 *
 */
public class LJLabel extends JLabel {
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/** key **/
	String key;
	/** localization provider **/
	ILocalizationProvider lp;

	/**
	 * Constructor for LJabel that initalize key and ILocalization provider
	 * @param key key 
	 * @param lp provider
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		
		this.setText(translation);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				
				if("length".equals(key)) {
					String str = LJLabel.this.getText().split(":")[1].trim();
					LJLabel.this.setText(translation + ": " + str);
					
				} else {
					LJLabel.this.setText(translation);
				}
			}
		});
	}
	
	/**
	 * This method gets translated text
	 * @return translated text
	 */
	public String getLocalizedText() {
		return lp.getString(key);
	}
}
