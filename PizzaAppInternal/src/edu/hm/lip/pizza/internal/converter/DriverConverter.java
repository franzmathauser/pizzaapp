package edu.hm.lip.pizza.internal.converter;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.internal.object.entity.EntityDriver;

/**
 * Konverter-Klasse fuer Driver-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser, Stefan Wörner
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
		if (eDriver == null)
		{
			return null;
		}

		Driver driver = new Driver();
		driver.setId( eDriver.getId() );
		driver.setName( eDriver.getName() );
		return driver;
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
		if (driver == null)
		{
			return null;
		}

		EntityDriver eDriver = new EntityDriver();
		eDriver.setId( driver.getId() );
		eDriver.setName( driver.getName() );
		return eDriver;
	}

	/**
	 * Konvertiert eine Liste mit Fahrern aus dem Entity-Datenmodell in eine Liste mit Service-Modell.
	 * 
	 * @param eDriverList
	 *            List mit Fahrern aus dem Entity-Modell
	 * @return Liste mit Fahrern aus dem Service-Modell
	 */
	public static List<Driver> convertEntityToServiceDriverList( List<EntityDriver> eDriverList )
	{
		if (eDriverList == null || eDriverList.isEmpty())
		{
			return null;
		}

		List<Driver> driverList = new ArrayList<Driver>();

		for (EntityDriver eDriver : eDriverList)
		{
			driverList.add( convertEntityToServiceDriver( eDriver ) );
		}

		return driverList;
	}

}
