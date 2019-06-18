//package hr.fer.zemris.java.servlets;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
//import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
//import hr.fer.zemris.java.tecaj_13.model.BlogUser;
//
//public class AuthorServlet extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("hello");
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		JPADAOImpl sqlDao = new JPADAOImpl();
//		
//		int numberOfArgs = req.getPathInfo().length() -
//				req.getPathInfo().replace("/", "").length();
//		
//		// id in url
//		if(numberOfArgs == 3 && isNumeric(req.getPathInfo().split("/")[4])) {
//			BlogEntry entry = sqlDao.getEntry(Integer.valueOf(req.getPathInfo().split("/")[4]));
//			
//			req.setAttribute("title", entry.getTitle());
//			req.setAttribute("text", entry.getText());
//			req.setAttribute("comments", entry.getComments());
//			req.getRequestDispatcher("/WEB-INF/entry.jsp");
//			
//			return;
//		
//		} else if(numberOfArgs == 3 && "new".equals(req.getPathInfo().split("/")[4])) {
//			
//			BlogEntry newEntry = new BlogEntry();
//			
//			newEntry.setComments(null);
//			newEntry.setCreatedAt(new Date());
//			newEntry.setCreator(sqlDao.getBlogUser(req.getPathInfo().split("/")[3]));
//			newEntry.setLastModifiedAt(new Date());
//			newEntry.setText(req.getParameter("text"));
//			newEntry.setTitle(req.getParameter("title"));
//
//			req.getRequestDispatcher("/WEB-INF/entry.jsp");
//			
//			return;
//			
//		} else if(numberOfArgs == 3 && "edit".equals(req.getPathInfo().split("/")[4])) {
//
//			req.getRequestDispatcher("/WEB-INF/entry.jsp");
//			
//			return;
//		
//		} else if(numberOfArgs == 3 && "addComment".equals(req.getPathInfo().split("/")[4])) {
//
//			String newComment = req.getParameter("comment");
//			Long id = Long.valueOf(req.getPathInfo().split("/")[4]);
//			String nick = String.valueOf(req.getPathInfo().split("/")[3]);
//			
//			sqlDao.addCommentToBlogUser(id, newComment, sqlDao.getBlogUser(nick).getEmail());
//			
//			req.getRequestDispatcher("/WEB-INF/entry.jsp");
//			
//			return;
//		}
//		
//		BlogUser blogUser = sqlDao.getBlogUser(
//				req.getPathInfo().split("/")[1]);
//		
//		req.setAttribute("nickEntries", 
//				sqlDao.getEntries(blogUser.getNick()));
//		req.setAttribute("nickName", blogUser.getNick());
//		
//		req.getRequestDispatcher("blogEntriesListPage.jsp");
//	}
//	
//	private boolean isNumeric(String str) { 
//	  try {  
//	    Double.parseDouble(str);  
//	    return true;
//	  } catch(NumberFormatException e){  
//	    return false;  
//	  }  
//	}
//}
