package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThumbnailGeneratorServlet  extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String imageName = req.getParameter("imageName");
		String path = req.getServletContext().getRealPath("/WEB-INF/");
		resp.setContentType("image/jpg");
		
		ImagesDB.createThumbnail(imageName, resp.getOutputStream(), path);
	}
}
