package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


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
		
		
		
		JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
		JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        JMenuItem openMI = fileMenu.add(new JMenuItem("Open"));
        fileMenu.addSeparator();
        JMenuItem saveMI = fileMenu.add(new JMenuItem("Save"));
        JMenuItem saveAsMI = fileMenu.add(new JMenuItem("Save As ..."));
        fileMenu.addSeparator();
        JMenuItem exportMI = fileMenu.add(new JMenuItem("Export"));
        fileMenu.addSeparator();
        JMenuItem exitMI = fileMenu.add(new JMenuItem("Exit"));
        
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		tb.add(fgColorArea);
		tb.add(bgColorArea);
		tb.add(lineButton);
		tb.add(circleButton);
		tb.add(filledCircleButton);
		
		cp.add(tb, BorderLayout.PAGE_START);
		//cp.add(panel, BorderLayout.CENTER);
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