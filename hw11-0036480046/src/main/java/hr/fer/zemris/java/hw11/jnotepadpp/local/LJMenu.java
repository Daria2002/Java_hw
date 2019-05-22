package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * LJMenu class that represents localized menu
 * @author Daria Matković
 *
 */
public class LJMenu extends JMenu {
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/** key **/
	String key;
	/** provider **/
	ILocalizationProvider lp;

	/**
	 * LJMenu constructor that initialize key and provider
	 * @param key key 
	 * @param lp provider
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.lp = lp;
		String translation = lp.getString(key);
		
		this.setText(translation);
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				LJMenu.this.setText(translation);
			}
		});
	}
}
