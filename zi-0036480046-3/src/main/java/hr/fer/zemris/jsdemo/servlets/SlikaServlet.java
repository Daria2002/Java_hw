package hr.fer.zemris.jsdemo.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SlikaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = (String)(req.getParameter("name"));
	
		String path = (String)getServletContext().getRealPath("WEB-INF/images/" + name);
				
		System.out.println(path);
		
		System.out.println("name:"+name);
		
		req.setAttribute("imeS", name);
		
		String txtFile = readAllBytesJava7(path);
		
		
		
		req.setAttribute("numL", countOcc(txtFile, "LINE"));
		req.setAttribute("numK", countOcc(txtFile, "CIRCLE"));
		req.setAttribute("numKRUG", countOcc(txtFile, "FCIRCLE"));
		req.setAttribute("numT", countOcc(txtFile, "FTRIANGLE"));

		// Pošalji ih JSP-u...
				req.getRequestDispatcher("/WEB-INF/pages/prikazi.jsp")
				.forward(req, resp);
	}

	private int countOcc(String main, String check) {
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = main.indexOf(check,lastIndex);

		    if(lastIndex != -1){
		    	if("CIRCLE".equals(check) && lastIndex > 0 && main.charAt(lastIndex-1)=='F') {
		    		
		    	}
		    	else {
		    		count ++;
		    	}
		        lastIndex += check.length();
		    }
		}
		
		return count;
	}
	
	   private static String readAllBytesJava7(String filePath)
	    {
	        String content = "";
	 
	        try
	        {
	            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	 
	        return content;
	    }
	
}
