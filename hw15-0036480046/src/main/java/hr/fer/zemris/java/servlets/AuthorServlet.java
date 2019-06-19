package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
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

		int numberOfArgs = req.getRequestURI().length() -
				req.getRequestURI().toString().replace("/", "").length();
		
		// if new entry is added
		if(req.getParameter("title") != null) {
			BlogEntry newEntry = new BlogEntry();
			
			newEntry.setComments(null);
			newEntry.setCreatedAt(new Date());
			newEntry.setCreator(DAOProvider.getDao().getBlogUser(req.getRequestURI().split("/")[4]));
			newEntry.setLastModifiedAt(new Date());
			newEntry.setText(req.getParameter("text"));
			newEntry.setTitle(req.getParameter("title"));
			
			DAOProvider.getDao().addNewEntry(newEntry);
			
			resp.sendRedirect(req.getContextPath()+"/servleti/author/" + req.getRequestURI().split("/")[4]);
		
		} else if(req.getParameter("comment") != null) {

			String message = req.getParameter("comment");
			
			String email;
			if(req.getSession().getAttribute("current.user.nick") != null) {
				BlogUser bu = DAOProvider.getDao().getBlogUser((String)req.getSession().getAttribute("current.user.nick"));
				email = bu.getEmail();
			} else {
				email = req.getParameter("email");
			}
			
			Long id = Long.valueOf(req.getRequestURI().split("/")[5]);
			DAOProvider.getDao().addCommentToBlogUser(id, message, email);
			
			req.getRequestDispatcher("/entry.jsp").forward(req, resp);
		}
		
		// http://localhost:8080/blog/servleti/author/NICK/id
		else if(numberOfArgs == 6 && isNumeric(req.getRequestURI().split("/")[5])) {
			BlogEntry entry = DAOProvider.getDao().getEntry(Long.valueOf(req.getRequestURI().split("/")[5]));
			
			req.setAttribute("title", entry.getTitle());
			req.setAttribute("text", entry.getText());
			req.setAttribute("comments", entry.getComments());
			req.setAttribute("nick", entry.getCreator().getNick());
			req.getRequestDispatcher("/entry.jsp").forward(req, resp);
			
			return;
		
		} else if(numberOfArgs == 5 && "new".equals(req.getRequestURI().split("/")[5])) {
			
			req.getRequestDispatcher("/newEntry.jsp").forward(req, resp);
			
			return;
			
		} else if(numberOfArgs == 6 && "edit".equals(req.getRequestURI().split("/")[4])) {

			req.getRequestDispatcher("/entry.jsp");
			
			return;
			
		} else {
			BlogUser blogUser = DAOProvider.getDao().getBlogUser(
					req.getRequestURI().split("/")[4]);
			
			req.setAttribute("nickEntries", 
					DAOProvider.getDao().getEntries(blogUser.getNick()));
			req.setAttribute("nickName", blogUser.getNick());
			
			req.getRequestDispatcher("/blogEntriesListPage.jsp").forward(req, resp);
			
		}
		
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
