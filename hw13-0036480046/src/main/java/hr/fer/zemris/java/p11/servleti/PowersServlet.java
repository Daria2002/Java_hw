package hr.fer.zemris.java.p11.servleti;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PowersServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    Integer a = 0;
    	Integer b = 0;
    	Integer n = 0;
    	
    	try {
    		a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception e) {
			req.setAttribute("mess", "Parameters are not ok");
			req.getRequestDispatcher("/WEB-INF/pages/powers.jsp").forward(req, resp);
		}
    	
    	try {
    		b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception e) {
			req.setAttribute("mess", "Parameters are not ok");
			req.getRequestDispatcher("/WEB-INF/pages/powers.jsp").forward(req, resp);
		}
    	
    	if(a > 100 || a < -100 || b > 100 || b < 100 || n  > 5 || n < 1) {
    		req.setAttribute("mess", "Parameters are not ok");
			req.getRequestDispatcher("/WEB-INF/pages/powers.jsp").forward(req, resp);
    	}
    	
    	req.setAttribute("mess", null);
    	
    	createExelFile(a, b, n);

	    req.getRequestDispatcher("/WEB-INF/pages/powers.jsp").forward(req, resp);
	}

	private void createExelFile(int a, int b, int n) {
		try{
			String filename="/powers.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			
			for(int i = 0; i < n; i++) {
				HSSFSheet sheet =  hwb.createSheet("sheet" + String.valueOf(i));

				HSSFRow rowhead = sheet.createRow((short)0);
				rowhead.createCell((short) 0).setCellValue(a);
				rowhead.createCell((short) 1).setCellValue(b);
				
				HSSFRow row=   sheet.createRow((short)1);
				row.createCell((short) 0).setCellValue(a^i);
				row.createCell((short) 1).setCellValue(b^i);

			}
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			hwb.close();
			System.out.println("Your excel file has been generated!");

		} catch ( Exception ex ) {
		    System.out.println(ex);

		}
	}
}

