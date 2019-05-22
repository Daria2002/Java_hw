package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Form localization provider that implements LocalizationProviderBridge
 * @author Daria Matković
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * This method represents constructor that initialize provider and frame
	 * @param provider provider
	 * @param frame frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, 
			JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}
}
