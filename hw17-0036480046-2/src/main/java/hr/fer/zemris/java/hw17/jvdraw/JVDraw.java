package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
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

/**
 * This class represents frame for paint
 * @author Daria Matković
 *
 */
public class JVDraw extends JFrame {
	/** selected tool */
	private Tool selectedTool;
	/** implementation of drawing model */
	private MyDrawingModel mdm = new MyDrawingModel();
	/** line tool */
	private Tool lineTool;
	/** circle tool */
	private Tool circleTool;
	/** filled circle tool */
	private Tool filledCircleTool;
	/** drawing canvas */
	private JDrawingCanvas jDrawingCanvas;
	/** file to save */
	private File fileToSave = null;
	
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

	/**
	 * This method is executed when program is run
	 * @param args
	 */
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
        openMI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mdm.clear();
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(JVDraw.this);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    try {
						List<String> list = Files.readAllLines(
								selectedFile.toPath(), Charset.defaultCharset());

						makeFileObjects(list);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

			private void makeFileObjects(List<String> list) {
				for(String line:list) {
					String[] params = line.split(" ");
					if("LINE".equals(params[0])) {
						mdm.add(new Line(Integer.valueOf(params[1]),
								Integer.valueOf(params[2]),
								Integer.valueOf(params[3]),
								Integer.valueOf(params[4]),
								new Color(Integer.valueOf(params[5]), 
										Integer.valueOf(params[6]),
										Integer.valueOf(params[7]))));
						
					} else if("CIRCLE".equals(params[0])) {
						mdm.add(new Circle(Integer.valueOf(params[1]),
								Integer.valueOf(params[2]),
								Integer.valueOf(params[3]),
								new Color(Integer.valueOf(params[4]), 
										Integer.valueOf(params[5]),
										Integer.valueOf(params[6]))));
						
						
					} else if("FCIRCLE".equals(params[0])) {
						mdm.add(new FilledCircle(Integer.valueOf(params[1]),
								Integer.valueOf(params[2]),
								Integer.valueOf(params[3]),
								new Color(Integer.valueOf(params[4]), 
										Integer.valueOf(params[5]),
										Integer.valueOf(params[6])),
								new Color(Integer.valueOf(params[7]), 
										Integer.valueOf(params[8]),
										Integer.valueOf(params[9]))));
						
					}
				}
			}
		});
        
        fileMenu.addSeparator();
        
        JMenuItem saveMI = fileMenu.add(new JMenuItem("Save"));
        saveMI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					save(fileToSave);
					mdm.clearModifiedFlag();
				} catch (Exception e2) {
					saveAs();
					mdm.clearModifiedFlag();
				}
			}
		});
        
        JMenuItem saveAsMI = fileMenu.add(new JMenuItem("Save As ..."));
        saveAsMI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
				mdm.clearModifiedFlag();
			}
		});
        
        fileMenu.addSeparator();
        
        JMenuItem exportMI = fileMenu.add(new JMenuItem("Export"));
        exportMI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Rectangle box = jDrawingCanvas.getBoundingBox();
				BufferedImage image = null;
				
				try {
					image = new BufferedImage(box.width,
							box.height, BufferedImage.TYPE_3BYTE_BGR);
				} catch (Exception x) {
					image = new BufferedImage(Math.abs(box.width),
							Math.abs(box.height),
							BufferedImage.TYPE_3BYTE_BGR);
				}
				
				Graphics2D g = image.createGraphics();
				g.translate(-box.x, -box.y);
				
				GeometricalObjectPainter gop = new GeometricalObjectPainter(g);
				
				for(int i = 0; i < JVDraw.this.mdm.getSize(); i++) {
					if(mdm.getObject(i).getClass() == Line.class) {
						gop.visit((Line)mdm.getObject(i));
					} else if(mdm.getObject(i).getClass() == Circle.class) {
						gop.visit((Circle)mdm.getObject(i));
					} else if(mdm.getObject(i).getClass() == FilledCircle.class) {
						gop.visit((FilledCircle)mdm.getObject(i));
					} 
				}
				
				g.dispose();
				if(export(image)) {
					JOptionPane.showMessageDialog(JVDraw.this, "Image exported.");
				}
			}
		});
        
        fileMenu.addSeparator();
        
        JMenuItem exitMI = fileMenu.add(new JMenuItem("Exit"));
        exitMI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mdm.isModified()) {
					int result = JOptionPane.showConfirmDialog(JVDraw.this,
							"Do you want to save changes before exit?", 
						      "question",      
						      JOptionPane.YES_NO_CANCEL_OPTION, 
						      JOptionPane.QUESTION_MESSAGE);
					
					if(result == JOptionPane.YES_OPTION) {
						if(saveAs()) {
							System.exit(0);
						}
						return;
					} else if(result == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				System.exit(0);
			}
		});
        
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
	
	private boolean export(BufferedImage image) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		 
		
		FileNameExtensionFilter filterPng = new FileNameExtensionFilter(
				".png", "png");
		fileChooser.addChoosableFileFilter(filterPng);
		
		FileNameExtensionFilter filterJpg = new FileNameExtensionFilter(
				".jpg", "jpg");
		fileChooser.addChoosableFileFilter(filterJpg);
		
		FileNameExtensionFilter filterGif = new FileNameExtensionFilter(
				".gif", "gif");
		fileChooser.addChoosableFileFilter(filterGif);
		
		fileChooser.setAcceptAllFileFilterUsed(false);

		int userSelection = fileChooser.showSaveDialog(JVDraw.this);
		
		if(userSelection == JFileChooser.CANCEL_OPTION) {
			return false;
		}
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    System.out.println("type:"+fileChooser.getTypeDescription(file));
		    String filePath = file.getAbsolutePath();
		    
			try {
				System.out.println("ekst:"+getExtension(filePath));
				
				String extension = getExtension(filePath);
				if(extension == null) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Please enter extension in file name, e.g. fileName.extension");
					export(image);
					return true;
				}
				
				ImageIO.write(image, getExtension(filePath), file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return true;
		}
		return false;
	}
	
	
	private boolean saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		 
		int userSelection = fileChooser.showSaveDialog(JVDraw.this);
		 
		if(userSelection == JFileChooser.CANCEL_OPTION) {
			return false;
		}
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    fileToSave = fileChooser.getSelectedFile();
		    
		    String filePath = fileToSave.getAbsolutePath();
		    if(!isExtensionJVD(filePath)) {
		    	int result = JOptionPane.showConfirmDialog(JVDraw.this,
		    			"Selected file doesn't have .jvd extension. Press Cancel"
		    			+ " if you want to cancel saving, and Ok to continue saving.",
		    			"info", JOptionPane.OK_CANCEL_OPTION,
		    			JOptionPane.INFORMATION_MESSAGE);
		    	if(result == JOptionPane.CANCEL_OPTION) {
		    		return false;
		    	}
		    	if(!saveAs()) {
		    		return false;
		    	}
		    }
		    
		    save(fileToSave);
		    return true;
		}
		return false;
	}
	
	/**
	 * Method that saves file 
	 * @param fileToSave file to save
	 */
	private void save(File fileToSave) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(fileToSave.getAbsolutePath());
			out.write(jDrawingCanvas.getTextFile().getBytes(Charset.forName("UTF-8")));
		    out.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Error occurred while saving");
		}
	}
	
	/**
	 * checks that extension is jvd
	 * @param absolutePath
	 * @return
	 */
	private boolean isExtensionJVD(String absolutePath) {
	    return "jvd".equals(getExtension(absolutePath));
	}

	/**
	 * gets extension
	 * @param name file name
	 * @return extension name
	 */
	private String getExtension(String name) {
		System.out.println("name:"+name);
		String extension = "";

	    int i = name.lastIndexOf('.');
	    if (i > 0) {
	        extension = name.substring(i+1);
	    }
	    
	    if(extension.isEmpty() || extension.isBlank()) {
	    	return null;
	    }
	    
	    return extension;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}
	
	/**
	 * Method for editing
	 * @param goe Geometrical object editor
	 */
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
	
	/**
	 * This method makes info text
	 * @param fgColorArea foreground color area
	 * @param bgColorArea background color area
	 * @return info text
	 */
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