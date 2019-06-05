package hr.fer.zemris.java.servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GlasanjeXlsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		// Napravi datoteku ako je potrebno; inače je samo pročitaj...
		
		String[] data = readFile(req, fileName);
		createExelFile(req, resp, data);
	}

	private void createExelFile(HttpServletRequest request, HttpServletResponse response, String[] data) {
		try{
			HSSFWorkbook hwb = new HSSFWorkbook();
			
			HSSFSheet sheet =  hwb.createSheet("sheet");
	
			for(int i = 0; i < data.length; i++) {
				String[] row = data[i].split("\t");
				
 				HSSFRow rowhead = sheet.createRow((short)i);
				rowhead.createCell((short) 0).setCellValue(row[0]);
				rowhead.createCell((short) 1).setCellValue(row[1]);
			}
			
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=glasanje.xls");
			hwb.write(response.getOutputStream());
			response.getOutputStream().flush();
			hwb.close();
	
		} catch ( Exception ex ) {
		    System.out.println(ex);
	
		}
	}
	
	private String[] readFile(HttpServletRequest req, String fileName) {
		try {
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