package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Product REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class ProductServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
{

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
		// Produkt anlegen
		// ==================================================
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );

		log( this.getClass(), "Create", productCreated.toString() );

		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

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
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFindAll()
	 */
	@Test
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
			assertProductEquals( productCreated, product, false );

			productsCreated.add( productCreated );
		}

		// ==================================================
		// Produkt auslesen
		// ==================================================
		List<Product> productsFound = getProductProxy().findAll();
		Assert.assertNotNull( productsFound );

		for (Product productFound : productsFound)
		{
			log( this.getClass(), "Find_All", productFound.toString() );
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
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFind()
	 */
	@Test
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
		assertProductEquals( productCreated, product, false );

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
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testUpdate()
	 */
	@Test
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
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Produkt aktualisieren
		// ==================================================
		productCreated.setName( product.getName() + "_Updated" );
		productCreated.setDescription( product.getDescription() + "_Updated" );
		productCreated.setImageUrl( "updated_" + product.getImageUrl() );
		productCreated.setPriceL( "" + (Double.parseDouble( product.getPriceL() ) + 1) );
		productCreated.setPriceXL( "" + (Double.parseDouble( product.getPriceXL() ) + 1) );
		productCreated.setPriceXXL( "" + (Double.parseDouble( product.getPriceXXL() ) + 1) );

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
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testRemove()
	 */
	@Test
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
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		log( this.getClass(), "Remove", productCreated.toString() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
	}

}
