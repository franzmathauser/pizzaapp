package edu.hm.lip.pizza.test.services.rest.pure;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.test.JsonMapper;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class GPSDataServiceTest extends AbstractRestServicePureTest implements IRestServiceDefaultTestFunctions
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
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static GPSData createGPSData( GPSData gpsData, Driver driver, boolean log ) throws Exception
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

		ClientRequest request = getClient( "drivers/" + driver.getId() + "/gpsdata" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( gpsData ) );

		ClientResponse<String> response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		if (log)
		{
			log( GPSDataServiceTest.class, "Create", gpsData.toString() );
		}

		response = getClient( "gpsdatas" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<GPSData> gpsDataListFound = JsonMapper.fromJSONArray( response.getEntity(), GPSData.class );
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
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static void deleteGPSData( GPSData gpsDataCreated, boolean log ) throws Exception
	{
		ClientResponse<String> response = getClient( "gpsdatas/" + gpsDataCreated.getId() ).delete( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		if (log)
		{
			log( GPSDataServiceTest.class, "Remove", gpsDataCreated.toString() );
		}

		response = getClient( "gpsdatas/" + gpsDataCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		GPSData gpsDataFound = JsonMapper.fromJSON( response.getEntity(), GPSData.class );
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
	 * @throws Exception
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
	{
		ClientResponse<String> response = null;

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
		response = getClient( "gpsdatas" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<GPSData> gpsDataListFound = JsonMapper.fromJSONArray( response.getEntity(), GPSData.class );
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
		ClientResponse<String> response = null;

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
		response = getClient( "gpsdatas/" + gpsDataCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		GPSData gpsDataFound = JsonMapper.fromJSON( response.getEntity(), GPSData.class );
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
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void findDriversLastPositions() throws Exception
	{
		ClientResponse<String> response = null;

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
		response = getClient( "gpsdatas/drivers" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<GPSData> gpsDataListLastPositions = JsonMapper.fromJSONArray( response.getEntity(), GPSData.class );
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
