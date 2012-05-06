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

import edu.hm.lip.pizza.api.object.ressources.GPSData;

/**
 * 
 * REST-Service für die GPS-Datendomäne. Verfügbare Aktionen: GET, POST, PUT, DELETE
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/gpsdatas" )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public interface IGPSDataServiceLocal
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
	 * Erzeugt ein neues GPS-Datum.
	 * 
	 * @param gpsData
	 *            GPS-Datum
	 * @return erzeugtes GPS-Datum
	 */
	@POST
	@Path( "" )
	GPSData create( GPSData gpsData );

	/**
	 * Liste der letzten GPS Positionen der Fahrer.
	 * 
	 * @return GPS-Data-Liste
	 */
	@GET
	@Path( "/drivers" )
	List<GPSData> findDriverPositions();

	/**
	 * Letzte GPS Position des Fahrers.
	 * 
	 * @param id
	 *            Fahreridentifikation
	 * @return GPS-Datum
	 */
	@GET
	@Path( "/drivers/{id}" )
	GPSData findDriverPositions( @PathParam( "id" ) int id );

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
