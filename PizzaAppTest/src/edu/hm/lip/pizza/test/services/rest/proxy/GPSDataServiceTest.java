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
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		List<GPSData> gpsDataListCreated = new ArrayList<GPSData>();

		for (GPSData gpsData : getGPSDataList())
		{
			completeGPSData( gpsData, driverCreated );
			getDriverProxy().createGPSData( driverCreated.getId(), gpsData );

			gpsDataListCreated.add( gpsData );
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
		// Fahrer und GPS Daten löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		GPSData gpsData = getGPSData();
		completeGPSData( gpsData, driverCreated );

		getDriverProxy().createGPSData( driverCreated.getId(), gpsData );

		// ==================================================
		// GPSDaten prüfen
		// ==================================================
		List<GPSData> gpsDataListFound = getGPSDataProxy().findAll();
		Assert.assertNotNull( gpsDataListFound );
		Assert.assertTrue( gpsDataListFound.size() >= 1 );
		assertContainsGPSData( gpsDataListFound, gpsData, false, true );

		// ==================================================
		// GPS Datum auslesen
		// ==================================================
		GPSData gpsDataFound = getGPSDataProxy().find( gpsData.getId() );
		Assert.assertNotNull( gpsDataFound );

		log( this.getClass(), "Find", gpsDataFound.toString() );

		assertGPSDataEquals( gpsDataFound, gpsData, true );

		// ==================================================
		// Fahrer und GPS Daten löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// GPS Datum anlegen
		// ==================================================
		GPSData gpsData = getGPSData();
		completeGPSData( gpsData, driverCreated );

		getDriverProxy().createGPSData( driverCreated.getId(), gpsData );

		// ==================================================
		// GPSDaten prüfen
		// ==================================================
		List<GPSData> gpsDataListFound = getGPSDataProxy().findAll();
		Assert.assertNotNull( gpsDataListFound );
		Assert.assertTrue( gpsDataListFound.size() >= 1 );
		assertContainsGPSData( gpsDataListFound, gpsData, false, true );

		// ==================================================
		// GPS Datum löschen
		// ==================================================
		getGPSDataProxy().remove( gpsData.getId() );

		log( this.getClass(), "Remove", gpsData.toString() );

		GPSData gpsDataFound = getGPSDataProxy().find( gpsData.getId() );
		Assert.assertNull( gpsDataFound );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
			Driver driverCreated = getDriverProxy().create( driver );
			Assert.assertNotNull( driverCreated );
			Assert.assertNotNull( driverCreated.getId() );
			assertDriverEquals( driverCreated, driver, false );

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
				completeGPSData( gpsData, driverCreated );
				getDriverProxy().createGPSData( driverCreated.getId(), gpsData );

				gpsDataListCreated.add( gpsData );

				try
				{
					Thread.sleep( 1000 );
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		// ==================================================
		// GPS Datum auslesen
		// ==================================================
		List<GPSData> gpsDataListFound = getGPSDataProxy().findAll();
		Assert.assertNotNull( gpsDataListFound );
		Assert.assertTrue( gpsDataListFound.size() >= gpsDataListCreated.size() );
		for (GPSData gpsDataCreated : gpsDataListCreated)
		{
			assertContainsGPSData( gpsDataListFound, gpsDataCreated, false, true );
		}

		// ==================================================
		// Letzte Positionen auslesen
		// ==================================================
		List<GPSData> gpsDataListLastPositions = getGPSDataProxy().findDriversLastPositions();
		Assert.assertNotNull( gpsDataListFound );

		for (GPSData gpsDataLastPosition : gpsDataListLastPositions)
		{
			log( this.getClass(), "Find_Dirvers_Last_Positions", gpsDataLastPosition.toString() );
		}

		Assert.assertTrue( gpsDataListFound.size() >= driversCreated.size() );
		for (GPSData gpsDataLastPosition : gpsDataListLastPositions)
		{
			assertContainsGPSData( gpsDataListFound, gpsDataLastPosition, false, false );
			Assert.assertEquals( gpsDataLastPosition.getLat(), getGPSDataList().get( getGPSDataList().size() - 1 ).getLat() );
			Assert.assertEquals( gpsDataLastPosition.getLon(), getGPSDataList().get( getGPSDataList().size() - 1 ).getLon() );
		}

		// ==================================================
		// Fahrer und GPS Daten löschen
		// ==================================================
		for (Driver driverCreated : driversCreated)
		{
			getDriverProxy().remove( driverCreated.getId() );

			Driver driverFound = getDriverProxy().find( driverCreated.getId() );
			Assert.assertNull( driverFound );
		}
	}

}
