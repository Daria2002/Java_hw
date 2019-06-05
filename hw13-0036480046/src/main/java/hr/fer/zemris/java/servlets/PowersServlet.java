package hr.fer.zemris.java.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class creates xls file that contains power of parameters user enetered.
 * @author Daria Matković
 *
 */
public class PowersServlet extends HttpServlet {

	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    Integer a = 0;
    	Integer b = 0;
    	Integer n = 0;
    	
    	try {
    		a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
		}
    	
    	try {
    		b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
		}
    	
    	try {
    		n = Integer.valueOf(req.getParameter("n"));
		} catch (Exception e) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
		}
    	
    	if(a > 100 || a < -100 || b > 100 || b < -100 || n  > 5 || n < 1) {
    		req.getRequestDispatcher("/error.jsp").forward(req, resp);
    	}
    	
    	createXlsFile(a, b, n, resp, req);
	}

	/**
	 * This method creates xls file
	 * @param a a
	 * @param b b
	 * @param n number of pages
	 * @param response response
	 * @param request request
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	private void createXlsFile(int a, int b, int n, HttpServletResponse response, 
			HttpServletRequest request) throws ServletException, IOException {
		try{
			HSSFWorkbook hwb = new HSSFWorkbook();
			
			for(int i = 0; i < n; i++) {
				HSSFSheet sheet =  hwb.createSheet("sheet" + String.valueOf(i));

				HSSFRow rowhead = sheet.createRow((short)0);
				rowhead.createCell((short) 0).setCellValue(a);
				rowhead.createCell((short) 1).setCellValue(Math.pow(a, i+1));
				
				HSSFRow row = sheet.createRow((short)1);
				row.createCell((short) 0).setCellValue(b);
				row.createCell((short) 1).setCellValue(Math.pow(b, i+1));
			}
			
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=power.xls");
			hwb.write(response.getOutputStream());
			response.getOutputStream().flush();
			hwb.close();

		} catch ( Exception ex ) {
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}

