package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

/**
 * Testklasse für den GPSData REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class GPSDataServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * Legt das übergebene GPS Datum an.
	 * 
	 * @param gpsData
	 *            Anzulegendes GPS Datum
	 * @param driver
	 *            Fahrer
	 * @param log
	 *            Ausgabe ins Log
	 * @return Angelegtes GPS Datum
	 */
	protected static GPSData createGPSData( GPSData gpsData, Driver driver, boolean log )
	{
		try
		{
			Thread.sleep( 1000 );
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		completeGPSData( gpsData, driver );

		getDriverProxy().createGPSData( driver.getId(), gpsData );

		if (log)
		{
			log( GPSDataServiceTest.class, "Create", gpsData.toString() );
		}

		List<GPSData> gpsDataListFound = getGPSDataProxy().findAll();
		Assert.assertNotNull( gpsDataListFound );
		Assert.assertTrue( gpsDataListFound.size() >= 1 );

		GPSData gpsDataFound = new GPSData();
		gpsDataFound.setId( null );
		gpsDataFound.setDate( gpsData.getDate() );
		gpsDataFound.setDriver( gpsData.getDriver() );
		gpsDataFound.setLat( gpsData.getLat() );
		gpsDataFound.setLon( gpsData.getLon() );

		assertContainsGPSData( gpsDataListFound, gpsDataFound, false, true );

		return gpsDataFound;
	}

	/**
	 * Löscht das übergebene GPS Datum.
	 * 
	 * @param gpsDataCreated
	 *            Zu löschendes GPS Datum
	 * @param log
	 *            Ausgabe ins Log
	 */
	protected static void deleteGPSData( GPSData gpsDataCreated, boolean log )
	{
		getGPSDataProxy().remove( gpsDataCreated.getId() );

		if (log)
		{
			log( GPSDataServiceTest.class, "Remove", gpsDataCreated.toString() );
		}

		GPSData gpsDataFound = getGPSDataProxy().find( gpsDataCreated.getId() );
		Assert.assertNull( gpsDataFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testCreate()
	 */
	@Override
	public void testCreate() throws Exception
	{
		// Nicht implementiert!
		return;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
	{
		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = DriverServiceTest.createDriver( getDriver(), false );

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		List<GPSData> gpsDataListCreated = new ArrayList<GPSData>();

		for (GPSData gpsData : getGPSDataList())
		{
			GPSData gpsDataCreated = createGPSData( gpsData, driverCreated, false );
			gpsDataListCreated.add( gpsDataCreated );
		}

		// ==================================================
		// GPS Datum auslesen
		// ==================================================
		List<GPSData> gpsDataListFound = getGPSDataProxy().findAll();
		Assert.assertNotNull( gpsDataListFound );

		for (GPSData gpsDataFound : gpsDataListFound)
		{
			log( this.getClass(), "Find_All", gpsDataFound.toString() );
		}

		Assert.assertTrue( gpsDataListFound.size() >= gpsDataListCreated.size() );
		for (GPSData gpsDataCreated : gpsDataListCreated)
		{
			assertContainsGPSData( gpsDataListFound, gpsDataCreated, false, false );
		}

		// ==================================================
		// GPS Datum löschen
		// ==================================================
		for (GPSData gpsDataCreated : gpsDataListCreated)
		{
			deleteGPSData( gpsDataCreated, false );
		}

		// ==================================================
		// Fahrer löschen
		// ==================================================
		DriverServiceTest.deleteDriver( driverCreated, false );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFind()
	 */
	@Test
	@Override
	public void testFind() throws Exception
	{
		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = DriverServiceTest.createDriver( getDriver(), false );

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		GPSData gpsDataCreated = createGPSData( getGPSData(), driverCreated, false );

		// ==================================================
		// GPS Datum auslesen
		// ==================================================
		GPSData gpsDataFound = getGPSDataProxy().find( gpsDataCreated.getId() );
		Assert.assertNotNull( gpsDataFound );

		log( this.getClass(), "Find", gpsDataFound.toString() );

		assertGPSDataEquals( gpsDataFound, gpsDataCreated, true );

		// ==================================================
		// GPS Datum löschen
		// ==================================================
		deleteGPSData( gpsDataCreated, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		DriverServiceTest.deleteDriver( driverCreated, false );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testUpdate()
	 */
	@Override
	public void testUpdate() throws Exception
	{
		// Nicht implementiert!
		return;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testRemove()
	 */
	@Test
	@Override
	public void testRemove() throws Exception
	{
		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = DriverServiceTest.createDriver( getDriver(), false );

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		GPSData gpsDataCreated = createGPSData( getGPSData(), driverCreated, false );

		// ==================================================
		// GPS Datum löschen
		// ==================================================
		deleteGPSData( gpsDataCreated, true );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		DriverServiceTest.deleteDriver( driverCreated, false );
	}

	/**
	 * Testet die FIND DRIVERS LAST POSITIONS Funktion.
	 */
	@Test
	public void findDriversLastPositions()
	{
		// ==================================================
		// Fahrer anlegen
		// ==================================================
		List<Driver> driversCreated = new ArrayList<Driver>();

		for (Driver driver : getDriverList())
		{
			Driver driverCreated = DriverServiceTest.createDriver( driver, false );
			driversCreated.add( driverCreated );
		}

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		List<GPSData> gpsDataListCreated = new ArrayList<GPSData>();

		for (GPSData gpsData : getGPSDataList())
		{
			for (Driver driverCreated : driversCreated)
			{
				GPSData gpsDataCreated = createGPSData( gpsData, driverCreated, false );
				gpsDataListCreated.add( gpsDataCreated );
			}
		}

		// ==================================================
		// Letzte Positionen auslesen
		// ==================================================
		List<GPSData> gpsDataListLastPositions = getGPSDataProxy().findDriversLastPositions();
		Assert.assertNotNull( gpsDataListLastPositions );

		for (GPSData gpsDataLastPosition : gpsDataListLastPositions)
		{
			log( this.getClass(), "Find_Dirvers_Last_Positions", gpsDataLastPosition.toString() );
		}

		Assert.assertTrue( gpsDataListLastPositions.size() >= driversCreated.size() );
		for (GPSData gpsDataLastPosition : gpsDataListLastPositions)
		{
			for (Driver driverCreated : driversCreated)
			{
				if (driverCreated.getId().intValue() == gpsDataLastPosition.getDriver().getId().intValue())
				{
					Assert.assertEquals( getGPSDataList().get( getGPSDataList().size() - 1 ).getLat(),
							gpsDataLastPosition.getLat() );
					Assert.assertEquals( getGPSDataList().get( getGPSDataList().size() - 1 ).getLon(),
							gpsDataLastPosition.getLon() );
					break;
				}
			}
		}

		// ==================================================
		// Fahrer und GPS Daten löschen
		// ==================================================
		for (Driver driverCreated : driversCreated)
		{
			DriverServiceTest.deleteDriver( driverCreated, false );
		}
	}

}
