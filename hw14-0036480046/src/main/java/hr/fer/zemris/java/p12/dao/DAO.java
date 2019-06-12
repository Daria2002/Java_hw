package hr.fer.zemris.java.p12.dao;

//import hr.fer.zemris.java.p12.model.Unos;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.Unos;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	
	public Unos getEntry(long id);
	public List<Poll> getDefinedPolls();
	public List<Unos> getOptions(long id);
	Poll getPoll(long pollId);
}