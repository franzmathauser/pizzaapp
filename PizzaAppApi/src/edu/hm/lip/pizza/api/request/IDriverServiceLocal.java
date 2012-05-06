package edu.hm.lip.pizza.api.request;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import edu.hm.lip.pizza.api.object.ressources.Driver;
import edu.hm.lip.pizza.api.object.ressources.Order;
import edu.hm.lip.pizza.api.object.ressources.Product;

/**
 * @author Franz Mathauser
 */
@Local
@Path( "/drivers" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IDriverServiceLocal
{

	@GET
	@Path( "" )
	List<Driver> findAll();

	@POST
	@Path( "" )
	public Driver create( Product product );

	@GET
	@Path( "{id}" )
	Driver find( @PathParam( "id" ) int id );

	@GET
	@Path( "{id}/route" )
	@QueryParam("")
	List<Order> getRoute( @PathParam( "id" ) int id );

	@POST
	@Path( "{id}/orders" )
	List<Order> getOrders( @PathParam( "id" ) int id );

	@DELETE
	@Path( "{d_id}/orders/{o_id}" )
	public void remove( @PathParam( "d_id" ) int id, @PathParam( "o_id" ) int o_id );

}
