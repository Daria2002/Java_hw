package hr.fer.zemris.java.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents servlet that reads file.
 * @author Daria Matković
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
//		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
//		
//		// Napravi datoteku ako je potrebno; inače je samo pročitaj...
//		
//		String[] data = readFile(req);
//		
//		if(data == null) {
//			req.getRequestDispatcher("/error.jsp").forward(req, resp);
//		}
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * This method reads file
	 * @param req request
	 * @return string array with data from glasanje-rezultati.txt
	 */
	private String[] readFile(HttpServletRequest req) {
		try {
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
			return data;
		} catch (Exception e) {
			return null;
		}
	}
}