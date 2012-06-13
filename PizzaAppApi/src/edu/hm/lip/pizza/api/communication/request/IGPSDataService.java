package edu.hm.lip.pizza.api.communication.request;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.hm.lip.pizza.api.object.resource.GPSData;

/**
 * REST-Service für die GPS-Datendomäne. Verfügbare Aktionen: GET, DELETE
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
@Path( "/gpsdatas" )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public interface IGPSDataService
{

	/**
	 * Liste aller GPS Daten.
	 * 
	 * @return GPSDaten-Liste
	 */
	@GET
	@Path( "" )
	List<GPSData> findAll();

	/**
	 * Liste der letzten GPS Positionen aller Fahrer.
	 * 
	 * @return GPS-Data-Liste
	 */
	@GET
	@Path( "/drivers" )
	List<GPSData> findDriversLastPositions();

	/**
	 * Liefere GPS-Datum anhand einer id.
	 * 
	 * @param id
	 *            GPS-Data-Identifikator
	 * @return GPS-Datum
	 */
	@GET
	@Path( "{id}" )
	GPSData find( @PathParam( "id" ) int id );

	/**
	 * Löschen eines GPS-Datums.
	 * 
	 * @param id
	 *            GPS-Data-Identifikator
	 */
	@DELETE
	@Path( "{id}" )
	void remove( @PathParam( "id" ) int id );

}
