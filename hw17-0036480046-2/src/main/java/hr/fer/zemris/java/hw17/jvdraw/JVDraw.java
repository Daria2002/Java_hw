package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


public class JVDraw extends JFrame {
	
	private Tool selectedTool;
	private MyDrawingModel mdm = new MyDrawingModel();
	private Tool lineTool;
	private Tool circleTool;
	private Tool filledCircleTool;
	private JDrawingCanvas jDrawingCanvas;
	
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
		
		JColorArea fgColorArea = new JColorArea(Color.RED);
		JColorArea bgColorArea = new JColorArea(Color.YELLOW);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		jDrawingCanvas = new JDrawingCanvas(new Supplier<Tool>() {
			
			@Override
			public Tool get() {
				return selectedTool;
			}
		}, mdm);
		
		jDrawingCanvas.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(selectedTool != null) {
					selectedTool.mouseMoved(e);
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(selectedTool != null) {
					selectedTool.mouseDragged(e);
				}
			}
		});
		
		jDrawingCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(selectedTool != null) {
					selectedTool.mouseReleased(e);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(selectedTool != null) {
					selectedTool.mousePressed(e);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedTool != null) {
					selectedTool.mouseClicked(e);
				}
			}
		});
		
		mdm.addDrawingModelListener(jDrawingCanvas);
		
		panel.add(jDrawingCanvas, BorderLayout.CENTER);
		
		
		DrawingObjectListModel dolm = new DrawingObjectListModel(mdm);
		JList<GeometricalObject> jList = new JList<GeometricalObject>(dolm);
		
		jList.addKeyListener(new KeyAdapter() {
			
			@Override
	        public void keyPressed(KeyEvent ke) {
				GeometricalObject selectedObject = jList.getSelectedValue();
				
				if(selectedObject != null) {
					if(ke.getKeyCode() == KeyEvent.VK_DELETE) {
		                mdm.remove(selectedObject);
		                
		            } else if(ke.getKeyCode() == KeyEvent.VK_PLUS) {
		                selectedObject.accept(new GeometricalObjectVisitor() {
							
							@Override
							public void visit(FilledCircle filledCircle) {
								filledCircle.centerY--;
							}
							
							@Override
							public void visit(Circle circle) {
								circle.setCenterY(circle.getCenterY()-1);
							}
							
							@Override
							public void visit(Line line) {
								line.setY0(line.getY0()-1);
								line.setY1(line.getY1()-1);
							}
						});
		                
		            }  else if(ke.getKeyCode() == KeyEvent.VK_MINUS) {
		                selectedObject.accept(new GeometricalObjectVisitor() {
							
							@Override
							public void visit(FilledCircle filledCircle) {
								filledCircle.centerY++;
							}
							
							@Override
							public void visit(Circle circle) {
								circle.setCenterY(circle.getCenterY()+1);
							}
							
							@Override
							public void visit(Line line) {
								line.setY0(line.getY0()+1);
								line.setY1(line.getY1()+1);
							}
						});
		            }
				}
	        }
		});
		
		jList.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int numberOfClicks = e.getClickCount();
				if(numberOfClicks == 2) {
					GeometricalObject go = jList.getSelectedValue();
					GeometricalObjectEditor goe = go.createGeometricalObjectEditor();
					editing(goe);
				}
			}
		});
		
		JScrollPane jsp = new JScrollPane(jList);
		
		panel.add(jsp, BorderLayout.EAST);
		
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
		
		lineTool = new LineTool(mdm, fgColorArea, jDrawingCanvas);
		circleTool = new CircleTool(mdm, jDrawingCanvas, fgColorArea);
		filledCircleTool = new FilledCircleTool(bgColorArea, fgColorArea, jDrawingCanvas, mdm);
		
		JToggleButton lineButton = new JToggleButton();
		lineButton.add(new JLabel("Line"));
		lineButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JVDraw.this.selectedTool = lineTool;
			}
		});
		
		JToggleButton circleButton = new JToggleButton();
		circleButton.add(new JLabel("Circle"));
		circleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JVDraw.this.selectedTool = circleTool;
			}
		});
		
		JToggleButton filledCircleButton = new JToggleButton();
		filledCircleButton.add(new JLabel("Filled circle"));
		filledCircleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JVDraw.this.selectedTool = filledCircleTool;
			}
		});
		
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
		
		cp.add(panel, BorderLayout.CENTER);
		cp.add(tb, BorderLayout.PAGE_START);
		
		cp.add(bottomColorInfo, BorderLayout.PAGE_END);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	
	private void editing(GeometricalObjectEditor goe) {

		int result = JOptionPane.showConfirmDialog(JVDraw.this, goe,
				"Edit", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			try {
				goe.checkEditing();
				
				try {
					goe.acceptEditing();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(goe, e2.getMessage(), 
							"accept failed", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(goe, e2.getMessage(),
						"wrong parameters", JOptionPane.ERROR_MESSAGE);
				editing(goe);
			}
		
		}
	}
	
	private String makeInfoText(JColorArea fgColorArea, JColorArea bgColorArea) {
		return "Foreground color: (" +
				fgColorArea.getCurrentColor().getRed() + ", " +
				fgColorArea.getCurrentColor().getGreen() + ", " +
				fgColorArea.getCurrentColor().getBlue() + "), background color: (" +
				bgColorArea.getCurrentColor().getRed() + ", " +
				bgColorArea.getCurrentColor().getGreen() + ", " +
				bgColorArea.getCurrentColor().getBlue() + ").";
	}
}