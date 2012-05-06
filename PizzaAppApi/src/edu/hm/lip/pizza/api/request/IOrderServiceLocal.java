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
 * REST-Service für die Bestelldomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/orders" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IOrderServiceLocal
{

	/**
	 * Liste aller Bestellungen.
	 * 
	 * @return Order-Liste
	 */
	@GET
	@Path( "" )
	List<Order> findAll();

	/**
	 * Erzeugt eine neue Bestellung.
	 * 
	 * @param order
	 *            Bestellung
	 * @return erzeugte Bestellung
	 */
	@POST
	@Path( "" )
	Order create( Order order );

	/**
	 * Liefere Bestellung anhand von id.
	 * 
	 * @param id
	 *            Bestellidentifikator
	 * @return Bestellung
	 */
	@GET
	@Path( "{id}" )
	Order find( @PathParam( "id" ) int id );

	/**
	 * Lösche Bestellung.
	 * 
	 * @param id
	 *            Bestellidentifikator
	 */
	@DELETE
	@Path( "{id}" )
	void remove( @PathParam( "id" ) int id );

	/**
	 * Setze Bestell-Stage als Ausgeliefert.
	 * 
	 * @param id
	 *            Bestellidentifikator
	 * @return bearbeitete Bestellung
	 */
	@PUT
	@Path( "{id}/delivered" )
	Order updateOrderToDelivered( @PathParam( "id" ) int id );
}
