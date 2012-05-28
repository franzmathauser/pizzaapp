package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class DriverServiceTest extends AbstractRestServiceTest
{

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testCreate()
	 */
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
		assertDriverEqualsWithoutId( driverCreated, driver );

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
			assertDriverEqualsWithoutId( driverCreated, driver );

			driversCreated.add( driverCreated );
		}

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		List<Driver> driversFound = getDriverProxy().findAll();
		Assert.assertNotNull( driversFound );

		for (Driver driverFound : driversFound)
		{
			log( this.getClass(), "FindAll", driverFound.toString() );
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
		assertDriverEqualsWithoutId( driverCreated, driver );

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
		assertDriverEqualsWithoutId( driverCreated, driver );

		// ==================================================
		// Fahrer aktualisieren
		// ==================================================
		driverCreated.setName( driver.getName() + "_Updated" );

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
		assertDriverEqualsWithoutId( driverCreated, driver );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		log( this.getClass(), "Remove", driverCreated.toString() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	@Test
	public void testGetRoute()
	{

	}

	@Test
	public void testAddOrder()
	{

	}

	@Test
	public void testRemoveOrder()
	{

	}

	@Test
	public void testCreateGPSData()
	{

	}

	@Test
	public void testGetUndeliveredOrders()
	{

	}

}
