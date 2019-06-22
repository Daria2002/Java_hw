package hr.fer.zemris.jsdemo.servlets;

public class Image {

	private String name;
	private String discription;
	private String[] tags;
	
	public Image(String name, String discription, String[] tags) {
		super();
		this.name = name;
		this.discription = discription;
		this.tags = tags;
	}

	public String getName() {
		return name;
	}
	
	public String getDiscription() {
		return discription;
	}

	public String[] getTags() {
		return tags;
	}
}
