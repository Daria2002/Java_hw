package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

		String connectionURL;
		try {
			connectionURL = getConnectionURL(fullPath);
		} catch (Exception e2) {
			return;
		}
		
		if(connectionURL == null) {
			System.out.println("Path is not ok.");
			return;
		}
		
//		
//		String dbName="votingDB";
//		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;// + ";user=perica;password=pero";
//		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);/*
		cpds.setUser("ivica");
		cpds.setPassword("ivo");*/
		
		try {
			Connection con = cpds.getConnection();
			
			// create if doesn't exists
			try {
				createPolls(con);
				createPollOptions(con);
				addBendData(con);
				addLaptopData(con);
			} catch (Exception e) {
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}
	
	private void addLaptopData(Connection con) throws SQLException {
		long pollId = addPoll(con, "Voting for favourite laptop:", "What is your favourite laptop?");
		
		String insertPollOptions = "INSERT INTO PollOptions (optionTitle,"
				+ " optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(insertPollOptions);
		
		addRowInPollOptions(ps, 1, "Lenovo", "https://www.lenovo.com/hr/hr/", pollId, 0);
		addRowInPollOptions(ps, 2, "Hp", 
				"https://store.hp.com/id-id/default/laptops-tablets.html", pollId, 0);
		addRowInPollOptions(ps, 3, "Dell", "https://www.dell.com/", pollId, 0);
		addRowInPollOptions(ps, 4, "Toshiba", "http://www.toshiba.com/tai/", pollId, 0);
	}

	private int addPoll(Connection con, String title, String message) 
			throws SQLException {
		String insertPolls = "INSERT INTO Polls (title, message) VALUES (?, ?)";
		PreparedStatement preparedStatement = con.prepareStatement(insertPolls,
				Statement.RETURN_GENERATED_KEYS);
		
		//PreparedStatement preparedStatement = con.prepareStatement(insertPolls);
		/*
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int id = 0;
        while(rs.next()){
        	id = rs.getInt(1);
        }
		*/
		//preparedStatement.getGeneratedKeys();
		//preparedStatement.setInt(1, id);
		preparedStatement.setString(1, title);
		preparedStatement.setString(2, message);
		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int id = 0;
        while(rs!=null && rs.next()){
        	id = rs.getInt(1);
        }
		return id;
	}

	private void addBendData(Connection con) throws SQLException {
		int pollId = addPoll(con, "Glasanje za omiljeni bend:", "Od sljedećih bendova,"
				+ " koji Vam je bend najdraži? Kliknite na link kako " + 
				"biste glasali!");
		
		 String insertPollOptions = "INSERT INTO PollOptions (optionTitle,"
				+ " optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = con.prepareStatement(insertPollOptions);
		
		addRowInPollOptions(ps, 1, "The Beatles", 
				"https://www.youtube.com/watch?v=z9ypq6_5bsg", pollId, 0);
		addRowInPollOptions(ps, 2, "The Platters", 
				"https://www.youtube.com/watch?v=H2di83WAOhU", pollId, 0);
		addRowInPollOptions(ps, 3, "The Beach Boys", 
				"https://www.youtube.com/watch?v=2s4slliAtQU", pollId, 0);
		addRowInPollOptions(ps, 4, "The Four Seasons", 
				"https://www.youtube.com/watch?v=y8yvnqHmFds", pollId, 0);
		addRowInPollOptions(ps, 5, "The Marcels", 
				"https://www.youtube.com/watch?v=qoi3TH59ZEs", pollId, 0);
		addRowInPollOptions(ps, 6, "The Everly Brothers", 
				"https://www.youtube.com/watch?v=tbU3zdAgiX8", pollId, 0);
		addRowInPollOptions(ps, 7, "The Mamas And The Papas", 
				"https://www.youtube.com/watch?v=N-aK6JnyFmk", pollId, 0);
	}

	private void addRowInPollOptions(PreparedStatement ps, long id,
			String optionTitle, String optionLink, long pollId, long votesCount)
					throws SQLException {
		//ps.setInt(1, id);
		ps.setString(1, optionTitle);
		ps.setString(2, optionLink);
		ps.setLong(3, pollId);
		ps.setLong(4, votesCount);
		ps.executeUpdate();
	}
	
	private void createPollOptions(Connection con) throws SQLException {
		String pollOptionsTable = "CREATE TABLE PollOptions " + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
				"optionTitle VARCHAR(100) NOT NULL," + 
				"optionLink VARCHAR(150) NOT NULL," + 
				"pollID BIGINT," + 
				"votesCount BIGINT," + 
				"FOREIGN KEY (pollID) REFERENCES Polls(id)" + 
				")";
		
		PreparedStatement ps = con.prepareStatement(pollOptionsTable);
		ps.executeUpdate();
		ps.close();
	}

	private void createPolls(Connection con) throws SQLException {
		String pollsTable = "CREATE TABLE Polls " + 
				"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
				"title VARCHAR(150) NOT NULL," + 
				"message CLOB(2048) NOT NULL" + 
				")";
		
		PreparedStatement ps = con.prepareStatement(pollsTable);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Gets connection url
	 * @param fullPath path to properties file
	 * @return connection url
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private String getConnectionURL(String fullPath) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(fullPath));
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
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}