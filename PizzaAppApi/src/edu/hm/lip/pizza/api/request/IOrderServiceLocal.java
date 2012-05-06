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

import edu.hm.lip.pizza.api.object.ressources.Order;

/**
 * @author Franz Mathauser
 */
@Local
@Path( "/orders" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IOrderServiceLocal
{

	@GET
	@Path( "" )
	List<Order> findAll();

	@POST
	@Path( "" )
	public Order create( Order order );

	@GET
	@Path( "{id}" )
	Order find( @PathParam( "id" ) int id );

	@DELETE
	@Path( "{id}" )
	public void remove( @PathParam( "id" ) int id );

	@PUT
	@Path( "{id}/delivered" )
	public Order updateOrderToDelivered( @PathParam( "id" ) int id );
}
