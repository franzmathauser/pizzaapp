package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class DriverServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testCreate()
	 */
	@Test
	@Override
	public void testCreate() throws Exception
	{
		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );

		log( this.getClass(), "Create", driverCreated.toString() );

		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
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
		// Fahrer auslesen
		// ==================================================
		List<Driver> driversFound = getDriverProxy().findAll();
		Assert.assertNotNull( driversFound );

		for (Driver driverFound : driversFound)
		{
			log( this.getClass(), "Find_All", driverFound.toString() );
		}

		Assert.assertTrue( driversFound.size() >= driversCreated.size() );
		for (Driver driverCreated : driversCreated)
		{
			Assert.assertTrue( driversFound.contains( driverCreated ) );
		}

		// ==================================================
		// Fahrer löschen
		// ==================================================
		for (Driver driverCreated : driversCreated)
		{
			getDriverProxy().remove( driverCreated.getId() );

			Driver driverFound = getDriverProxy().find( driverCreated.getId() );
			Assert.assertNull( driverFound );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testFind()
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
		// Fahrer auslesen
		// ==================================================
		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNotNull( driverFound );

		log( this.getClass(), "Find", driverFound.toString() );

		Assert.assertEquals( driverFound, driverCreated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testUpdate()
	 */
	@Test
	@Override
	public void testUpdate() throws Exception
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
		// Fahrer aktualisieren
		// ==================================================
		driverCreated.setName( driver.getName() + "_Updated" );
		// TODO update all attributes

		Driver driverUpdated = getDriverProxy().update( driverCreated.getId(), driverCreated );
		Assert.assertNotNull( driverUpdated );

		log( this.getClass(), "Update", driverUpdated.toString() );

		Assert.assertEquals( driverUpdated.getName(), driverCreated.getName() );
		Assert.assertEquals( driverUpdated, driverCreated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testRemove()
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
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		log( this.getClass(), "Remove", driverCreated.toString() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * Testet die GET ROUTE Funktion.
	 */
	@Test
	public void testGetRoute()
	{
		//TODO Implementieren
	}

	/**
	 * Testet die ADD ORDER Funktion.
	 */
	@Test
	public void testAddOrder()
	{
		//TODO Implementieren
	}

	/**
	 * Testet die REMOVE ORDER Funktion.
	 */
	@Test
	public void testRemoveOrder()
	{
		//TODO Implementieren
	}

	/**
	 * Testet die CREATE GPS DATA Funktion.
	 */
	@Test
	public void testCreateGPSData()
	{
		//TODO Implementieren
	}

	/**
	 * Testet die GET UNDELIVERED ORDERS Funktion.
	 */
	@Test
	public void testGetUndeliveredOrders()
	{
		//TODO Implementieren
	}

}
