package hr.fer.zemris.java.p12.model;

public class User {

	private String fn;
	private String ln;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String fn, String ln) {
		super();
		this.fn = fn;
		this.ln = ln;
	}
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getLn() {
		return ln;
	}
	public void setLn(String ln) {
		this.ln = ln;
	}
}
