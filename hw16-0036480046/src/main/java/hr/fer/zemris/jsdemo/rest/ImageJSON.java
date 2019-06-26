package hr.fer.zemris.jsdemo.rest;

import hr.fer.zemris.jsdemo.servlets.ImagesDB;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

/**
 * This class gets requested data for image, or tag name
 * @author Daria Matković
 *
 */
@Path("/imagej")
public class ImageJSON {

	/**
	 * This method is called only if url written in path is requested using method GET.
	 * In this case it would be: http://localhost:8080/galerija/tags
	 * @return
	 */
	@Path("/tags")
	@GET
	@Produces("application/json")
	public Response getTags() {
		return Response.status(Status.OK).entity(new Gson().toJson(ImagesDB.getTags())).build();
	}
	
	@Path("/thumbnails/{tagName}")
	@GET
	@Produces("application/json")
	public Response getThumbnails(@PathParam("tagName") String tagName) {
		return Response.status(Status.OK).entity(new Gson().toJson(ImagesDB.getImages(tagName))).build();
	}
	
	@Path("/image/{imageName}")
	@GET
	@Produces("application/json")
	public Response getImage(@PathParam("imageName") String imageName) {
		return Response.status(Status.OK).entity(new Gson().toJson(ImagesDB.getImageWithGivenName(imageName))).build();
	}
}
