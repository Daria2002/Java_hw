package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
		
		DatabaseMetaData dbmd;
		try {
			dbmd = cpds.getConnection().getMetaData();
			Connection con = cpds.getConnection();
			
			int polls = tableExists(con, "Polls");
			if (polls < 0) {
				createPolls(con);
			}
			if(polls <= 0) {
				addBendData(con);
				addLaptopData();
			}
			
			if (tableExists(con, "PollOptions") <= 0) {
				createPollOptions(con);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addBendData(Connection con) throws SQLException {
		int pollId = 1;
		
		String insertPolls = "INSERT INTO Polls (ID, Title, Message) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = con.prepareStatement(insertPolls);
		preparedStatement.setInt(1, pollId);
		preparedStatement.setString(2, "Glasanje za omiljeni bend:");
		preparedStatement.setString(3, "Od sljedećih bendova,"
				+ " koji Vam je bend najdraži? Kliknite na link kako " + 
				"biste glasali!");
		preparedStatement.executeUpdate();
		
		String insertPollOptions = "INSERT INTO PollOptions (id, OptionTitle,"
				+ " optionLink, pollID) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(insertPollOptions);
		
		addRowInPollOptions(ps, 1, "The Beatles", 
				"https://www.youtube.com/watch?v=z9ypq6_5bsg", pollId);
		addRowInPollOptions(ps, 2, "The Platters", 
				"https://www.youtube.com/watch?v=H2di83WAOhU", pollId);
		addRowInPollOptions(ps, 3, "The Beach Boys", 
				"https://www.youtube.com/watch?v=2s4slliAtQU", pollId);
		addRowInPollOptions(ps, 4, "The Four Seasons", 
				"https://www.youtube.com/watch?v=y8yvnqHmFds", pollId);
		addRowInPollOptions(ps, 5, "The Marcels", 
				"https://www.youtube.com/watch?v=qoi3TH59ZEs", pollId);
		addRowInPollOptions(ps, 6, "The Everly Brothers", 
				"https://www.youtube.com/watch?v=tbU3zdAgiX8", pollId);
		addRowInPollOptions(ps, 7, "The Mamas And The Papas", 
				"https://www.youtube.com/watch?v=N-aK6JnyFmk", pollId);
	}

	private void addRowInPollOptions(PreparedStatement ps, int id,
			String optionTitle, String optionLink, int pollId) throws SQLException {
		ps.setInt(1, id);
		ps.setString(2, optionTitle);
		ps.setString(3, optionLink);
		ps.setInt(4, pollId);
		ps.executeUpdate();
	}
	
	private void createPollOptions(Connection con) throws SQLException {
		String pollOptionsTable = "CREATE TABLE PollOptions\n" + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
				"optionTitle VARCHAR(100) NOT NULL,\n" + 
				"optionLink VARCHAR(150) NOT NULL,\n" + 
				"pollID BIGINT,\n" + 
				"votesCount BIGINT,\n" + 
				"FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + 
				");";
		
		PreparedStatement ps = con.prepareStatement(pollOptionsTable);
		ps.executeUpdate();
		ps.close();
	}

	private void createPolls(Connection con) throws SQLException {
		String pollsTable = "CREATE TABLE Polls\n" + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
				"title VARCHAR(150) NOT NULL,\n" + 
				"message CLOB(2048) NOT NULL\n" + 
				");";
		
		PreparedStatement ps = con.prepareStatement(pollsTable);
		ps.executeUpdate();
		ps.close();
	}

	private static int tableExists(Connection con, String table) {
	      int numRows = 0;
	      try {
	         DatabaseMetaData dbmd = con.getMetaData();
	         // Note the args to getTables are case-sensitive!
	         ResultSet rs = dbmd.getTables( null, "APP", table.toUpperCase(), null);
	         while(rs.next()) ++numRows;
	      } catch (SQLException e) {
	    	  return -1;
	      }
	      return numRows;
	}

	/**
	 * Gets connection url
	 * @param fullPath path to properties file
	 * @return connectoion url
	 */
	private String getConnectionURL(String fullPath) {
		Properties p = new Properties();
		StringBuilder sb = new StringBuilder();
		
		sb.append("jdbc:derby://");
		sb.append(p.getProperty("host"));
		sb.append(":");
		sb.append(p.getProperty("port"));
		sb.append("/");
		sb.append(p.getProperty("name"));
		sb.append(";");
		sb.append("user=" + p.getProperty("user"));
		sb.append(";");
		sb.append("password=" + p.getProperty("password"));
		
		return sb.toString();
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