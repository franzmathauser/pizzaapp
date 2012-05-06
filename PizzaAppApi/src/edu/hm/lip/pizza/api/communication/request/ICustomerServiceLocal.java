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

import edu.hm.lip.pizza.api.object.resources.Customer;

/**
 * REST-Service für die Kundendomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/customers" )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public interface ICustomerServiceLocal
{

	/**
	 * Liste aller Kunden.
	 * 
	 * @return Kundenliste
	 */
	@GET
	@Path( "" )
	List<Customer> findAll();

	/**
	 * Erzeugt ein neues Kunden-Objekt.
	 * 
	 * @param customer
	 *            Kunde
	 * @return erzeugte Kunde
	 */
	@POST
	@Path( "" )
	Customer create( Customer customer );

	/**
	 * Liefere Kunde anhand seiner id.
	 * 
	 * @param id
	 *            Kundenidentifikator
	 * @return Kunde
	 */
	@GET
	@Path( "{id}" )
	Customer find( @PathParam( "id" ) int id );

	/**
	 * Überschreibe Kundendaten.
	 * 
	 * @param customer
	 *            Kunde
	 * @return Kunde
	 */
	@PUT
	@Path( "{id}" )
	Customer update( Customer customer );

	/**
	 * Kunden löschen.
	 * 
	 * @param id
	 *            Kundenidentifikator
	 */
	@DELETE
	@Path( "{id}" )
	void remove( @PathParam( "id" ) int id );

}
