package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class GlasanjeGrafikaServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("image/png");

		JFreeChart chart = getChart(request);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsPNG(bos, chart, 500, 270);
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(bos.toByteArray());
		os.close();
		request.setAttribute("/glasanje-grafika", bos.toByteArray());
	}

	public JFreeChart getChart(HttpServletRequest req) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		int totalSum = 0;
		Map<String, String> points = readPointsAndIds(req);
		Map<String, String> names = readNamesAndIds(req);
		
		for(String id:points.keySet()) {
			totalSum += Integer.valueOf(points.get(id));
		}
		
		for(String id:points.keySet()) {
			dataset.setValue(names.get(id), Integer.valueOf(points.get(id)));
		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Votes", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
	
	// returns map, where key is id and values are points
	private Map<String, String> readPointsAndIds(HttpServletRequest req) {
		try {
			String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
			// Napravi datoteku ako je potrebno; ažuriraj podatke koji su u njoj...
			
			BufferedReader abc;
			
				abc = new BufferedReader(new FileReader(fileName));
			
			List<String> lines = new ArrayList<String>();
		
			String line = abc.readLine();
			while(line != null) {
			    lines.add(line);
			    line = abc.readLine();
			}
			abc.close();
		
			// If you want to convert to a String[]
			String[] data = lines.toArray(new String[]{});
			
			Map<String, String> map = new HashMap<String, String>();
			for(int i = 0;  i < data.length; i++) {
				String[] row = data[i].split("\t");
				map.put(row[0], row[1]);
			}
			
			return map;
		} catch (Exception e) {
			return null;
		}
	}
	
	private Map<String, String> readNamesAndIds(HttpServletRequest req) {
		try {
			String fileName2 = req.getServletContext()
					.getRealPath("/WEB-INF/glasanje-definicija.txt");
			
			BufferedReader abc2 = new BufferedReader(new FileReader(fileName2));
			List<String> lines2 = new ArrayList<String>();

			String line2 = abc2.readLine();
			while(line2 != null) {
			    lines2.add(line2);
			    line2 = abc2.readLine();
			}
			abc2.close();

			// If you want to convert to a String[]
			String[] data2 = lines2.toArray(new String[]{});
			
			Map<String, String> mapIdAndNames = new HashMap<String, String>();
			
			for(int i = 0; i < data2.length; i++) {
				String[] lineData = data2[i].split("\t");
				mapIdAndNames.put(lineData[0], lineData[1]);
			}
			
			return mapIdAndNames;
			
		} catch (Exception e) {
			return null;
		}
	}
}
