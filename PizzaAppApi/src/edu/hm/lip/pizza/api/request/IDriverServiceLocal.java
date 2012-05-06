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

/**
 * REST-Service für die Fahrerdomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/drivers" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IDriverServiceLocal
{

	/**
	 * Liste aller Fahrer.
	 * 
	 * @return Fahrerliste
	 */
	@GET
	@Path( "" )
	List<Driver> findAll();

	/**
	 * Erzeugt ein neuen Fahrer.
	 * 
	 * @param driver
	 *            Fahrer
	 * @return erzeugten Fahrer
	 */
	@POST
	@Path( "" )
	Driver create( Driver driver );

	/**
	 * Liefere Fahrer anhand von id.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @return Fahrer
	 */
	@GET
	@Path( "{id}" )
	Driver find( @PathParam( "id" ) int id );

	/**
	 * Liste der auszuliefernden Bestellungen in optimierter Reihenfolge für einen Fahrer mit id.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @return Bestellungsliste
	 */
	@GET
	@Path( "{id}/route" )
	@QueryParam( "" )
	List<Order> getRoute( @PathParam( "id" ) int id );

	/**
	 * Fügt eine Bestellungen einem Fahrer hinzu.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @return Bestellungsliste
	 */
	@POST
	@Path( "{id}/orders" )
	List<Order> getOrders( @PathParam( "id" ) int id );

	/**
	 * Entferne zugeordnete Bestellung von Fahrer.
	 * 
	 * @param dId
	 *            Fahreridentifikation
	 * @param oId
	 *            Bestellungsidentifikation
	 */
	@DELETE
	@Path( "{dId}/orders/{oId}" )
	void remove( @PathParam( "dId" ) int dId, @PathParam( "oId" ) int oId );

}
