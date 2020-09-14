package hr.fer.zemris.java.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CitajFileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ClassLoader cl = this.getClass().getClassLoader();
		String fileName = "test.txt";
		File file = new File(cl.getResource(fileName).getFile());
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = "";
		StringBuilder fileContent =  new StringBuilder();
		while((line = br.readLine()) != null) {
			fileContent.append(line);
			fileContent.append("\n");
		}
		
		req.getSession().setAttribute("fileContent", fileContent.toString());
		req.getRequestDispatcher("/citajFile.jsp").forward(req, resp);
	}
	
}
