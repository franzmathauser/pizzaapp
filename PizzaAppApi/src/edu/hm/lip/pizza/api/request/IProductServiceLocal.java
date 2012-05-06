package edu.hm.lip.pizza.api.request;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.hm.lip.pizza.api.object.ressources.GPSData;
import edu.hm.lip.pizza.api.object.ressources.Product;

/**
 * @author Franz Mathauser
 */
@Local
@Path( "/products" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IProductServiceLocal
{

	@GET
	@Path( "" )
	List<Product> findAll();

	@POST
	@Path( "" )
	public Product create( Product product );

	@GET
	@Path( "{id}" )
	Product find( @PathParam( "id" ) int id );

	@PUT
	@Path( "{id}" )
	public Product update( Product product );

	@DELETE
	@Path( "{id}" )
	public void remove( @PathParam( "id" ) int id );

}