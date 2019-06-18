package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class JPADAOImpl implements DAO {

	/* predavanje
	
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	
	 */
	
	
	@Override
	public boolean userExists(String username) {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, username);
		return blogUser != null;
	}

	@Override
	public boolean correctPassword(String nickName, String passwordHash) {
		return passwordHash.equals(JPAEMProvider.getEntityManager()
				.find(BlogUser.class, nickName).getPasswordHash());
	}

	@Override
	public void addNewUser(String firstName, String lastName, String email,
			String nickName, String passwordHash) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		em.getTransaction().begin();
		
		BlogUser blogUser = new BlogUser();
		blogUser.setEmail(email);
		blogUser.setFirstName(firstName);
		blogUser.setLastName(lastName);
		blogUser.setNick(nickName);
		blogUser.setPasswordHash(passwordHash);
		
		em.persist(blogUser);
		em.getTransaction().commit();
	}

	@Override
	public BlogUser getBlogUser(String nickName) {
		return JPAEMProvider.getEntityManager().find(BlogUser.class, nickName);
	}

	@Override
	public List<BlogEntry> getEntries(String nick) {
		BlogUser blogUser = getBlogUser(nick);
		return blogUser.getBlogEntries();
	}

	@Override
	public BlogEntry getEntry(int id) {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

}