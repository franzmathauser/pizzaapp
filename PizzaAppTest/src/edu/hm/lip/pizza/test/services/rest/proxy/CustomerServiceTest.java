package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.Gender;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Customer REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class CustomerServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
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
		Customer customerCreated = getCustomerProxy().create( customer );
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

		getCustomerProxy().remove( customerCreated.getId() );

		if (log)
		{
			log( CustomerServiceTest.class, "Remove", customerCreated.toString() );
		}

		Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
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
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
	{
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
		List<Customer> customersFound = getCustomerProxy().findAll();
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
		// ==================================================
		// Kunde anlegen
		// ==================================================
		Customer customerCreated = createCustomer( getCustomer(), false );

		// ==================================================
		// Kunde auslesen
		// ==================================================
		Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
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

		Customer customerUpdated = getCustomerProxy().update( customerCreated.getId(), customerCreated );
		Assert.assertNotNull( customerUpdated );

		log( this.getClass(), "Update", customerUpdated.toString() );

		Assert.assertEquals( customerCreated, customerUpdated );

		// / ==================================================
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
