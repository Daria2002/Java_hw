package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.jpdao.JPDAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class represents servlet for adding new entries, editing existing entries
 * and adding comments.
 * @author Daria Matković
 *
 */
public class AuthorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			newEntry.setCreator(JPDAOProvider.getDao().getBlogUser(req.getRequestURI().split("/")[4]));
			newEntry.setLastModifiedAt(new Date());
			newEntry.setText(req.getParameter("text"));
			newEntry.setTitle(req.getParameter("title"));
			
			JPDAOProvider.getDao().addNewEntry(newEntry);
			
			resp.sendRedirect(req.getContextPath()+"/servleti/author/" + req.getRequestURI().split("/")[4]);
		
		} else if(req.getParameter("newText") != null) {
			String newEntryText = req.getParameter("newText");
			String newEntryTitle = req.getParameter("newTitle");
			BlogEntry blogEntry = JPDAOProvider.getDao().getEntry(Long.valueOf(req.getRequestURI().split("/")[5]));
			
			blogEntry.setText(newEntryText);
			blogEntry.setTitle(newEntryTitle);

			resp.sendRedirect(req.getServletContext().getContextPath()
					+"/servleti/author/" + req.getRequestURI().split("/")[4]);
			
	    } else if(numberOfArgs == 7 && "edit".equals(req.getRequestURI().split("/")[6])) {
	    	req.setAttribute("exText", JPDAOProvider.getDao()
	    			.getEntry(Long.valueOf(req.getRequestURI().split("/")[5])).getText());
	    	req.setAttribute("exTitle", JPDAOProvider.getDao()
	    			.getEntry(Long.valueOf(req.getRequestURI().split("/")[5])).getTitle());
			req.getRequestDispatcher("/edit.jsp").forward(req, resp);;
			
			return;
			
		} else if(req.getParameter("comment") != null) {

			String message = req.getParameter("comment");
			
			String email;
			if(req.getSession().getAttribute("current.user.nick") != null) {
				BlogUser bu = JPDAOProvider.getDao().getBlogUser((String)req.getSession().getAttribute("current.user.nick"));
				email = bu.getEmail();
			} else {
				email = req.getParameter("email");
			}
			
			Long id = Long.valueOf(req.getRequestURI().split("/")[5]);
			JPDAOProvider.getDao().addCommentToBlogUser(id, message, email);
			
			req.setAttribute("nickEntries", 
					JPDAOProvider.getDao().getEntries(req.getRequestURI().split("/")[4]));
			req.setAttribute("nickName", req.getRequestURI().split("/")[4]);
			
			resp.sendRedirect(req.getServletContext().getContextPath()
					+"/servleti/author/" + req.getRequestURI().split("/")[4]);
			
			
		}
		
		// http://localhost:8080/blog/servleti/author/NICK/id
		else if(numberOfArgs == 6 && isNumeric(req.getRequestURI().split("/")[5])) {
			BlogEntry entry = JPDAOProvider.getDao().getEntry(Long.valueOf(req.getRequestURI().split("/")[5]));
			
			req.setAttribute("title", entry.getTitle());
			req.setAttribute("text", entry.getText());
			req.setAttribute("comments", entry.getComments());
			req.setAttribute("nick", entry.getCreator().getNick());
			req.getRequestDispatcher("/entry.jsp").forward(req, resp);
			
			return;
		
		} else if(numberOfArgs > 4 && "new".equals(req.getRequestURI().split("/")[5])) {
			
			req.getRequestDispatcher("/newEntry.jsp").forward(req, resp);
			
			return;
			
		} else {
			BlogUser blogUser = JPDAOProvider.getDao().getBlogUser(
					req.getRequestURI().split("/")[4]);
			
			req.setAttribute("nickEntries", 
					JPDAOProvider.getDao().getEntries(blogUser.getNick()));
			req.setAttribute("nickName", blogUser.getNick());
			
			req.getRequestDispatcher("/blogEntriesListPage.jsp").forward(req, resp);
			
		}
		
	}
	
	/**
	 * checks if given string represents number
	 * @param str value to check 
	 * @return true if given string represents number, otherwise false
	 */
	private boolean isNumeric(String str) { 
	  try {  
	    Double.parseDouble(str);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  }  
	}
}
