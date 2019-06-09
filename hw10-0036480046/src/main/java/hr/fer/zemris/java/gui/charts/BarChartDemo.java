package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration program for bar chart.
 * @author Daria Matković
 *
 */
public class BarChartDemo extends JFrame {
	
	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method represents constructor for bar chart, that sets properties
	 * and size
	 */
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
	}
	
	/**
	 * This method represents main method that runs drawing bar chart graph
	 * for data from given file
	 * @param args path to file where data for bar chart is written.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Enter one argument that represents file name");
			System.exit(0);
		}
		
		String fileName = args[0];
		List<String> result = read(fileName);
		
		String xDescription = result.get(0);
		String yDescription = result.get(1);
		
		int yMin = Integer.valueOf(result.get(3));
		int yMax = Integer.valueOf(result.get(4));
		int space = Integer.valueOf(result.get(5));

		JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(new JLabel(fileName, SwingConstants.CENTER), BorderLayout.NORTH);
        content.add(new BarChartComponent(new BarChart(parse(result.get(2).split(" ")),
        		xDescription, yDescription, yMin, yMax, space)), BorderLayout.CENTER);
        
        BarChartDemo bcd = new BarChartDemo();
		bcd.setLayout(new BoxLayout(bcd.getContentPane(), BoxLayout.Y_AXIS));
        bcd.setContentPane(content);
        
		SwingUtilities.invokeLater(() -> {bcd.setVisible(true);});
		
	}
	
	private static List<String> read(String fileName) {
		List<String> result = new ArrayList<String>();
		
		try {
			result = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			System.out.println("File can't be read");
			System.exit(0);
		}
		
		return result;
	}
	
	private static List<XYValue> parse(String[] points) {
		List<XYValue> xyValueList = new ArrayList<XYValue>();
		
		for(String point : points) {
			int commaIndex = point.indexOf(",");
			
			xyValueList.add(new XYValue(Integer.valueOf(point.substring(0, commaIndex)),
					Integer.valueOf(point.substring(commaIndex + 1))));
		}
		
		return xyValueList;
	}
} 
