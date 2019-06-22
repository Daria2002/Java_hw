package hr.fer.zemris.jsdemo.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

public class ImagesDB {
	
	public static final int WIDTH = 150;
	public static final int HEIGHT = 150;
	
	public static Set<String> tags = new HashSet<String>();
	/** key is tag, value List of images with that tag **/
	public static Map<String, List<String>> imageMap = new HashMap<String, List<String>>();
	
	public static Map<String, Image> images = new HashMap<String, Image>();
	
	public static List<String> getImages(String tag) {
		return imageMap.get(tag);
	}
	
	public static void createThumbnail(String imageName, OutputStream os, String pathBase) {
		try {
			Path path = Paths.get(pathBase + "/thumbnails");
			//File currentDirectory = new File(new File(".").getAbsolutePath());
			if(!Files.exists(path)) {
				Files.createDirectories(path);
			}
			if(!path.resolve(imageName).toFile().exists()) {
				BufferedImage bi = ImageIO.read(Paths.get(pathBase + "/slike")
						.resolve(imageName).toFile());
				
				BufferedImage newImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g2D = newImage.createGraphics();
				g2D.drawImage(bi, 0, 0, WIDTH, HEIGHT, null);
				g2D.dispose();
				
				ImageIO.write(newImage, "jpg", path.resolve(imageName).toFile());
			}

			FileInputStream thumbnailStream = new FileInputStream(
					Paths.get(pathBase + "/thumbnails")
					.resolve(imageName).toFile());
			
			byte[] buff = new byte[4096];
			int len;
			while(true) {
				len = thumbnailStream.read(buff);
				if(len < 0) {
					break;
				}
				os.write(buff, 0, len);
			}
			
			thumbnailStream.close();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createImage(String imageName, OutputStream os, String pathBase) {
		try {
			FileInputStream imageStream = new FileInputStream(
					Paths.get(pathBase + "/slike")
					.resolve(imageName).toFile());
			
			byte[] buff = new byte[4096];
			int len;
			while(true) {
				len = imageStream.read(buff);
				if(len < 0) {
					break;
				}
				os.write(buff, 0, len);
			}
			
			imageStream.close();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Image getImageWithGivenName(String name) {
		return images.get(name);
	}
	
	public static Set<String> getTags() {
		return tags;
	}
	
	public static void addImage(Image image) {
		String[] tagArray = image.getTags();
		List<String> imageList;
		
		images.put(image.getName(), image);
		
		for(int i = 0; i < tagArray.length; i++) {
			tags.add(tagArray[i]);
			imageList = imageMap.get(tagArray[i]);
			if(imageList != null) {
				imageList.add(image.getName());
			} else {
				imageList = new ArrayList<String>();
				imageList.add(image.getName());
				imageMap.put(tagArray[i], imageList);
			}
		}
	}
}