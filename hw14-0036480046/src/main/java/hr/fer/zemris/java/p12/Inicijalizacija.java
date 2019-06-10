package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		String fullPath = context.getRealPath("/WEB-INF/dbsettings.properties");

		String connectionURL = getConnectionURL(fullPath);
		
		if(connectionURL == null) {
			System.out.println("Path is not ok.");
			return;
		}
		
		/*
		String dbName="baza1DB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName + ";user=perica;password=pero";
		*/
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Gets connection url
	 * @param fullPath path to properties file
	 * @return connectoion url
	 */
	private String getConnectionURL(String fullPath) {
		String[] lines = getFileLines(fullPath);
		
		if(lines == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:derby://");
		
		for(int i = 0;  i < lines.length; i++) {
			if(i == 2) {
				sb.append("user=");
			} else if(i == 3) {
				sb.append("password=");
			}
			
			
			sb.append(lines[i].split("=")[1]);
			
			switch (i) {
			case 0:
				sb.append(":");
				break;
			
			case 1:
				sb.append("//");
				break;
				
			case 2:
				sb.append(";");
				break;
			
			case 3:
				sb.append(";");
				break;
			
			default:
				break;
			}
		}
		
		return sb.toString();
	}

	/**
	 * Reads file and puts lines into string array
	 * @param fullPath file path
	 * @return returns lines in string array
	 */
	private String[] getFileLines(String fullPath) {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(fullPath));
			String str;
			List<String> list = new ArrayList<String>();
			
			while((str = in.readLine()) != null){
			    list.add(str);
			}
			
			return list.toArray(new String[0]);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}