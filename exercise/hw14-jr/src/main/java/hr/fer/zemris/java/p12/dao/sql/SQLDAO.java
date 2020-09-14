package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.Unos;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public Unos getEntry(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		Unos unos = null;
		
		try {
			pst = con.prepareStatement("select * from PollOptions where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						unos = new Unos();
						unos.setId(rs.getLong(1));
						unos.setTitle(rs.getString(2));
						unos.setDesc(rs.getString(3));
						unos.setPollId(rs.getInt(4));
						unos.setVotes(rs.getInt(5));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return unos;
	}

	@Override
	public List<Poll> getDefinedPolls() {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<Poll> polls = new ArrayList<Poll>();
		
		try {
			pst = con.prepareStatement("select * from Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						polls.add(new Poll(rs.getLong(1), rs.getString(2), clobStringConversion(rs.getClob(3))));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return polls;
	}

	@Override
	public Poll getPoll(long pollId) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select * from Polls where id=?");
			pst.setLong(1, pollId);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						return new Poll(rs.getLong(1), rs.getString(2), clobStringConversion(rs.getClob(3)));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return null;
	}
	
	/**
	 * Converts clob to string
	 * @param clb clob to convert
	 * @return string value of given clob
	 * @throws IOException exception
	 * @throws SQLException exception
	 */
	private static String clobStringConversion(Clob clb) throws IOException, SQLException {
	     if (clb == null)
	    	 return  "";
             
	     StringBuffer str = new StringBuffer();
	     String strng;
               
     
	     BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
    
	     while ((strng=bufferRead .readLine())!=null)
	    	 str.append(strng);
    
	     return str.toString();
    }        
	
	@Override
	public List<Unos> getOptions(long pollId) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<Unos> entries = new ArrayList<Unos>();
		Unos unos = null;
		
		try {
			pst = con.prepareStatement("select * from PollOptions where pollID=?");
			pst.setLong(1, pollId);
			
			try {
				ResultSet rs = pst.executeQuery();
				try {
					
					while(rs!=null && rs.next()) {
						unos = new Unos();
						unos.setId(rs.getLong(1));
						unos.setTitle(rs.getString(2));
						unos.setDesc(rs.getString(3));
						unos.setPollId(rs.getInt(4));
						unos.setVotes(rs.getInt(5));
						entries.add(unos);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return entries;
	}

	/**
	 * Increase votes for entry with given id
	 * @param id entry's id
	 */
	public void increaseVotes(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		long votes = getVotes(id);
		
		try {
			pst = con.prepareStatement("UPDATE PollOptions SET votesCount=? WHERE id=?");
			pst.setLong(1, votes + 1);
			pst.setLong(2, id);
			
			pst.execute();
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
	}

	/**
	 * Returns votes for entry with given id 
	 * @param id entry's id
	 * @return entry's votes
	 */
	private long getVotes(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select * from PollOptions where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						return rs.getLong(5);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return -1;
	}
	
	// rest of the code is used for jr preparation
	public boolean addUserDB(String name, int age) {
		Connection con = SQLConnectionProvider.getConnection();
		
		// provjeri je li postoji, ako postoji onda ga nemoj dodat, inače dodaj
		String sql = "Select * from UsersTable where name = ? and age = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, age);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e1) {
		}
		
		PreparedStatement pst = null;
		try {
			// save user in votingDB u tablicu koja je napravljena u Inicijalizacija.java
			pst = con.prepareStatement(
					"INSERT INTO UsersTable(name,age) VALUES(?,?)");
			pst.setString(1, name);
			pst.setLong(2, age);
			pst.executeUpdate();
		} catch (Exception e) {
		}

		return false;
	}
	
	public List<Integer> getAgesOfUsers(String name) {
		List<Integer> users = new ArrayList<Integer>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select * from UsersTable where name=?");
			pst.setString(1, name);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						users.add(rs.getInt(2));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return users;
	}
	
	public Map<String, Integer> getAllUsers() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select * from UsersTable");
			
			try {
				ResultSet rs = pst.executeQuery();
				
				while (rs!=null && rs.next()) {
					map.put(rs.getString(1), rs.getInt(2));
				}
				
			} catch (Exception e) {
			}
			
		} catch (Exception e) {
		}
		
		return map;
	}
	
}