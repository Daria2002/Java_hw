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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet creates xls file with votes. File downloads when user click on
 * link.
 * @author Daria Matković
 *
 */
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * deafult serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		// Napravi datoteku ako je potrebno; inače je samo pročitaj...
		
		String[] data = readFile(fileName);
		createXlsFile(req, resp, data);
	}

	/**
	 * This method creates xls file with votes.
	 * @param request request
	 * @param response response
	 * @param data data
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	private void createXlsFile(HttpServletRequest request,
			HttpServletResponse response,
			String[] data) throws ServletException, IOException {
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
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	/**
	 * This method reads given file
	 * @param fileName file to read
	 * @return string array where every array element represents one line in file
	 */
	private String[] readFile(String fileName) {
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