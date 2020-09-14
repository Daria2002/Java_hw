package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.Unos;

/**
 * Ova klasa se koristi za stvaranje pie chart
 * @author Daria Matković
 *
 */
public class StatistikaDB extends HttpServlet {

	// nacrtaj stupcasti i okrugli dijagram
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		JFreeChart chart = getChart(req, resp);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsPNG(bos, chart, 500, 270);
		OutputStream os = new BufferedOutputStream(resp.getOutputStream());
		os.write(bos.toByteArray());
		os.close();
		
		req.setAttribute("servleti/statistikaDBgraf", bos.toByteArray());
		//req.getRequestDispatcher("/statDB.jsp").forward(req, resp);
	}
	
	public JFreeChart getChart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		SQLDAO sqldao = new SQLDAO();
		Map<String, Integer> map = sqldao.getAllUsers();
		
		for(String key:map.keySet()) {
			dataset.setValue(key, map.get(key));
		}
		
		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Korisnici", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
