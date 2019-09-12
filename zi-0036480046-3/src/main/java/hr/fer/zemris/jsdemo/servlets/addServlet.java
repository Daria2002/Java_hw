package hr.fer.zemris.jsdemo.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.IdentifierProjection;

public class addServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String ime = req.getParameter("ime");
//		if(!ime.endsWith(".jvd")) {
//
//			req.getRequestDispatcher("main.jsp").forward(req, resp);
//		}else {
		
		
		//String path = "/home/daria/eclipse-workspace/my-hw/zi-0036480046-3/src/main/webapp/WEB-INF/images/" + req.getParameter("ime");
//		File f = new File(path);
//		save(f ,req.getParameter("txt"));
//		
//		req.setAttribute("lista", citaj());
//		
//		req.getRequestDispatcher("show.jsp").forward(req, resp);
		}
	//}
	
	private List<String> citaj() {
		String path = "/home/daria/eclipse-workspace/my-hw/zi-0036480046-3/src/main/webapp/WEB-INF/images/";

		File f = new File(path);
		List<String> fi = new ArrayList<String>();
		 for (final File fileEntry : f.listFiles()) {
		        if (!fileEntry.isDirectory()) {
		        	fi.add(fileEntry.getName());
		        }
		    }
		
		return fi;
	}
	
	private void save(File fileToSave, String text) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(fileToSave.getAbsolutePath());
			out.write(text.getBytes(Charset.forName("UTF-8")));
		    out.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Error occurred while saving");
		}
	}
}
