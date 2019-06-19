package hr.fer.zemris.java.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

public class RegistrationServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstName = req.getParameter("firstName");
	    String lastName = req.getParameter("lastName");
	    String email = req.getParameter("email");
	    String nickName = req.getParameter("nick");
	    String password = req.getParameter("password");
		
	    if(firstName == null || lastName == null || email == null || password == null) {
	    	req.setAttribute("mess", "Please fill all gaps");
	    	req.getRequestDispatcher("/WEB-INF/registration.jsp");
	    	return;
	    }	
	    
	    JPADAOImpl sqlDao = new JPADAOImpl();
    	sqlDao.addNewUser(firstName, lastName, email, nickName, makePasswordHash(password));
    	req.getRequestDispatcher("/WEB-INF/index.jsp");
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
