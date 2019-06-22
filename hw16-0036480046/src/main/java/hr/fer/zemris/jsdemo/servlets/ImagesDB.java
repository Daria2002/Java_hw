package hr.fer.zemris.jsdemo.servlets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ImagesDB {
	
	public static Set<String> tags = new HashSet<String>();
	/** key is tag, value List of images with that tag **/
	public static Map<String, List<Image>> imageMap = new HashMap<String, List<Image>>();
	
	public static Set<String> getTags() {
		return tags;
	}
	
	public static void addImage(Image image) {
		String[] tagArray = image.getTags();
		List<Image> imageList;
		
		for(int i = 0; i < tagArray.length; i++) {
			tags.add(tagArray[i]);
			imageList = imageMap.get(tagArray[i]);
			if(imageList != null) {
				imageList.add(image);
			}
		}
	}
}