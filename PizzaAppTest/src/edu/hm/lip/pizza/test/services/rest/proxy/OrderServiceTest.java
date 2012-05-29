package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;

import junit.framework.Assert;

/**
 * Testklasse für den Order REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class OrderServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
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
	 */
	protected static Order createOrder( Order order, Product product, Customer customer, boolean log )
	{
		addOrderLine( order, product );
		order.setCustomer( customer );

		Order orderCreated = getOrderProxy().create( order );
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
	 */
	protected static void deleteOrder( Order orderCreated, boolean log )
	{
		try
		{
			Thread.sleep( 2000 );
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		getOrderProxy().remove( orderCreated.getId() );

		if (log)
		{
			log( OrderServiceTest.class, "Remove", orderCreated.toString() );
		}

		Order orderFound = getOrderProxy().find( orderCreated.getId() );
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
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
	{
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
		List<Order> ordersFound = getOrderProxy().findAll();
		Assert.assertNotNull( ordersFound );

		for (Order orderFound : ordersFound)
		{
			log( this.getClass(), "Find_All", orderFound.toString() );
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
		Order orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNotNull( orderFound );

		log( this.getClass(), "Find", orderFound.toString() );

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
	 */
	@Test
	public void testCreateNextOrderStage()
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
		// Bestellung aktualisieren
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Next_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
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
	 */
	@Test
	public void testCreatePreviousOrderStage()
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
		// Bestellung aktualisieren (NEXT Stage)
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung aktualisieren (PREVIOUS Stage)
		// ==================================================
		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Create_Previous_Order_Stage", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.ORDERED );
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
	 */
	@Test
	public void testUpdateOrderToDelivered()
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
		// Bestellung aktualisieren
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().updateOrderToDelivered( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Update_Order_To_Delivered", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
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
	 */
	@Test
	public void testGetUndeliveredOrders()
	{
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
				Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
				orderCreated.setCurrentStage( Stage.IN_PREPARATION );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}

			if (i < ordersCreated.size() - 1)
			{
				Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
				orderCreated.setCurrentStage( Stage.IN_STOVE );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}

			if (i < ordersCreated.size() - 2)
			{
				Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
				orderCreated.setCurrentStage( Stage.IN_DELIVERY );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}

			if (i < ordersCreated.size() - 3)
			{
				Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
				Assert.assertNotNull( orderUpdated );
				Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
				orderCreated.setCurrentStage( Stage.DELIVERED );
				assertOrderEquals( orderUpdated, orderCreated, true );
			}
		}

		// ==================================================
		// Bestellung auslesen
		// ==================================================
		List<Order> ordersFound = getOrderProxy().getUndeliveredOrders();
		Assert.assertNotNull( ordersFound );

		for (Order orderFound : ordersFound)
		{
			log( this.getClass(), "Get_Undelivered_Orders", orderFound.toString() );
		}

		Assert.assertTrue( ordersFound.size() >= ordersCreated.size() - 3 );
		for (Order orderFound : ordersFound)
		{
			assertContainsOrder( ordersCreated, orderFound, true );
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
	 * Testet die GET NEXT ORDER STAGE Funktion.
	 */
	@Test
	public void testGetNextOrderStage()
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
		// Bestellung auslesen/aktualisieren
		// ==================================================
		String orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.IN_PREPARATION );

		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.IN_STOVE );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.IN_DELIVERY );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertNextOrderStage( orderStage, Stage.DELIVERED );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
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
	 */
	@Test
	public void testGetPreviousOrderStage()
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
		// Bestellung aktualisieren (NEXT Stage)
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung auslesen/aktualisieren
		// ==================================================
		String orderStage = getOrderProxy().getPreviousOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.IN_DELIVERY );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getPreviousOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.IN_STOVE );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getPreviousOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.IN_PREPARATION );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getPreviousOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Previous_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertPreviousOrderStage( orderStage, Stage.ORDERED );

		orderUpdated = getOrderProxy().createPreviousOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.ORDERED );
		orderCreated.setCurrentStage( Stage.ORDERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getPreviousOrderStage( orderCreated.getId() );
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
