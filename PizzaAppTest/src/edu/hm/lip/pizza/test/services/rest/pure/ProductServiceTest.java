package edu.hm.lip.pizza.test.services.rest.pure;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.JsonMapper;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class ProductServiceTest extends AbstractRestServicePureTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * Legt das übergebene Produkt an.
	 * 
	 * @param product
	 *            Anzulegendes Produkt
	 * @param log
	 *            Ausgabe ins Log
	 * @return Angelegtes Produkt
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static Product createProduct( Product product, boolean log ) throws Exception
	{
		ClientRequest request = getClient( "products" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( product ) );

		ClientResponse<String> response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Product productCreated = JsonMapper.fromJSON( response.getEntity(), Product.class );
		Assert.assertNotNull( productCreated );

		if (log)
		{
			log( ProductServiceTest.class, "Create", productCreated.toString() );
		}

		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

		return productCreated;
	}

	/**
	 * Löscht das übergebene Produkt.
	 * 
	 * @param productCreated
	 *            Zu löschendes Produkt
	 * @param log
	 *            Ausgabe ins Log
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static void deleteProduct( Product productCreated, boolean log ) throws Exception
	{
		ClientResponse<String> response = getClient( "products/" + productCreated.getId() ).delete( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		if (log)
		{
			log( ProductServiceTest.class, "Remove", productCreated.toString() );
		}

		response = getClient( "products/" + productCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		Product productFound = JsonMapper.fromJSON( response.getEntity(), Product.class );
		Assert.assertNull( productFound );
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
		// Produkt anlegen
		// ==================================================
		Product productCreated = createProduct( getProduct(), true );

		// ==================================================
		// Produkt löschen
		// ==================================================
		deleteProduct( productCreated, false );
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
		// Produkt anlegen
		// ==================================================
		List<Product> productsCreated = new ArrayList<Product>();

		for (Product product : getProductList())
		{
			Product productCreated = createProduct( product, false );
			productsCreated.add( productCreated );
		}

		// ==================================================
		// Produkt auslesen
		// ==================================================
		response = getClient( "products" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Product> productsFound = JsonMapper.fromJSONArray( response.getEntity(), Product.class );
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
			deleteProduct( productCreated, false );
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
		// Produkt anlegen
		// ==================================================
		Product productCreated = createProduct( getProduct(), false );

		// ==================================================
		// Produkt auslesen
		// ==================================================
		response = getClient( "products/" + productCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Product productFound = JsonMapper.fromJSON( response.getEntity(), Product.class );
		Assert.assertNotNull( productFound );

		log( this.getClass(), "Find", productFound.toString() );

		Assert.assertEquals( productFound, productCreated );

		// ==================================================
		// Produkt löschen
		// ==================================================
		deleteProduct( productCreated, false );
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
		// Produkt anlegen
		// ==================================================
		Product product = getProduct();
		Product productCreated = createProduct( product, false );

		// ==================================================
		// Produkt aktualisieren
		// ==================================================
		productCreated.setName( product.getName() + "_Updated" );
		productCreated.setDescription( product.getDescription() + "_Updated" );
		productCreated.setImageUrl( "updated_" + product.getImageUrl() );
		productCreated.setPriceL( "" + (Double.parseDouble( product.getPriceL() ) + 1) );
		productCreated.setPriceXL( "" + (Double.parseDouble( product.getPriceXL() ) + 1) );
		productCreated.setPriceXXL( "" + (Double.parseDouble( product.getPriceXXL() ) + 1) );

		request = getClient( "products/" + productCreated.getId() );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( productCreated ) );

		response = request.put( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Product productUpdated = JsonMapper.fromJSON( response.getEntity(), Product.class );
		Assert.assertNotNull( productUpdated );

		log( this.getClass(), "Update", productUpdated.toString() );

		Assert.assertEquals( productUpdated, productCreated );

		// ==================================================
		// Produkt löschen
		// ==================================================
		deleteProduct( productCreated, false );
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
		Product productCreated = createProduct( getProduct(), false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		deleteProduct( productCreated, true );
	}

}
