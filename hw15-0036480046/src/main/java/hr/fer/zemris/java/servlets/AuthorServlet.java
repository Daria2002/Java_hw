package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class AuthorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("hello");
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JPADAOImpl sqlDao = new JPADAOImpl();
		
		int numberOfArgs = req.getRequestURI().length() -
				req.getRequestURI().toString().replace("/", "").length();
		
		// http://localhost:8080/blog/servleti/author/NICK/id
		if(numberOfArgs == 5 && isNumeric(req.getRequestURI().split("/")[4])) {
			BlogEntry entry = sqlDao.getEntry(Integer.valueOf(req.getRequestURI().split("/")[4]));
			
			req.setAttribute("title", entry.getTitle());
			req.setAttribute("text", entry.getText());
			req.setAttribute("comments", entry.getComments());
			req.getRequestDispatcher("/entry.jsp");
			
			return;
		
		} else if(numberOfArgs == 5 && "new".equals(req.getRequestURI().split("/")[4])) {
			
			BlogEntry newEntry = new BlogEntry();
			
			newEntry.setComments(null);
			newEntry.setCreatedAt(new Date());
			newEntry.setCreator(sqlDao.getBlogUser(req.getRequestURI().split("/")[3]));
			newEntry.setLastModifiedAt(new Date());
			newEntry.setText(req.getParameter("text"));
			newEntry.setTitle(req.getParameter("title"));

			req.getRequestDispatcher("/entry.jsp");
			
			return;
			
		} else if(numberOfArgs == 6 && "edit".equals(req.getRequestURI().split("/")[4])) {

			req.getRequestDispatcher("/entry.jsp");
			
			return;
		
		} else if(numberOfArgs == 6 && "addComment".equals(req.getRequestURI().split("/")[4])) {

			String newComment = req.getParameter("comment");
			Long id = Long.valueOf(req.getRequestURI().split("/")[4]);
			String nick = String.valueOf(req.getRequestURI().split("/")[3]);
			
			sqlDao.addCommentToBlogUser(id, newComment, sqlDao.getBlogUser(nick).getEmail());
			
			req.getRequestDispatcher("/entry.jsp");
			
			return;
		}
		
		BlogUser blogUser = sqlDao.getBlogUser(
				req.getRequestURI().split("/")[4]);
		
		req.setAttribute("nickEntries", 
				sqlDao.getEntries(blogUser.getNick()));
		req.setAttribute("nickName", blogUser.getNick());
		
		req.getRequestDispatcher("/blogEntriesListPage.jsp");
	}
	
	private boolean isNumeric(String str) { 
	  try {  
	    Double.parseDouble(str);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  }  
	}
}
