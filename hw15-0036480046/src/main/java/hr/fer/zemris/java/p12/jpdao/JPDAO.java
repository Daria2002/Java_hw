package hr.fer.zemris.java.p12.jpdao;

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
public interface JPDAO {
	
	/**
	 * Check that user exists
	 * @param username user name of user to check
	 * @return true if user exists, otherwise false
	 */
	public boolean userExists(String username);
	
	/**
	 * Check if given password is correct for user with given nick name
	 * @param nickName nick name
	 * @param passwordHash password hash to check
	 * @return true if password is correct, otherwise false
	 */
	public boolean correctPassword(String nickName, String passwordHash);
	
	/**
	 * Adds new user
	 * @param firstName first name
	 * @param lastName last name
	 * @param email email
	 * @param nickName nick name
	 * @param password password
	 */
	public void addNewUser(String firstName, String lastName, String email,
			String nickName, String password); 
	
	/**
	 * Gets blog user
	 * @param nickName nick name
	 * @return blog user
	 */
	public BlogUser getBlogUser(String nickName);
	
	/**
	 * gets entries
	 * @param nick nick
	 * @return entries for user with given nick
	 */
	public List<BlogEntry> getEntries(String nick);
	
	/**
	 * gets entry
	 * @param id id
	 * @return blog entry
	 */
	public BlogEntry getEntry(Long id);
	
	/**
	 * adds comment to blog user
	 * @param id id
	 * @param newComment new comment
	 * @param email email
	 */
	public void addCommentToBlogUser(Long id, String newComment, String email);
	
	/**
	 * gets registered users
	 * @return list with blog users
	 */
	public List<BlogUser> getRegistredUsers();
	
	/**
	 * adds new entry
	 * @param newEntry entry to add
	 */
	public void addNewEntry(BlogEntry newEntry);
}