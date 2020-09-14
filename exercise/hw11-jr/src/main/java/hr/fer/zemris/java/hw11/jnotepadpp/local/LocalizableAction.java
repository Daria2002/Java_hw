package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class represents localizable action that extends abstract action
 * @author Daria Matković
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/** key **/
	String key;
	/** provider **/
	ILocalizationProvider lp;
	
	/**
	 * Constructor that initialize key and provider
	 * @param key key 
	 * @param lp provider
	 */
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
