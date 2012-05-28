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

import edu.hm.lip.pizza.api.object.resource.Product;

/**
 * REST-Service für die Produktdomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/products" )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public interface IProductService
{

	/**
	 * Liste aller Produkte.
	 * 
	 * @return Produktliste
	 */
	@GET
	@Path( "" )
	List<Product> findAll();

	/**
	 * Erzeugt ein neues Produkt.
	 * 
	 * @param product
	 *            Produkt
	 * @return erzeugte Produkt
	 */
	@POST
	@Path( "" )
	Product create( Product product );

	/**
	 * Liefere Product anhand von id.
	 * 
	 * @param id
	 *            Produktidentifikator
	 * @return Produkt
	 */
	@GET
	@Path( "{id}" )
	Product find( @PathParam( "id" ) int id );

	/**
	 * Überschreibe Produktdaten.
	 * 
	 * @param id
	 *            Produktidentifikator
	 * @param product
	 *            Produkt
	 * @return überschriebenes Produkt
	 */
	@PUT
	@Path( "{id}" )
	Product update( @PathParam( "id" ) int id, Product product );

	/**
	 * Lösche Produkt anhand von id.
	 * 
	 * @param id
	 *            Produktidentifikator
	 */
	@DELETE
	@Path( "{id}" )
	void remove( @PathParam( "id" ) int id );

}
