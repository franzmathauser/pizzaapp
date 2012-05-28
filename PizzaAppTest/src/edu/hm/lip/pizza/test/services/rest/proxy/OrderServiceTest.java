package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.Stage;
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
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testCreate()
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
		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order order = getOrder();
		addOrderLine( order, productCreated );
		order.setCustomer( getCustomer() );

		Order orderCreated = getOrderProxy().create( order );
		Assert.assertNotNull( orderCreated );

		log( this.getClass(), "Create", orderCreated.toString() );

		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		getOrderProxy().remove( orderCreated.getId() );

		Order orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNull( orderFound );

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
	@Test
	@Override
	public void testFindAll() throws Exception
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
		// Bestellung anlegen
		// ==================================================
		List<Order> ordersCreated = new ArrayList<Order>();

		for (Order order : getOrderList())
		{
			addOrderLine( order, productCreated );
			order.setCustomer( getCustomer() );

			Order orderCreated = getOrderProxy().create( order );
			Assert.assertNotNull( orderCreated );
			Assert.assertNotNull( orderCreated.getId() );
			assertOrderEquals( orderCreated, order, false );

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
			getOrderProxy().remove( orderCreated.getId() );

			Order orderFound = getOrderProxy().find( orderCreated.getId() );
			Assert.assertNull( orderFound );
		}

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
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testFind()
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
		// Bestellung anlegen
		// ==================================================
		Order order = getOrder();
		addOrderLine( order, productCreated );
		order.setCustomer( getCustomer() );

		Order orderCreated = getOrderProxy().create( order );
		Assert.assertNotNull( orderCreated );
		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

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
		getOrderProxy().remove( orderCreated.getId() );

		orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNull( orderFound );

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
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testUpdate()
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
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testRemove()
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
		// Bestellung anlegen
		// ==================================================
		Order order = getOrder();
		addOrderLine( order, productCreated );
		order.setCustomer( getCustomer() );

		Order orderCreated = getOrderProxy().create( order );
		Assert.assertNotNull( orderCreated );
		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		getOrderProxy().remove( orderCreated.getId() );

		log( this.getClass(), "Remove", orderCreated.toString() );

		Order orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNull( orderFound );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order order = getOrder();
		addOrderLine( order, productCreated );
		order.setCustomer( getCustomer() );

		Order orderCreated = getOrderProxy().create( order );
		Assert.assertNotNull( orderCreated );
		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Update", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Update", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Update", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );

		log( this.getClass(), "Update", orderUpdated.toString() );

		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.DELIVERED );
		orderCreated.setCurrentStage( Stage.DELIVERED );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		getOrderProxy().remove( orderCreated.getId() );

		Order orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNull( orderFound );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order order = getOrder();
		addOrderLine( order, productCreated );
		order.setCustomer( getCustomer() );

		Order orderCreated = getOrderProxy().create( order );
		Assert.assertNotNull( orderCreated );
		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

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
		getOrderProxy().remove( orderCreated.getId() );

		Order orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNull( orderFound );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		List<Order> ordersCreated = new ArrayList<Order>();

		for (Order order : getOrderList())
		{
			addOrderLine( order, productCreated );
			order.setCustomer( getCustomer() );

			Order orderCreated = getOrderProxy().create( order );
			Assert.assertNotNull( orderCreated );
			Assert.assertNotNull( orderCreated.getId() );
			assertOrderEquals( orderCreated, order, false );

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
			getOrderProxy().remove( orderCreated.getId() );

			Order orderFound = getOrderProxy().find( orderCreated.getId() );
			Assert.assertNull( orderFound );
		}

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
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
		Product product = getProduct();

		Product productCreated = getProductProxy().create( product );
		Assert.assertNotNull( productCreated );
		Assert.assertNotNull( productCreated.getId() );
		assertProductEquals( productCreated, product, false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order order = getOrder();
		addOrderLine( order, productCreated );
		order.setCustomer( getCustomer() );

		Order orderCreated = getOrderProxy().create( order );
		Assert.assertNotNull( orderCreated );
		Assert.assertNotNull( orderCreated.getId() );
		assertOrderEquals( orderCreated, order, false );

		// ==================================================
		// Bestellung auslesen/aktualisieren
		// ==================================================
		String orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertOrderStage( orderStage, Stage.IN_PREPARATION );

		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_PREPARATION );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertOrderStage( orderStage, Stage.IN_STOVE );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_STOVE );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertOrderStage( orderStage, Stage.IN_DELIVERY );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( orderUpdated.getCurrentStage(), Stage.IN_DELIVERY );
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderStage = getOrderProxy().getNextOrderStage( orderCreated.getId() );
		log( this.getClass(), "Get_Next_Stage", orderStage );
		Assert.assertNotNull( orderStage );
		assertOrderStage( orderStage, Stage.DELIVERED );

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
		getOrderProxy().remove( orderCreated.getId() );

		Order orderFound = getOrderProxy().find( orderCreated.getId() );
		Assert.assertNull( orderFound );

		// ==================================================
		// Produkt löschen
		// ==================================================
		getProductProxy().remove( productCreated.getId() );

		Product productFound = getProductProxy().find( productCreated.getId() );
		Assert.assertNull( productFound );
	}

}
