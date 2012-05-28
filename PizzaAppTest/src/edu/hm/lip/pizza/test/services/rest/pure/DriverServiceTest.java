package edu.hm.lip.pizza.test.services.rest.pure;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.test.JsonMapper;
import edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class DriverServiceTest extends AbstractRestServiceTest implements IRestServiceDefaultTestFunctions
{

	private ClientRequest getDriverClient( String path )
	{
		ClientRequest request = new ClientRequest( getRestUrl() + "/" + path );
		request.accept( MediaType.APPLICATION_JSON );

		return request;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testCreate()
	 */
	@Test
	@Override
	public void testCreate() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		request = getDriverClient( "drivers" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driver ) );

		response = request.post( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		Driver driverCreated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNotNull( driverCreated );

		log( this.getClass(), "Create", driverCreated.toString() );

		Assert.assertNotNull( driverCreated.getId() );
		Assert.assertEquals( driverCreated.getName(), driver.getName() );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		response = getDriverClient( "drivers/" + driverCreated.getId() ).delete( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		response = getDriverClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNull( driverFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		List<Driver> driversCreated = new ArrayList<Driver>();

		for (Driver driver : getDriverList())
		{
			request = getDriverClient( "drivers" );
			request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driver ) );

			response = request.post( String.class );
			Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

			Driver driverCreated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
			Assert.assertNotNull( driverCreated );
			Assert.assertNotNull( driverCreated.getId() );
			Assert.assertEquals( driverCreated.getName(), driver.getName() );

			driversCreated.add( driverCreated );
		}

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		response = getDriverClient( "drivers" ).get( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		List<Driver> driversFound = JsonMapper.fromJSONArray( response.getEntity(), Driver.class );
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
			response = getDriverClient( "drivers/" + driverCreated.getId() ).delete( String.class );
			Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

			response = getDriverClient( "drivers/" + driverCreated.getId() ).get( String.class );
			Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

			Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		request = getDriverClient( "drivers" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driver ) );

		response = request.post( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		Driver driverCreated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		Assert.assertEquals( driverCreated.getName(), driver.getName() );

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		response = getDriverClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNotNull( driverFound );

		log( this.getClass(), "Find", driverFound.toString() );

		Assert.assertEquals( driverFound, driverCreated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		response = getDriverClient( "drivers/" + driverCreated.getId() ).delete( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		response = getDriverClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		request = getDriverClient( "drivers" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driver ) );

		response = request.post( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		Driver driverCreated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		Assert.assertEquals( driverCreated.getName(), driver.getName() );

		// ==================================================
		// Fahrer aktualisieren
		// ==================================================
		driverCreated.setName( driver.getName() + "_Updated" );

		request = getDriverClient( "drivers/" + driverCreated.getId() );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driverCreated ) );

		response = request.put( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		Driver driverUpdated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNotNull( driverUpdated );

		log( this.getClass(), "Update", driverUpdated.toString() );

		Assert.assertEquals( driverUpdated.getName(), driverCreated.getName() );
		Assert.assertEquals( driverUpdated, driverCreated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		response = getDriverClient( "drivers/" + driverCreated.getId() ).delete( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		response = getDriverClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		request = getDriverClient( "drivers" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driver ) );

		response = request.post( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_OK );

		Driver driverCreated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		Assert.assertEquals( driverCreated.getName(), driver.getName() );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		response = getDriverClient( "drivers/" + driverCreated.getId() ).delete( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		log( this.getClass(), "Remove", driverCreated.toString() );

		response = getDriverClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( response.getStatus(), HttpStatus.SC_NO_CONTENT );

		Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
		Assert.assertNull( driverFound );
	}

	/**
	 * Testet die GET ROUTE Funktion.
	 */
	@Test
	public void testGetRoute()
	{

	}

	/**
	 * Testet die ADD ORDER Funktion.
	 */
	@Test
	public void testAddOrder()
	{

	}

	/**
	 * Testet die REMOVE ORDER Funktion.
	 */
	@Test
	public void testRemoveOrder()
	{

	}

	/**
	 * Testet die CREATE GPS DATA Funktion.
	 */
	@Test
	public void testCreateGPSData()
	{

	}

	/**
	 * Testet die GET UNDELIVERED ORDERS Funktion.
	 */
	@Test
	public void testGetUndeliveredOrders()
	{

	}

}
