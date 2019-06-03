package hr.fer.zemris.java.p11.servleti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ReportServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("image/png");

		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		request.getSession().setAttribute("image", chart);
		/*
		OutputStream outputStream = response.getOutputStream();
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
		//response.getOutputStream().close(); 
		//request.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(request, response);
		outputStream.flush();
		outputStream.close();
		*/
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ChartUtilities.writeChartAsPNG(bos, chart, 500, 270);
		request.getSession().setAttribute("im", bos);
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(bos.toByteArray());
		os.flush();
		os.close();
		request.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(request, response);
	}

	public JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Ford", 23.3);
		dataset.setValue("Chevy", 32.4);
		dataset.setValue("Yugo", 44.2);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Cars", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
