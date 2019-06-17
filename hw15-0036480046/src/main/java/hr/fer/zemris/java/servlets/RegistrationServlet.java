package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

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
	    
    	SQLDAO sqlDao = new SQLDAO();
    	sqlDao.addNewUser(firstName, lastName, email, nickName, password);
    	req.getRequestDispatcher("/WEB-INF/index.jsp");
	}
}
