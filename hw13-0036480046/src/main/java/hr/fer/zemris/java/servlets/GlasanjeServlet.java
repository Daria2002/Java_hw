package hr.fer.zemris.java.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlasanjeServlet extends HttpServlet {

	private static class bendData {
		private Integer id;
		private String bendName;
		private String link;
		
		public bendData(Integer id, String bendName, String link) {
			super();
			this.id = id;
			this.bendName = bendName;
			this.link = link;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Učitaj raspoložive bendove
		String fileName = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		BufferedReader abc = new BufferedReader(new FileReader(fileName));
		List<String> lines = new ArrayList<String>();

		String line = abc.readLine();
		while(line != null) {
		    lines.add(line);
		    line = abc.readLine();
		}
		abc.close();

		// If you want to convert to a String[]
		String[] data = lines.toArray(new String[]{});
		
		String[] names = new String[data.length];
		String[] ids = new String[data.length];

		Map<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i < data.length; i++) {
			String[] lineData = data[i].split("\t");
			map.put(lineData[0], lineData[1]);
		}
		req.setAttribute("map", map);
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
		.forward(req, resp);
	}
}
