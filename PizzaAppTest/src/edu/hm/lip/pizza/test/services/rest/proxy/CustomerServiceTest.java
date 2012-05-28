package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testCreate()
	 */
	@Test
	@Override
	public void testCreate() throws Exception
	{
		// ==================================================
		// Kunde anlegen
		// ==================================================
		Customer customer = getCustomer();

		Customer customerCreated = getCustomerProxy().create( customer );
		Assert.assertNotNull( customerCreated );

		log( this.getClass(), "Create", customerCreated.toString() );

		Assert.assertNotNull( customerCreated.getId() );
		assertCustomerEquals( customerCreated, customer, false );

		// ==================================================
		// Kunde löschen
		// ==================================================
		getCustomerProxy().remove( customerCreated.getId() );

		Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
		Assert.assertNull( customerFound );
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
		// Kunde anlegen
		// ==================================================
		List<Customer> customersCreated = new ArrayList<Customer>();

		for (Customer customer : getCustomerList())
		{
			Customer customerCreated = getCustomerProxy().create( customer );
			Assert.assertNotNull( customerCreated );
			Assert.assertNotNull( customerCreated.getId() );
			assertCustomerEquals( customerCreated, customer, false );

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
			getCustomerProxy().remove( customerCreated.getId() );

			Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
			Assert.assertNull( customerFound );
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
		// Kunde anlegen
		// ==================================================
		Customer customer = getCustomer();

		Customer customerCreated = getCustomerProxy().create( customer );
		Assert.assertNotNull( customerCreated );
		Assert.assertNotNull( customerCreated.getId() );
		assertCustomerEquals( customerCreated, customer, false );

		// ==================================================
		// Kunde auslesen
		// ==================================================
		Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
		Assert.assertNotNull( customerFound );

		log( this.getClass(), "Find", customerFound.toString() );

		Assert.assertEquals( customerFound, customerCreated );

		// ==================================================
		// Kunde löschen
		// ==================================================
		getCustomerProxy().remove( customerCreated.getId() );

		customerFound = getCustomerProxy().find( customerCreated.getId() );
		Assert.assertNull( customerFound );
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
		// Kunde anlegen
		// ==================================================
		Customer customer = getCustomer();

		Customer customerCreated = getCustomerProxy().create( customer );
		Assert.assertNotNull( customerCreated );
		Assert.assertNotNull( customerCreated.getId() );
		assertCustomerEquals( customerCreated, customer, false );

		// ==================================================
		// Kunde aktualisieren
		// ==================================================
		customerCreated.setLastname( customer.getLastname() + "_Updated" );

		Customer customerUpdated = getCustomerProxy().update( customerCreated.getId(), customerCreated );
		Assert.assertNotNull( customerUpdated );

		log( this.getClass(), "Update", customerUpdated.toString() );

		Assert.assertEquals( customerUpdated.getLastname(), customerCreated.getLastname() );
		Assert.assertEquals( customerUpdated, customerCreated );

		// ==================================================
		// Kunde löschen
		// ==================================================
		getCustomerProxy().remove( customerCreated.getId() );

		Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
		Assert.assertNull( customerFound );
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
		// Kunde anlegen
		// ==================================================
		Customer customer = getCustomer();

		Customer customerCreated = getCustomerProxy().create( customer );
		Assert.assertNotNull( customerCreated );
		Assert.assertNotNull( customerCreated.getId() );
		assertCustomerEquals( customerCreated, customer, false );

		// ==================================================
		// Kunde löschen
		// ==================================================
		getCustomerProxy().remove( customerCreated.getId() );

		log( this.getClass(), "Remove", customerCreated.toString() );

		Customer customerFound = getCustomerProxy().find( customerCreated.getId() );
		Assert.assertNull( customerFound );
	}

}
