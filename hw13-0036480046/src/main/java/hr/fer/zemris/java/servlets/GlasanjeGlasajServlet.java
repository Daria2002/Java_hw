package hr.fer.zemris.java.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlasanjeGlasajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Zabiljezi glas...
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		// Napravi datoteku ako je potrebno; ažuriraj podatke koji su u njoj...
		
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
		
		// map where ids are keys and values are bend points
		Map<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i < data.length; i++) {
			String[] lineData = data[i].split("\t");
			
			map.put(lineData[0], lineData[1]);
		}
		
		// take ide 
		String key = req.getParameter("id");
		if(map.containsKey(key)) {
			map.put(key, map.get(key) + 1);
		}
		
		Map<String, String> mapNamesAndId = (Map<String, String>)req.getAttribute("mapNamesAndId");
		
		req.setAttribute("mapNamesAndId", mapNamesAndId);
		req.setAttribute("map", map);
		
		StringBuilder fileContent = new StringBuilder();
		for(String keyEl:map.keySet()) {
			fileContent.append(keyEl + "\t" + map.get(keyEl) + "\n");
		}
		
		File destFile = new File(fileName);
		FileOutputStream fileStream = new FileOutputStream(destFile, false); // true to append
		                   
		fileStream.write(fileContent.toString().getBytes());
		fileStream.close();
		
		// Kad je gotovo, pošalji redirect pregledniku I dalje NE generiraj odgovor
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
