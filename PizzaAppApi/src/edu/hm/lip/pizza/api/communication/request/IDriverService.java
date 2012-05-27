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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.DriverRoute;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderId;

/**
 * REST-Service für die Fahrerdomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
@Path( "/drivers" )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public interface IDriverService
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
	 * Aktualisiert einen Fahrer.
	 * 
	 * @param id
	 *            Fahreridentifikator
	 * @param driver
	 *            Fahrer
	 * @return Aktualisierter Fahrer
	 */
	@PUT
	@Path( "{id}" )
	Driver update( @PathParam( "id" ) int id, Driver driver );

	/**
	 * Löscht den Fahrer.
	 * 
	 * @param id
	 *            Fahreridentifikator
	 */
	@DELETE
	@Path( "{id}" )
	void remove( @PathParam( "id" ) int id );

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
	DriverRoute getRoute( @PathParam( "id" ) int id );

	/**
	 * Fügt einem Fahrer eine Bestellung hinzu.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @param orderId
	 *            Bestellungsidentifikator
	 */
	@POST
	@Path( "{id}/orders" )
	void addOrder( @PathParam( "id" ) int id, OrderId orderId );

	/**
	 * Entferne zugeordnete Bestellung von Fahrer.
	 * 
	 * @param driverId
	 *            Fahreridentifikation
	 * @param orderId
	 *            Bestellungsidentifikation
	 */
	@DELETE
	@Path( "{dId}/orders/{oId}" )
	void removeOrder( @PathParam( "dId" ) int driverId, @PathParam( "oId" ) int orderId );

	/**
	 * Fügt einem Fahrer neue GPS-Daten hinzu.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @param gpsData
	 *            GPS-Daten
	 */
	@POST
	@Path( "{id}/gpsdata" )
	void createGPSData( @PathParam( "id" ) int id, GPSData gpsData );

	/**
	 * Liste der auszuliefernden Bestellungen in optimierter Reihenfolge für einen Fahrer mit id.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @return Bestellungsliste
	 */
	@GET
	@Path( "{id}/orders" )
	List<Order> getUndeliveredOrders( @PathParam( "id" ) int id );

}
