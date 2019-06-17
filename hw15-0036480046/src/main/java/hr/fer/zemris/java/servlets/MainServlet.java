package hr.fer.zemris.java.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nickName = req.getParameter("username");
        String password = req.getParameter("password");
        
        SQLDAO sqlDao = new SQLDAO();
        
        if(sqlDao.userExists(nickName)) {
        	
        	if(sqlDao.correctPassword(nickName, makePasswordHash(password))) {
        		//openBlogEntries();
        	
        	} else {
            	displayErrorMessage();
            	req.setAttribute("username", nickName);
            	req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        	}
        	
        } else {
        	req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
        }
	}
	
	private void displayErrorMessage() {
		String someMessage = "Wrong password!";
		System.out.println("<script type='text/javascript'>");
		System.out.println("alert(" + "'" + someMessage + "'" + ");</script>");
		System.out.println("</head><body></body></html>");
	}

	private String makePasswordHash(String password) {
		try {
			return getDigest(password);
		} catch (Exception e) {
		}
		return null;
	}
	
	private static String getDigest(String data) throws Exception {
		byte[] byteArray = 
				createDigest(data);
		
		String result = byteToHex(byteArray);
		
	    return result;
	}
	
	private static byte[] createDigest(String filename) throws Exception {
       InputStream input =  new FileInputStream(filename);

       byte[] buffer = new byte[4000];
       MessageDigest complete = MessageDigest.getInstance("SHA-256");
       int numRead;

       do {
           numRead = input.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       input.close();
       return complete.digest();
	}
	
	private static String byteToHex(byte[] byteArray) {
		String result = "";

		for (int i = 0; i < byteArray.length; i++) {
			result += Integer.toString( ( byteArray[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		
		return result;
	}
}
