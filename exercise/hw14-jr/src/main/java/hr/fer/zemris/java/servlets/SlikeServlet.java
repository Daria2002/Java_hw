package hr.fer.zemris.java.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SlikeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
			String imagePath = req.getParameter("path");
			resp.setContentType("image/jpg");
			OutputStream os = resp.getOutputStream();
			createImage(imagePath, os);
	}
	
	private void createImage(String imagePath, OutputStream os) throws IOException {
		FileInputStream fis = new FileInputStream(new File(imagePath));
		byte[] buff = new byte[4096];
		int len;
		while(true) {
			len = fis.read(buff);
			if(len < 0)
				break;
			os.write(buff, 0, len);
		}
		os.close();
	}

	private byte[] imageToByteArray(String imagePath) throws IOException {
		return Files.readAllBytes(new File(imagePath).toPath());
	}
	
}
