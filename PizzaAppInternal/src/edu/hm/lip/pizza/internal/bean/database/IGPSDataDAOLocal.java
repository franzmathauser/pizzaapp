package edu.hm.lip.pizza.internal.bean.database;

import java.util.List;

import javax.ejb.Local;

import edu.hm.lip.pizza.internal.object.entity.EntityGPSData;

/**
 * Interface der Bean für den Datenbankzugriff auf die GPSData Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Local
public interface IGPSDataDAOLocal
{

	/**
	 * Estellt ein neues GPS-Datum in der Datenbank.
	 * 
	 * @param gpsData
	 *            Zu erstellendes GPS-Datum
	 * @return Erstelltes GPS-Datum
	 */
	EntityGPSData create( EntityGPSData gpsData );

	/**
	 * Liest alle GPS-Daten aus der Datenbank aus.
	 * 
	 * @return Liste mit allen GPS-Daten
	 */
	List<EntityGPSData> readAll();

	/**
	 * Liest ein GPS-Datum anhand des GPS-Datumsidentifikators aus der Datenbank aus.
	 * 
	 * @param id
	 *            GPS-Datumsidentifikator
	 * @return GPS-Datum
	 */
	EntityGPSData read( int id );

	/**
	 * Aktualisiert ein GPS-Datum in der Datenbank.
	 * 
	 * @param gpsData
	 *            Zu aktualisierendes GPS-Datum
	 * @return Aktualisertes GPS-Datum
	 */
	EntityGPSData update( EntityGPSData gpsData );

	/**
	 * Entfernt ein GPS-Datum aus der Datenbank.
	 * 
	 * @param gpsData
	 *            Zu entfernendes GPS-Datum
	 */
	void delete( EntityGPSData gpsData );

	/**
	 * Liest die letzten Positionen aller Fahrer aus.
	 * 
	 * @return Letzte Positionen der Fahrer.
	 */
	List<EntityGPSData> getDriversLastPositions();

}
