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

public class BarChartDemo extends JFrame {
	
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
	}
	
	public static void main(String[] args) {
		String fileName = args[0];
		List<String> result = new ArrayList<String>();
		
		try {
			result = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			System.out.println("File can't be read");
			System.exit(0);
		}
		
		String xDescription = result.get(0);
		String yDescription = result.get(1);
	
		String[] points = result.get(2).split(" ");
		
		List<XYValue> xyValueList = new ArrayList<XYValue>();
		
		for(String point : points) {
			int commaIndex = point.indexOf(",");
			
			xyValueList.add(new XYValue(Integer.valueOf(point.substring(0, commaIndex)),
					Integer.valueOf(point.substring(commaIndex + 1))));
		}
		
		int yMin = Integer.valueOf(result.get(3));
		int yMax = Integer.valueOf(result.get(4));
		
		int space = Integer.valueOf(result.get(5));
		
		BarChart bc = new BarChart(xyValueList, xDescription, yDescription,
				yMin, yMax, space);
		
		BarChartComponent bcc = new BarChartComponent(bc);	
		BarChartDemo bcd = new BarChartDemo();
		
		bcd.setLayout(new BoxLayout(bcd.getContentPane(), BoxLayout.Y_AXIS));

		JLabel lab = new JLabel(fileName, SwingConstants.CENTER);
		
		JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(lab , BorderLayout.NORTH);
        content.add(bcc, BorderLayout.CENTER);
        
        bcd.setContentPane(content);
        
		SwingUtilities.invokeLater(() -> {bcd.setVisible(true);});
		
	}
}
