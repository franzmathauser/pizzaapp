package edu.hm.lip.pizza.api.communication.request;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.hm.lip.pizza.api.object.resource.heatmap.Heatmap;

/**
 * REST-Service für die Statistik-Daten. Verfügbare Aktionen: GET
 * 
 * @author Franz Mathauser
 */
@Local
@Path( "/statistics" )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public interface IStatisticService
{

	/**
	 * Liste aller GPS-Daten der Kundenbestellungen.
	 * 
	 * @return Kundenliste
	 */
	@GET
	@Path( "heatmap" )
	Heatmap findAllHeatmapData();

}
