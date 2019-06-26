package hr.fer.zemris.jsdemo.servlets;

/**
 * This class represents image
 * @author Daria Matković
 *
 */
public class Image {
	/** image name **/
	private String name;
	/** image description **/
	private String description;
	/** image tags **/
	private String[] tags;
	
	/**
	 * Image constructor that initialize image data
	 * @param name image name
	 * @param description description
	 * @param tags image tags
	 */
	public Image(String name, String description, String[] tags) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Name getter
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Description getter
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Tags getter
	 * @return array of tags
	 */
	public String[] getTags() {
		return tags;
	}
}
