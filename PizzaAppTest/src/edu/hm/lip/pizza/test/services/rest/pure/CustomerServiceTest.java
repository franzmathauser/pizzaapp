package edu.hm.lip.pizza.test.services.rest.pure;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.Gender;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.test.JsonMapper;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class CustomerServiceTest extends AbstractRestServicePureTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * Legt den übergebenen Kunde an.
	 * 
	 * @param customer
	 *            Anzulegender Kunde
	 * @param log
	 *            Ausgabe ins Log
	 * @return Angelegter Kunde
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static Customer createCustomer( Customer customer, boolean log ) throws Exception
	{
		ClientRequest request = getClient( "customers" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( customer ) );

		ClientResponse<String> response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Customer customerCreated = JsonMapper.fromJSON( response.getEntity(), Customer.class );
		Assert.assertNotNull( customerCreated );
		updateCustomerCoordinates( customerCreated, customer );

		if (log)
		{
			log( CustomerServiceTest.class, "Create", customerCreated.toString() );
		}

		Assert.assertNotNull( customerCreated.getId() );
		assertCustomerEquals( customerCreated, customer, false );

		return customerCreated;
	}

	/**
	 * Löscht den übergebenen Kunde.
	 * 
	 * @param customerCreated
	 *            Zu löschender Kunde
	 * @param log
	 *            Ausgabe ins Log
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static void deleteCustomer( Customer customerCreated, boolean log ) throws Exception
	{
		try
		{
			Thread.sleep( 2000 );
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		ClientResponse<String> response = getClient( "customers/" + customerCreated.getId() ).delete( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		if (log)
		{
			log( CustomerServiceTest.class, "Remove", customerCreated.toString() );
		}

		response = getClient( "customers/" + customerCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		Customer customerFound = JsonMapper.fromJSON( response.getEntity(), Customer.class );
		Assert.assertNull( customerFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testCreate()
	 */
	@Test
	@Override
	public void testCreate() throws Exception
	{
		// ==================================================
		// Kunde anlegen
		// ==================================================
		Customer customerCreated = createCustomer( getCustomer(), true );

		// ==================================================
		// Kunde löschen
		// ==================================================
		deleteCustomer( customerCreated, false );
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
		// Kunde anlegen
		// ==================================================
		List<Customer> customersCreated = new ArrayList<Customer>();

		for (Customer customer : getCustomerList())
		{
			Customer customerCreated = createCustomer( customer, false );
			customersCreated.add( customerCreated );
		}

		// ==================================================
		// Kunde auslesen
		// ==================================================
		response = getClient( "customers" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Customer> customersFound = JsonMapper.fromJSONArray( response.getEntity(), Customer.class );
		Assert.assertNotNull( customersFound );

		for (Customer customerFound : customersFound)
		{
			log( this.getClass(), "Find_All", customerFound.toString() );
		}

		Assert.assertTrue( customersFound.size() >= customersCreated.size() );
		for (Customer customerCreated : customersCreated)
		{
			Assert.assertTrue( customersFound.contains( customerCreated ) );
		}

		// ==================================================
		// Kunde löschen
		// ==================================================
		for (Customer customerCreated : customersCreated)
		{
			deleteCustomer( customerCreated, false );
		}
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
		// Kunde anlegen
		// ==================================================
		Customer customerCreated = createCustomer( getCustomer(), false );

		// ==================================================
		// Kunde auslesen
		// ==================================================
		response = getClient( "customers/" + customerCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Customer customerFound = JsonMapper.fromJSON( response.getEntity(), Customer.class );
		Assert.assertNotNull( customerFound );

		log( this.getClass(), "Find", customerFound.toString() );

		Assert.assertEquals( customerCreated, customerFound );

		// ==================================================
		// Kunde löschen
		// ==================================================
		deleteCustomer( customerCreated, false );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testUpdate()
	 */
	@Test
	@Override
	public void testUpdate() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Kunde anlegen
		// ==================================================
		Customer customer = getCustomer();
		Customer customerCreated = createCustomer( customer, false );

		// ==================================================
		// Kunde aktualisieren
		// ==================================================
		customerCreated.setLastname( customer.getLastname() + "_Updated" );
		customerCreated.setForename( customer.getForename() + "_Updated" );
		customerCreated.setCity( customer.getCity() + "_Updated" );
		customerCreated.setCompany( customer.getCompany() + "_Updated" );
		customerCreated.setDepartment( customer.getDepartment() + "_Updated" );
		customerCreated.setEmail( customer.getEmail() + "_Updated" );
		customerCreated.setGender( Gender.FEMALE );
		customerCreated.setLevel( customer.getLevel() + "_Updated" );
		customerCreated.setPhone( customer.getPhone() + "_Updated" );
		customerCreated.setStreet( customer.getStreet() + "_Updated" );
		customerCreated.setZipcode( "" + (Integer.parseInt( customer.getZipcode() ) + 10) );

		request = getClient( "customers/" + customerCreated.getId() );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( customerCreated ) );

		response = request.put( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Customer customerUpdated = JsonMapper.fromJSON( response.getEntity(), Customer.class );
		Assert.assertNotNull( customerUpdated );

		log( this.getClass(), "Update", customerUpdated.toString() );

		Assert.assertEquals( customerCreated, customerUpdated );

		// ==================================================
		// Kunde löschen
		// ==================================================
		deleteCustomer( customerCreated, false );
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
		// Kunde anlegen
		// ==================================================
		Customer customerCreated = createCustomer( getCustomer(), false );

		// ==================================================
		// Kunde löschen
		// ==================================================
		deleteCustomer( customerCreated, true );
	}

}
