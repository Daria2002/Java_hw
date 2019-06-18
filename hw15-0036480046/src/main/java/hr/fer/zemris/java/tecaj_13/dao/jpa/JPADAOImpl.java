package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctPassword(String nickName, String passwordHash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addNewUser(String firstName, String lastName, String email, String nickName, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BlogUser getBlogUser(String nickName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BlogEntry> getEntries(String nick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlogEntry getEntry(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}