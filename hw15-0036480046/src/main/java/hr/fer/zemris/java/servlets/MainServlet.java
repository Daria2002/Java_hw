package hr.fer.zemris.java.servlets;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.derby.tools.sysinfo;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class represents servlet for logging in and registration
 * @author Daria Matković
 *
 */
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/start.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nickName = req.getParameter("username");
        String password = req.getParameter("password");
        
        JPADAOImpl sqlDao = new JPADAOImpl();
        
        if(sqlDao.userExists(nickName)) {
        	
        	if(sqlDao.correctPassword(nickName, makePasswordHash(password))) {
        		BlogUser blogUser = sqlDao.getBlogUser(nickName);
        		
        		req.getSession().setAttribute("current.user.id", blogUser.getId());
        		req.getSession().setAttribute("current.user.nick", blogUser.getNick());
        		req.getSession().setAttribute("current.user.fn", blogUser.getFirstName());
        		req.getSession().setAttribute("current.user.ln", blogUser.getLastName());
        		
        		//req.setAttribute("registredUsers", sqlDao.getRegistredUsers());

            	req.getRequestDispatcher("/start.jsp").forward(req, resp);
            	
        		//resp.sendRedirect(req.getContextPath()+"/servleti/author/" + nickName);
        		
        	} else {
        		req.setAttribute("error", "Given password is wrong");
            	req.setAttribute("username", nickName);
            	req.getRequestDispatcher("/start.jsp").forward(req, resp);
        	}
        	
        } else {
        	req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
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
			e.printStackTrace();
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
