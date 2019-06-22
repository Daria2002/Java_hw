package hr.fer.zemris.java.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import hr.fer.zemris.jsdemo.servlets.Image;
import hr.fer.zemris.jsdemo.servlets.ImagesDB;

@WebListener
/**
 * This class represents ini file
 * @author Daria Matković
 *
 */
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			String path = sce.getServletContext().getRealPath("WEB-INF/opisnik.txt");
			List<String> lines = Files.readAllLines(Paths.get(path));
			
			String imageName;
			String imageDiscription;
			String[] imageTags = new String[lines.size()/3];
						
			for(int i = 0; i < lines.size(); i = i+3) {
				imageName = lines.get(i);
				imageDiscription = lines.get(i+1);
				imageTags = lines.get(i+2).split(",");
				
				String[] imageTagsTrimed = new String[imageTags.length];
				for(int j = 0; j < imageTags.length; j++) {
					imageTagsTrimed[j] = imageTags[j].trim();
				}
				
				
				ImagesDB.addImage(new Image(imageName, imageDiscription, imageTagsTrimed));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
