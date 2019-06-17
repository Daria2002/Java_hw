package hr.fer.zemris.java.p12.dao;

//import hr.fer.zemris.java.p12.model.Unos;

import java.util.List;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	
	public boolean userExists(String username);
	public boolean correctPassword(String nickName, String passwordHash);
	public void addNewUser(String firstName, String lastName, String email,
			String nickName, String password); 
	public BlogUser getBlogUser(String nickName);
}