package edu.hm.lip.pizza.internal.converter;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.internal.object.entity.EntityGPSData;

/**
 * Konverter-Klasse fuer GPSData-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser
 */
public final class GPSDataConverter
{

	private GPSDataConverter()
	{

	}

	/**
	 * Konvertiert das Entity-GPSData-Objekt in ein GPSData-Objekt im Service-Datenmodell.
	 * 
	 * @param eGPSData
	 *            Entity-GPSData-Objekt
	 * @return GPSData-Objekt aus dem Service-Datenmodell
	 */
	public static GPSData convertEntityToServiceGPSData( EntityGPSData eGPSData )
	{
		GPSData gpsData = new GPSData();

		gpsData.setId( eGPSData.getId() );
		gpsData.setLat( eGPSData.getLat() );
		gpsData.setLon( eGPSData.getLon() );
		gpsData.setDate( eGPSData.getDate() );
		gpsData.setDriver( DriverConverter.convertEntityToServiceDriver( eGPSData.getDriver() ) );

		return gpsData;
	}

	/**
	 * Konvertiert ein GPSData-Objekt aus dem Service-Datenmodell in ein Entity-GPSData-Objekt.
	 * 
	 * @param gpsData
	 *            GPSData-Objekt aus dem Service-Datenmodell
	 * @return Entity-GPSData-Objekt
	 */
	public static EntityGPSData convertServiceToEntityGPSData( GPSData gpsData )
	{
		EntityGPSData eGPSData = new EntityGPSData();

		eGPSData.setId( gpsData.getId() );
		eGPSData.setLat( gpsData.getLat() );
		eGPSData.setLon( gpsData.getLon() );
		eGPSData.setDate( gpsData.getDate() );
		if (gpsData.getDriver() != null)
		{
			eGPSData.setDriver( DriverConverter.convertServiceToEntityDriver( gpsData.getDriver() ) );
		}

		return eGPSData;
	}

	/**
	 * Konvertiert eine Liste von Entity-GPSData-Objekten in eine Liste von GPSData-Objekten im Service-Datenmodell.
	 * 
	 * @param eGPSDataList
	 *            Liste von Entity-GPSData-Objekten
	 * @return Liste von GPSData-Objekten
	 */
	public static List<GPSData> convertEntityToServiceGPSDataList( List<EntityGPSData> eGPSDataList )
	{
		List<GPSData> gpsDatas = new ArrayList<GPSData>();

		for (EntityGPSData eGPSData : eGPSDataList)
		{
			gpsDatas.add( GPSDataConverter.convertEntityToServiceGPSData( eGPSData ) );
		}
		return gpsDatas;
	}
}
