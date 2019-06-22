package hr.fer.zemris.java.servlets;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class represents servlet for registration
 * @author Daria Matković
 *
 */
public class RegistrationServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstName = req.getParameter("firstName");
	    String lastName = req.getParameter("lastName");
	    String email = req.getParameter("email");
	    String nickName = req.getParameter("nick");
	    
	    JPADAOImpl sqlDao = new JPADAOImpl();
	    List<BlogUser> blogUsers = sqlDao.getRegistredUsers();
	    
	    for(int i = 0; i < blogUsers.size(); i++) {
	    	if(blogUsers.get(i).getNick().equals(nickName)) {
	    		req.setAttribute("error", "Blog user with given name already exists.");
		    	req.getRequestDispatcher("/registration.jsp").forward(req, resp);
		    	return;
	    	}
	    }
	    
	    String password = req.getParameter("password");
		
	    if(firstName == null || lastName == null || email == null || password == null) {
	    	req.setAttribute("mess", "Please fill all gaps");
	    	req.getRequestDispatcher("/registration.jsp").forward(req, resp);
	    	return;
	    }	
	    
    	sqlDao.addNewUser(firstName, lastName, email, nickName, makePasswordHash(password));
    	req.getRequestDispatcher("/start.jsp").forward(req, resp);
	}
	
	/**
	 * Makes password hash
	 * @param password password
	 * @return password hash
	 */
	private String makePasswordHash(String password) {
		try {
			return getDigest(password);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * gets digest from given data
	 * @param data data 
	 * @return digest
	 * @throws Exception exception
	 */
	private static String getDigest(String data) throws Exception {
		byte[] byteArray = 
				createDigest(data);
		
		String result = byteToHex(byteArray);
		
	    return result;
	}
	
	/**
	 * Creates digest
	 * @param value value
	 * @return byte array
	 * @throws Exception exception
	 */
	private static byte[] createDigest(String value) throws Exception {
       InputStream input = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));

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
	
	/**
	 * converts byte to hex
	 * @param byteArray byte array to convert
	 * @return hex value of byte array
	 */
	private static String byteToHex(byte[] byteArray) {
		String result = "";

		for (int i = 0; i < byteArray.length; i++) {
			result += Integer.toString( ( byteArray[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		
		return result;
	}
}
