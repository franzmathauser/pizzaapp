package edu.hm.lip.pizza.test.services.rest.pure;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.JsonMapper;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class OrderServiceTest extends AbstractRestServicePureTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * Legt die übergebene Bestellung an.
	 * 
	 * @param order
	 *            Bestellung
	 * @param product
	 *            Produkt
	 * @param customer
	 *            Kunde
	 * @param log
	 *            Ausgabe ins Log
	 * @return Angelegte Bestellung
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static Order createOrder( Order order, Product product, Customer customer, boolean log ) throws Exception
	{
		addOrderLine( order, product );
		order.setCustomer( customer );

		ClientRequest request = getClient( "orders" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( order ) );

		ClientResponse<String> response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderCreated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderCreated );

		if (log)
		{
			log( OrderServiceTest.class, "Create", orderCreated.toString() );
		}

		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

		return orderCreated;
	}

	/**
	 * Löscht die übergebene Bestellung.
	 * 
	 * @param orderCreated
	 *            Zu löschende Bestellung
	 * @param log
	 *            Ausgabe ins Log
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static void deleteOrder( Order orderCreated, boolean log ) throws Exception
	{
		try
		{
			Thread.sleep( 2000 );
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		ClientResponse<String> response = getClient( "orders/" + orderCreated.getId() ).delete( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		if (log)
		{
			log( OrderServiceTest.class, "Remove", orderCreated.toString() );
		}

		response = getClient( "orders/" + orderCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		Order orderFound = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNull( orderFound );
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
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
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
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		List<Order> ordersCreated = new ArrayList<Order>();

		for (Order order : getOrderList())
		{
			Order orderCreated = createOrder( order, productCreated, getCustomer(), false );
			ordersCreated.add( orderCreated );
		}

		// ==================================================
		// Bestellung auslesen
		// ==================================================
		response = getClient( "orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> ordersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
		Assert.assertNotNull( ordersFound );

		for (Order orderFound : ordersFound)
		{
			log( OrderServiceTest.class, "Find_All", orderFound.toString() );
		}

		Assert.assertTrue( ordersFound.size() >= ordersCreated.size() );
		for (Order orderCreated : ordersCreated)
		{
			assertContainsOrder( ordersFound, orderCreated, true );
		}

		// ==================================================
		// Bestellung löschen
		// ==================================================
		for (Order orderCreated : ordersCreated)
		{
			deleteOrder( orderCreated, false );
		}

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
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
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung auslesen
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderFound = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderFound );

		log( OrderServiceTest.class, "Find", orderFound.toString() );

		assertOrderEquals( orderFound, orderCreated, true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
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
		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, true );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

	/**
	 * Testet die CREATE NEXT ORDER STAGE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testCreateNextOrderStage() throws Exception
	{
		ClientResponse<String> response = null;

		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.DELIVERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

	/**
	 * Testet die CREATE PREVIOUS ORDER STAGE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testCreatePreviousOrderStage() throws Exception
	{
		ClientResponse<String> response = null;

		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung aktualisieren (NEXT Stage)
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.DELIVERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung aktualisieren (PREVIOUS Stage)
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( Stage.ORDERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.ORDERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

	/**
	 * Testet die UPDATE ORDER TO DELIVERED Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testUpdateOrderToDelivered() throws Exception
	{
		ClientResponse<String> response = null;

		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/delivered" ).put( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Update_Order_To_Delivered", orderUpdated.toString() );

		Assert.assertEquals( Stage.DELIVERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

	/**
	 * Testet die GET UNDELIVERED ORDERS Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testGetUndeliveredOrders() throws Exception
	{
		ClientResponse<String> response = null;

		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		List<Order> ordersCreated = new ArrayList<Order>();

		for (Order order : getOrderList())
		{
			Order orderCreated = createOrder( order, productCreated, getCustomer(), false );
			ordersCreated.add( orderCreated );
		}

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		for (int i = 0; i < ordersCreated.size(); i++)
		{
			Order orderCreated = ordersCreated.get( i );

			if (i < ordersCreated.size())
			{
				response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
				Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

				Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
				orderCreated.setCurrentStage( Stage.IN_PREPARATION );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}

			if (i < ordersCreated.size() - 1)
			{
				response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
				Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

				Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
				orderCreated.setCurrentStage( Stage.IN_STOVE );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}

			if (i < ordersCreated.size() - 2)
			{
				response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
				Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

				Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
				orderCreated.setCurrentStage( Stage.IN_DELIVERY );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}

			if (i < ordersCreated.size() - 3)
			{
				response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
				Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

				Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( Stage.DELIVERED, orderUpdated.getCurrentStage() );
				orderCreated.setCurrentStage( Stage.DELIVERED );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}
		}

		// ==================================================
		// Bestellung auslesen
		// ==================================================
		response = getClient( "orders/undelivered" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> ordersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
		Assert.assertNotNull( ordersFound );

		for (Order orderFound : ordersFound)
		{
			log( this.getClass(), "Get_Undelivered_Orders", orderFound.toString() );
		}

		Assert.assertTrue( ordersFound.size() >= 2 );
		assertContainsOrder( ordersFound, ordersCreated.get( ordersCreated.size() - 2 ), true );
		assertContainsOrder( ordersFound, ordersCreated.get( ordersCreated.size() - 1 ), true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		for (Order orderCreated : ordersCreated)
		{
			deleteOrder( orderCreated, false );
		}

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

	/**
	 * Testet die GET NEXT ORDER STAGE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testGetNextOrderStage() throws Exception
	{
		ClientResponse<String> response = null;

		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung auslesen/aktualisieren
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		String orderStage = response.getEntity();
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.IN_PREPARATION );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.IN_STOVE );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.IN_DELIVERY );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.DELIVERED );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.DELIVERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNull( orderStage );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

	/**
	 * Testet die GET PREVIOUS ORDER STAGE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testGetPreviousOrderStage() throws Exception
	{
		ClientResponse<String> response = null;

		// ==================================================
		// Produkt anlegen
		// ==================================================
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung aktualisieren (NEXT Stage)
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Order orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/next" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.DELIVERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung auslesen/aktualisieren
		// ==================================================
		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		String orderStage = response.getEntity();
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.IN_DELIVERY );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_DELIVERY, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.IN_STOVE );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.IN_PREPARATION );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.ORDERED );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderUpdated = JsonMapper.fromJSON( response.getEntity(), Order.class );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.ORDERED, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.ORDERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		response = getClient( "orders/" + orderCreated.getId() + "/stages/previous" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		orderStage = response.getEntity();
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.ORDERED );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );
	}

}
