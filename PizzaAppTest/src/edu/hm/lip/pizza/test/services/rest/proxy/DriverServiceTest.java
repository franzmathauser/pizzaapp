package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.RouteState;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.DriverRoute;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderId;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter.Measurements;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Stefan Wörner
 */
public class DriverServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
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
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );

		log( this.getClass(), "Create", driverCreated.toString() );

		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		// Fahrer anlegen
		// ==================================================
		List<Driver> driversCreated = new ArrayList<Driver>();

		for (Driver driver : getDriverList())
		{
			Driver driverCreated = getDriverProxy().create( driver );
			Assert.assertNotNull( driverCreated );
			Assert.assertNotNull( driverCreated.getId() );
			assertDriverEquals( driverCreated, driver, false );

			driversCreated.add( driverCreated );
		}

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		List<Driver> driversFound = getDriverProxy().findAll();
		Assert.assertNotNull( driversFound );

		for (Driver driverFound : driversFound)
		{
			log( this.getClass(), "Find_All", driverFound.toString() );
		}

		Assert.assertTrue( driversFound.size() >= driversCreated.size() );
		for (Driver driverCreated : driversCreated)
		{
			Assert.assertTrue( driversFound.contains( driverCreated ) );
		}

		// ==================================================
		// Fahrer löschen
		// ==================================================
		for (Driver driverCreated : driversCreated)
		{
			getDriverProxy().remove( driverCreated.getId() );

			Driver driverFound = getDriverProxy().find( driverCreated.getId() );
			Assert.assertNull( driverFound );
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
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNotNull( driverFound );

		log( this.getClass(), "Find", driverFound.toString() );

		Assert.assertEquals( driverFound, driverCreated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Fahrer aktualisieren
		// ==================================================
		driverCreated.setName( driver.getName() + "_Updated" );

		Driver driverUpdated = getDriverProxy().update( driverCreated.getId(), driverCreated );
		Assert.assertNotNull( driverUpdated );

		log( this.getClass(), "Update", driverUpdated.toString() );

		Assert.assertEquals( driverUpdated.getName(), driverCreated.getName() );
		Assert.assertEquals( driverUpdated, driverCreated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		log( this.getClass(), "Remove", driverCreated.toString() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * Testet die GET ROUTE Funktion.
	 */
	@Test
	public void testGetRoute()
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

		for (int i = 0; i < getOrderList().size(); i++)
		{
			Order order = getOrderList().get( i );

			addOrderLine( order, productCreated );
			order.setCustomer( getCustomerList().get( i ) );

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
		}

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Dem Fahrer Bestellung zuordnen
		// ==================================================
		for (int i = 0; i < ordersCreated.size() - 2; i++)
		{
			Order orderCreated = ordersCreated.get( i );

			OrderId orderId = new OrderId();
			orderId.setId( orderCreated.getId() );
			getDriverProxy().addOrder( driverCreated.getId(), orderId );

			orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		}

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		List<Order> driverOrdersFound = getDriverProxy().getUndeliveredOrders( driverCreated.getId() );
		Assert.assertNotNull( driverOrdersFound );

		for (Order driverOrderFound : driverOrdersFound)
		{
			Assert.assertEquals( driverOrderFound.getCurrentStage(), Stage.IN_DELIVERY );
		}

		Assert.assertTrue( driverOrdersFound.size() >= ordersCreated.size() - 2 );
		for (Order driverOrderFound : driverOrdersFound)
		{
			assertContainsOrder( ordersCreated, driverOrderFound, false );
		}

		// ==================================================
		// Route des Fahrers auslesen
		// ==================================================
		DriverRoute driverRoute = getDriverProxy().getRoute( driverCreated.getId() );
		Assert.assertNotNull( driverRoute );

		log( this.getClass(), "Get_Route", driverRoute.toString() );

		Assert.assertEquals( driverRoute.getStatus(), RouteState.TSP_SUCCESS );
		Assert.assertEquals( driverRoute.getMeasurement(), Measurements.DURATION );
		Assert.assertEquals( driverRoute.getMeasurementValue().longValue(), 2855L );
		Assert.assertTrue( driverRoute.getOrders().size() >= ordersCreated.size() - 2 );
		for (Order driverOrderFound : driverRoute.getOrders())
		{
			assertContainsOrder( ordersCreated, driverOrderFound, false );
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

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * Testet die ADD ORDER Funktion.
	 */
	@Test
	public void testAddOrder()
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

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Dem Fahrer Bestellung zuordnen
		// ==================================================
		OrderId orderId = new OrderId();
		orderId.setId( orderCreated.getId() );
		getDriverProxy().addOrder( driverCreated.getId(), orderId );

		log( this.getClass(), "Add_Order", driverCreated.toString() );
		log( this.getClass(), "Add_Order", orderCreated.toString() );

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		List<Order> driverOrdersFound = getDriverProxy().getUndeliveredOrders( driverCreated.getId() );
		Assert.assertNotNull( driverOrdersFound );
		for (Order driverOrderFound : driverOrdersFound)
		{
			Assert.assertEquals( driverOrderFound.getCurrentStage(), Stage.IN_DELIVERY );
		}
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		Assert.assertTrue( driverOrdersFound.size() >= 1 );
		assertContainsOrder( driverOrdersFound, orderCreated, false );

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

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * Testet die REMOVE ORDER Funktion.
	 */
	@Test
	public void testRemoveOrder()
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

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Dem Fahrer Bestellung zuordnen
		// ==================================================
		OrderId orderId = new OrderId();
		orderId.setId( orderCreated.getId() );
		getDriverProxy().addOrder( driverCreated.getId(), orderId );

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		List<Order> driverOrdersFound = getDriverProxy().getUndeliveredOrders( driverCreated.getId() );
		Assert.assertNotNull( driverOrdersFound );
		for (Order driverOrderFound : driverOrdersFound)
		{
			Assert.assertEquals( driverOrderFound.getCurrentStage(), Stage.IN_DELIVERY );
		}
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		Assert.assertTrue( driverOrdersFound.size() >= 1 );
		assertContainsOrder( driverOrdersFound, orderCreated, false );

		// ==================================================
		// Zuordnung wieder auflösen
		// ==================================================
		getDriverProxy().removeOrder( driverCreated.getId(), orderCreated.getId() );

		log( this.getClass(), "Remove_Order", driverCreated.toString() );
		log( this.getClass(), "Remove_Order", orderCreated.toString() );

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		driverOrdersFound = getDriverProxy().getUndeliveredOrders( driverCreated.getId() );
		Assert.assertTrue( driverOrdersFound.size() == 0 );

		// ==================================================
		// Bestellung auslesen
		// ==================================================
		List<Order> ordersFound = getOrderProxy().findAll();
		Assert.assertNotNull( ordersFound );
		Assert.assertTrue( ordersFound.size() >= 1 );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertContainsOrder( ordersFound, orderCreated, true );

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

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

	/**
	 * Testet die CREATE GPS DATA Funktion.
	 */
	@Test
	public void testCreateGPSData()
	{
		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// GPSDaten hinzufügen
		// ==================================================
		GPSData gpsData = getGPSData();
		completeGPSData( gpsData, driverCreated );

		getDriverProxy().createGPSData( driverCreated.getId(), gpsData );

		log( this.getClass(), "Create_GPS_Data", driverCreated.toString() );
		log( this.getClass(), "Create_GPS_Data", gpsData.toString() );

		// ==================================================
		// GPSDaten prüfen
		// ==================================================
		List<GPSData> gpsDataListFound = getGPSDataProxy().findAll();
		Assert.assertNotNull( gpsDataListFound );
		Assert.assertTrue( gpsDataListFound.size() >= 1 );
		assertContainsGPSData( gpsDataListFound, gpsData, false, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		}

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();

		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );
		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		// ==================================================
		// Dem Fahrer Bestellung zuordnen
		// ==================================================
		for (int i = 0; i < ordersCreated.size() - 2; i++)
		{
			Order orderCreated = ordersCreated.get( i );

			OrderId orderId = new OrderId();
			orderId.setId( orderCreated.getId() );
			getDriverProxy().addOrder( driverCreated.getId(), orderId );

			orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		}

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		List<Order> driverOrdersFound = getDriverProxy().getUndeliveredOrders( driverCreated.getId() );
		Assert.assertNotNull( driverOrdersFound );

		for (Order driverOrderFound : driverOrdersFound)
		{
			log( this.getClass(), "Get_Undelivered_Orders", driverOrderFound.toString() );
			Assert.assertEquals( driverOrderFound.getCurrentStage(), Stage.IN_DELIVERY );
		}

		Assert.assertTrue( driverOrdersFound.size() >= ordersCreated.size() - 2 );
		for (Order driverOrderFound : driverOrdersFound)
		{
			assertContainsOrder( ordersCreated, driverOrderFound, false );
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

		// ==================================================
		// Fahrer löschen
		// ==================================================
		getDriverProxy().remove( driverCreated.getId() );

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
	}

}
