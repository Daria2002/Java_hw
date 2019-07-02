package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class JVDraw extends JFrame {
	
	/**
     * Constructor that is used for initializing window size, location and title
     */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(10, 10);
		setSize(700, 500);
		setTitle("JVDraw");
		
		initGUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{new JVDraw().setVisible(true);});
	}
	
	/**
	 * This method initialize gui components
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		
		JColorArea fgColorArea = new JColorArea(Color.RED);
		JColorArea bgColorArea = new JColorArea(Color.YELLOW);
		
		fgColorArea.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				fgColorArea.setForeground(newColor);
			}
		});
		
		bgColorArea.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				bgColorArea.setForeground(newColor);
			}
		});
		
		JToggleButton lineButton = new JToggleButton();
		lineButton.add(new JLabel("Line"));
		JToggleButton circleButton = new JToggleButton();
		circleButton.add(new JLabel("Circle"));
		JToggleButton filledCircleButton = new JToggleButton();
		filledCircleButton.add(new JLabel("Filled circle"));
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(lineButton);
		bg.add(circleButton);
		bg.add(filledCircleButton);
		
		ColorInfoLabel bottomColorInfo = new ColorInfoLabel(fgColorArea, bgColorArea);
		
		fgColorArea.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				bottomColorInfo.setText(makeInfoText(fgColorArea, bgColorArea));
			}
		});
		
		bgColorArea.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				bottomColorInfo.setText(makeInfoText(fgColorArea, bgColorArea));
			}
		});
		
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");
		mb.add(menu);
		setJMenuBar(mb);
		
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		tb.add(fgColorArea);
		tb.add(bgColorArea);
		tb.add(lineButton);
		tb.add(circleButton);
		tb.add(filledCircleButton);
		
		cp.add(tb, BorderLayout.PAGE_START);
		cp.add(panel, BorderLayout.CENTER);
		cp.add(bottomColorInfo, BorderLayout.PAGE_END);
	}
	
	private String makeInfoText(JColorArea fgColorArea, JColorArea bgColorArea) {
		return "Foreground color: (" +
				fgColorArea.getCurrentColor().getRed() + ", " +
				fgColorArea.getCurrentColor().getBlue() + ", " +
				fgColorArea.getCurrentColor().getGreen() + "), background color: (" +
				bgColorArea.getCurrentColor().getRed() + ", " +
				bgColorArea.getCurrentColor().getGreen() + ", " +
				bgColorArea.getCurrentColor().getBlue() + ").";
	}
}