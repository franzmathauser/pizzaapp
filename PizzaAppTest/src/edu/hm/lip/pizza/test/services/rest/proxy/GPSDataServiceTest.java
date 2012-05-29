package edu.hm.lip.pizza.test.services.rest.proxy;

import org.junit.Test;

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
		// TODO Implementieren

		// // ==================================================
		// // GPS Datum anlegen
		// // ==================================================
		// List<GPSData> gpsDatasCreated = new ArrayList<GPSData>();
		//
		// for (GPSData gpsData : getGPSDataList())
		// {
		// GPSData gpsDataCreated = getGPSDataProxy().create( gpsData );
		// Assert.assertNotNull( gpsDataCreated );
		// Assert.assertNotNull( gpsDataCreated.getId() );
		// assertGPSDataEquals( gpsDataCreated, gpsData, false );
		//
		// gpsDatasCreated.add( gpsDataCreated );
		// }
		//
		// // ==================================================
		// // GPS Datum auslesen
		// // ==================================================
		// List<GPSData> gpsDatasFound = getGPSDataProxy().findAll();
		// Assert.assertNotNull( gpsDatasFound );
		//
		// for (GPSData gpsDataFound : gpsDatasFound)
		// {
		// log( this.getClass(), "Find_All", gpsDataFound.toString() );
		// }
		//
		// Assert.assertTrue( gpsDatasFound.size() >= gpsDatasCreated.size() );
		// for (GPSData gpsDataCreated : gpsDatasCreated)
		// {
		// Assert.assertTrue( gpsDatasFound.contains( gpsDataCreated ) );
		// }
		//
		// // ==================================================
		// // GPS Datum löschen
		// // ==================================================
		// for (GPSData gpsDataCreated : gpsDatasCreated)
		// {
		// getGPSDataProxy().remove( gpsDataCreated.getId() );
		//
		// GPSData gpsDataFound = getGPSDataProxy().find( gpsDataCreated.getId() );
		// Assert.assertNull( gpsDataFound );
		// }
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
		// TODO Implementieren

		// // ==================================================
		// // GPS Datum anlegen
		// // ==================================================
		// GPSData gpsData = getGPSData();
		//
		// GPSData gpsDataCreated = getGPSDataProxy().create( gpsData );
		// Assert.assertNotNull( gpsDataCreated );
		// Assert.assertNotNull( gpsDataCreated.getId() );
		// assertGPSDataEquals( gpsDataCreated, gpsData, false );
		//
		// // ==================================================
		// // GPS Datum auslesen
		// // ==================================================
		// GPSData gpsDataFound = getGPSDataProxy().find( gpsDataCreated.getId() );
		// Assert.assertNotNull( gpsDataFound );
		//
		// log( this.getClass(), "Find", gpsDataFound.toString() );
		//
		// Assert.assertEquals( gpsDataFound, gpsDataCreated );
		//
		// // ==================================================
		// // GPS Datum löschen
		// // ==================================================
		// getGPSDataProxy().remove( gpsDataCreated.getId() );
		//
		// gpsDataFound = getGPSDataProxy().find( gpsDataCreated.getId() );
		// Assert.assertNull( gpsDataFound );
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
		// TODO Implementieren

		// // ==================================================
		// // GPS Datum anlegen
		// // ==================================================
		// GPSData gpsData = getGPSData();
		//
		// GPSData gpsDataCreated = getGPSDataProxy().create( gpsData );
		// Assert.assertNotNull( gpsDataCreated );
		// Assert.assertNotNull( gpsDataCreated.getId() );
		// assertGPSDataEquals( gpsDataCreated, gpsData, false );
		//
		// // ==================================================
		// // GPS Datum löschen
		// // ==================================================
		// getGPSDataProxy().remove( gpsDataCreated.getId() );
		//
		// log( this.getClass(), "Remove", gpsDataCreated.toString() );
		//
		// GPSData gpsDataFound = getGPSDataProxy().find( gpsDataCreated.getId() );
		// Assert.assertNull( gpsDataFound );
	}

	/**
	 * Testet die FIND DRIVERS LAST POSITIONS Funktion.
	 */
	@Test
	public void findDriversLastPositions()
	{
		// TODO Implementieren
	}

}
