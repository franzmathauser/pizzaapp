package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest;

import junit.framework.Assert;

/**
 * Testklasse für den Product REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class ProductServiceTest extends AbstractRestServiceTest
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
		// Produkt anlegen
		// ==================================================
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );

		log( this.getClass(), "Create", productCreated.toString() );

		Assert.assertNotNull( productCreated.getId() );
		assertProductEqualsWithoutId( productCreated, product );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		// Produkt anlegen
		// ==================================================
		List<Product> productsCreated = new ArrayList<Product>();

		for (Product product : getProductList())
		{
			Product productCreated = getProductProxy().create( product );
			Assert.assertNotNull( productCreated );
			Assert.assertNotNull( productCreated.getId() );
			assertProductEqualsWithoutId( productCreated, product );

			productsCreated.add( productCreated );
		}

		// ==================================================
		// Produkt auslesen
		// ==================================================
		List<Product> productsFound = getProductProxy().findAll();
		Assert.assertNotNull( productsFound );

		for (Product productFound : productsFound)
		{
			log( this.getClass(), "FindAll", productFound.toString() );
		}

		Assert.assertTrue( productsFound.size() >= productsCreated.size() );
		for (Product productCreated : productsCreated)
		{
			Assert.assertTrue( productsFound.contains( productCreated ) );
		}

		// ==================================================
		// Produkt löschen
		// ==================================================
		for (Product productCreated : productsCreated)
		{
			getProductProxy().remove( productCreated.getId() );

			Product productFound = getProductProxy().find( productCreated.getId() );
			Assert.assertNull( productFound );
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
		// Produkt anlegen
		// ==================================================
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEqualsWithoutId( productCreated, product );

		// ==================================================
		// Produkt auslesen
		// ==================================================
		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNotNull( productFound );

		log( this.getClass(), "Find", productFound.toString() );

		Assert.assertEquals( productFound, productCreated );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		// Produkt anlegen
		// ==================================================
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEqualsWithoutId( productCreated, product );

		// ==================================================
		// Produkt aktualisieren
		// ==================================================
		productCreated.setName( product.getName() + "_Updated" );

		Product productUpdated = getProductProxy().update( productCreated.getId(), productCreated );
		Assert.assertNotNull( productUpdated );

		log( this.getClass(), "Update", productUpdated.toString() );

		Assert.assertEquals( productUpdated.getName(), productCreated.getName() );
		Assert.assertEquals( productUpdated, productCreated );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		// Produkt anlegen
		// ==================================================
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEqualsWithoutId( productCreated, product );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		log( this.getClass(), "Remove", productCreated.toString() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
	}

}
