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

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.Unos;

/**
 * This class generates pie chart for votes. This bar chart displays in /webapp2/glasanje-rezultati
 * after voting.
 * @author Daria Matković
 *
 */
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("image/png");

		JFreeChart chart = getChart(request, response);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsPNG(bos, chart, 500, 270);
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(bos.toByteArray());
		os.close();
		request.setAttribute("/glasanje-grafika", bos.toByteArray());
	}

	/**
	 * This method is used for generating and setting pie chart
	 * @param req request
	 * @param resp response
	 * @return chart
	 * @throws ServletException exception 
	 * @throws IOException exception
	 */
	public JFreeChart getChart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		Map<Long, Long> points = readPointsAndIds(req);
		Map<Long, String> names = readNamesAndIds(req);
		
		if(points == null || names == null) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
		}
		
		for(Long id:points.keySet()) {
			dataset.setValue(names.get(id), Long.valueOf(points.get(id)));
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
	
	/**
	 * This method reads file glasanje-rezultati.txt and makes map with points and ids
	 * @param req request
	 * @return returns map, where key is id and values are points
	 */
	private Map<Long, Long> readPointsAndIds(HttpServletRequest req) {
		try {
			Map<Long, Long> mapIdAndVotes = new HashMap<Long, Long>();
			
			SQLDAO sqlDao = new SQLDAO();
			List<Poll> polls = sqlDao.getDefinedPolls();
			
			for(int i = 0; i < polls.size(); i++) {
				Long pollId = polls.get(i).getPollId();
				
				List<Unos> entries = sqlDao.getOptions(pollId);
				for(int j = 0; j < entries.size(); j++) {
					mapIdAndVotes.put(entries.get(j).getId(), entries.get(j).getVotes());
				}	
			}
			
			return mapIdAndVotes;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * This method reads glasanje-definicija.txt file and makes map with names and ids
	 * @param req request
	 * @return returns map, where key is id and values are names
	 */
	private Map<Long, String> readNamesAndIds(HttpServletRequest req) {
		try {
			Map<Long, String> mapIdAndNames = new HashMap<Long, String>();
			
			SQLDAO sqlDao = new SQLDAO();
			List<Poll> polls = sqlDao.getDefinedPolls();
			
			for(int i = 0; i < polls.size(); i++) {
				Long pollId = polls.get(i).getPollId();
				
				List<Unos> entries = sqlDao.getOptions(pollId);
				for(int j = 0; j < entries.size(); j++) {
					mapIdAndNames.put(entries.get(j).getId(), entries.get(j).getTitle());
				}	
			}
			
			return mapIdAndNames;
			
		} catch (Exception e) {
			return null;
		}
	}
}
