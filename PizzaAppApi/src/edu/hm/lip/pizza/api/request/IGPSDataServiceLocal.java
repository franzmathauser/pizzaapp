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
@Path( "/gpsdatas" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IGPSDataServiceLocal
{

	@GET
	@Path( "" )
	List<GPSData> findAll();

	@POST
	@Path( "" )
	public GPSData create( GPSData gpsData );
	
	@GET
	@Path( "/drivers" )
	List<GPSData> findDriverPositions();
	
	@GET
	@Path( "/drivers/{id}" )
	GPSData findDriverPositions( @PathParam( "id" ) int id);
	
	@POST
	@Path( "/drivers/{id}/orders" )
	List<GPSData> addOrderToDriver( @PathParam( "id" ) int id);

	@GET
	@Path( "{id}" )
	List<GPSData> find( @PathParam( "id" ) int id );

	@DELETE
	@Path( "{id}" )
	public void remove( @PathParam( "id" ) int id );
	
	

}
