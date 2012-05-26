package edu.hm.lip.pizza.api.communication.request;

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
import javax.ws.rs.core.MediaType;

import edu.hm.lip.pizza.api.object.resource.Order;

/**
 * REST-Service für die Bestelldomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/orders" )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public interface IOrderService
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

	/**
	 * Liste aller Bestellungen, die sich noch in der Backstube befinden.
	 * 
	 * @return Order-Liste
	 */
	@GET
	@Path( "/undelivered" )
	List<Order> getUndeliveredOrders();

	/**
	 * Führt Bestellung in die nächste Stage über.
	 * 
	 * @param id
	 *            Bestellidentifikator
	 * @return Bestellung mit neuer Stage
	 */
	@POST
	@Path( "{id}/stages" )
	Order createNextOrderStage( @PathParam( "id" ) int id );

	/**
	 * Führt Bestellung in die nächste Stage über.
	 * 
	 * @param id
	 *            Bestellidentifikator
	 * @return Bestellung mit neuer Stage
	 */
	@GET
	@Path( "{id}/stages" )
	String getNextOrderStage( @PathParam( "id" ) int id );

}
