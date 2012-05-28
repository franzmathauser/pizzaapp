package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.enumeration.Size;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderLine;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest;

import junit.framework.Assert;

/**
 * Testklasse für den Order REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class OrderServiceTest extends AbstractRestServiceTest
{

	private void addOrderLine( Order order, Product product )
	{
		if (order == null || product == null || product.getId() == null)
		{
			return;
		}

		OrderLine orderLine = new OrderLine();
		orderLine.setId( null );
		orderLine.setProductId( product.getId() );
		orderLine.setQuantity( 2 );
		orderLine.setSize( Size.L );

		List<OrderLine> oderLines = new ArrayList<OrderLine>();
		oderLines.add( orderLine );

		order.setOrderLines( oderLines );
		order.setPrice( "" + (Double.parseDouble( product.getPriceL() ) * 2) );
	}

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
		Assert.assertNotNull( productCreated.getId() );
		assertProductEqualsWithoutId( productCreated, product );

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
		assertOrderEqualsWithoutId( orderCreated, order );

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
	@Override
	public void testFindAll() throws Exception
	{
		// // ==================================================
		// // Bestellung anlegen
		// // ==================================================
		// List<Order> ordersCreated = new ArrayList<Order>();
		//
		// for (Order order : getOrderList())
		// {
		// Order orderCreated = getOrderProxy().create( order );
		// Assert.assertNotNull( orderCreated );
		// Assert.assertNotNull( orderCreated.getId() );
		// assertOrderEqualsWithoutId( orderCreated, order );
		//
		// ordersCreated.add( orderCreated );
		// }
		//
		// // ==================================================
		// // Bestellung auslesen
		// // ==================================================
		// List<Order> ordersFound = getOrderProxy().findAll();
		// Assert.assertNotNull( ordersFound );
		//
		// for (Order orderFound : ordersFound)
		// {
		// log( this.getClass(), "FindAll", orderFound.toString() );
		// }
		//
		// Assert.assertTrue( ordersFound.size() >= ordersCreated.size() );
		// for (Order orderCreated : ordersCreated)
		// {
		// Assert.assertTrue( ordersFound.contains( orderCreated ) );
		// }
		//
		// // ==================================================
		// // Bestellung löschen
		// // ==================================================
		// for (Order orderCreated : ordersCreated)
		// {
		// getOrderProxy().remove( orderCreated.getId() );
		//
		// Order orderFound = getOrderProxy().find( orderCreated.getId() );
		// Assert.assertNull( orderFound );
		// }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testFind()
	 */
	@Override
	public void testFind() throws Exception
	{
		// // ==================================================
		// // Bestellung anlegen
		// // ==================================================
		// Order order = getOrder();
		//
		// Order orderCreated = getOrderProxy().create( order );
		// Assert.assertNotNull( orderCreated );
		// Assert.assertNotNull( orderCreated.getId() );
		// assertOrderEqualsWithoutId( orderCreated, order );
		//
		// // ==================================================
		// // Bestellung auslesen
		// // ==================================================
		// Order orderFound = getOrderProxy().find( orderCreated.getId() );
		// Assert.assertNotNull( orderFound );
		//
		// log( this.getClass(), "Find", orderFound.toString() );
		//
		// Assert.assertEquals( orderFound, orderCreated );
		//
		// // ==================================================
		// // Bestellung löschen
		// // ==================================================
		// getOrderProxy().remove( orderCreated.getId() );
		//
		// orderFound = getOrderProxy().find( orderCreated.getId() );
		// Assert.assertNull( orderFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testUpdate()
	 */
	@Override
	public void testUpdate() throws Exception
	{
		// // ==================================================
		// // Bestellung anlegen
		// // ==================================================
		// Order order = getOrder();
		//
		// Order orderCreated = getProxy().create( order );
		// Assert.assertNotNull( orderCreated );
		// Assert.assertNotNull( orderCreated.getId() );
		// assertEqualsWithoutId( orderCreated, order );
		//
		// // ==================================================
		// // Bestellung aktualisieren
		// // ==================================================
		// orderCreated.setName( order.getName() + "_Updated" );
		//
		// Order orderUpdated = getProxy().update( orderCreated.getId(), orderCreated );
		// Assert.assertNotNull( orderUpdated );
		//
		// log( this.getClass(), "Update", orderUpdated.toString() );
		//
		// Assert.assertEquals( orderUpdated.getName(), orderCreated.getName() );
		// Assert.assertEquals( orderUpdated, orderCreated );
		//
		// // ==================================================
		// // Bestellung löschen
		// // ==================================================
		// getProxy().remove( orderCreated.getId() );
		//
		// Order orderFound = getProxy().find( orderCreated.getId() );
		// Assert.assertNull( orderFound );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.test.services.rest.AbstractRestServiceTest#testRemove()
	 */
	@Override
	public void testRemove() throws Exception
	{
		// // ==================================================
		// // Bestellung anlegen
		// // ==================================================
		// Order order = getOrder();
		//
		// Order orderCreated = getOrderProxy().create( order );
		// Assert.assertNotNull( orderCreated );
		// Assert.assertNotNull( orderCreated.getId() );
		// assertOrderEqualsWithoutId( orderCreated, order );
		//
		// // ==================================================
		// // Bestellung löschen
		// // ==================================================
		// getOrderProxy().remove( orderCreated.getId() );
		//
		// log( this.getClass(), "Remove", orderCreated.toString() );
		//
		// Order orderFound = getOrderProxy().find( orderCreated.getId() );
		// Assert.assertNull( orderFound );
	}

}
