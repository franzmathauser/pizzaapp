package edu.hm.lip.pizza.internal.converter;

import edu.hm.lip.pizza.api.object.ressources.Driver;
import edu.hm.lip.pizza.internal.object.entities.EntityDriver;

/**
 * Konverter-Klasse fuer Driver-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser
 */
public final class DriverConverter
{

	private DriverConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Driver-Objekt in ein Driver-Objekt im Service-Datenmodell.
	 * 
	 * @param eDriver
	 *            Entity-Driver-Objekt
	 * @return Driver-Objekt aus dem Service-Datenmodell
	 */
	public static Driver convertEntityToServiceDriver( EntityDriver eDriver )
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Konvertiert ein Driver-Objekt aus dem Service-Datenmodell in ein Entity-Driver-Objekt.
	 * 
	 * @param driver
	 *            Driver-Objekt aus dem Service-Datenmodell
	 * @return Entity-Driver-Objekt
	 */
	public static EntityDriver convertServiceToEntityDriver( Driver driver )
	{
		// TODO Auto-generated method stub
		return null;
	}

}
