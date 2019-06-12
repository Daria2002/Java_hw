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

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.Unos;

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
		
		SQLDAO sqlDao = new SQLDAO();
		
		List<Poll> polls = sqlDao.getDefinedPolls();
		List<Long> pollIds = new ArrayList<Long>();
		
		// make list of all poll id's
		for(int i = 0; i < pollIds.size(); i++) {
			pollIds.add(polls.get(i).getPollId());
		}
		
		List<Unos> entries = new ArrayList<Unos>();
		
		// make list of all entries
		for(int i = 0; i < pollIds.size(); i++) {
			List<Unos> help = sqlDao.getOptions(pollIds.get(i));
			
			// add all entries with pollId = pollIds.get(i) 
			for(int j = 0; j < help.size(); j++) {
				entries.add(help.get(j));
			}
		}
		
		createXlsFile(req, resp, entries);
	}

	/**
	 * This method creates xls file with votes.
	 * @param request request
	 * @param response response
	 * @param data data
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	private void createXlsFile(HttpServletRequest request, HttpServletResponse response,
			List<Unos> data) throws ServletException, IOException {
		try{
			HSSFWorkbook hwb = new HSSFWorkbook();
			
			HSSFSheet sheet =  hwb.createSheet("sheet");
	
			for(int i = 0; i < data.size(); i++) {
 				HSSFRow rowhead = sheet.createRow((short)i);
				rowhead.createCell((short) 0).setCellValue(data.get(i).getId());
				rowhead.createCell((short) 1).setCellValue(data.get(i).getVotes());
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
}