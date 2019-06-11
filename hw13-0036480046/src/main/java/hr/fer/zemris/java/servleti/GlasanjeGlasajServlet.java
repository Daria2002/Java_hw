package hr.fer.zemris.java.servleti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents servlet that is called after voting. It refreshes data in
 * glasanje-rezultati file. This class sets attributes with data about name, data and ids.
 * @author Daria Matković
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

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
		
		String key = req.getParameter("id");
		String newValue = map.get(key) != null ? String.valueOf(Integer.valueOf(map.get(key))+1)
				: String.valueOf(1);
		map.put(key, newValue);
		
		req.getSession().setAttribute("map", map);
		
		StringBuilder fileContent = new StringBuilder();
		for(String keyEl:map.keySet()) {
			fileContent.append(keyEl + "\t" + map.get(keyEl) + "\n");
		}
		
		File destFile = new File(fileName);
		FileOutputStream fileStream = new FileOutputStream(destFile, false); // true to append
		                   
		fileStream.write(fileContent.toString().getBytes());
		fileStream.close();
		
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
		Map<String, String> mapIdAndLinks = new HashMap<String, String>();
		
		for(int i = 0; i < data2.length; i++) {
			String[] lineData = data2[i].split("\t");
			mapIdAndNames.put(lineData[0], lineData[1]);
			mapIdAndLinks.put(lineData[0], lineData[2]);
		}
		
		req.getSession().setAttribute("mapIdAndNames", mapIdAndNames);
		req.getSession().setAttribute("mapIdAndLinks", mapIdAndLinks);
		
		// Kad je gotovo, pošalji redirect pregledniku I dalje NE generiraj odgovor
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
