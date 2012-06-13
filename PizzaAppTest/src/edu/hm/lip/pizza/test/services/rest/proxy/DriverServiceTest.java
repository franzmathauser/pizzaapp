package edu.hm.lip.pizza.test.services.rest.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.RouteState;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Customer;
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
 * @author Franz Mathauser, Stefan Wörner
 */
public class DriverServiceTest extends AbstractRestServiceProxyTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * Legt den übergebenen Fahrer an.
	 * 
	 * @param driver
	 *            Anzulegender Fahrer
	 * @param log
	 *            Ausgabe ins Log
	 * @return Angelegter Fahrer
	 */
	protected static Driver createDriver( Driver driver, boolean log )
	{
		Driver driverCreated = getDriverProxy().create( driver );
		Assert.assertNotNull( driverCreated );

		if (log)
		{
			log( DriverServiceTest.class, "Create", driverCreated.toString() );
		}

		Assert.assertNotNull( driverCreated.getId() );
		assertDriverEquals( driverCreated, driver, false );

		return driverCreated;
	}

	/**
	 * Löscht den übergebenen Fahrer.
	 * 
	 * @param driverCreated
	 *            Zu löschender Fahrer
	 * @param log
	 *            Ausgabe ins Log
	 */
	protected static void deleteDriver( Driver driverCreated, boolean log )
	{
		getDriverProxy().remove( driverCreated.getId() );

		if (log)
		{
			log( DriverServiceTest.class, "Remove", driverCreated.toString() );
		}

		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNull( driverFound );
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
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), true );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
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
			Driver driverCreated = createDriver( driver, false );
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
			deleteDriver( driverCreated, false );
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
		Driver driverCreated = createDriver( getDriver(), false );

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		Driver driverFound = getDriverProxy().find( driverCreated.getId() );
		Assert.assertNotNull( driverFound );

		log( this.getClass(), "Find", driverFound.toString() );

		Assert.assertEquals( driverCreated, driverFound );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
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
		Driver driverCreated = createDriver( driver, false );

		// ==================================================
		// Fahrer aktualisieren
		// ==================================================
		driverCreated.setName( driver.getName() + "_Updated" );

		Driver driverUpdated = getDriverProxy().update( driverCreated.getId(), driverCreated );
		Assert.assertNotNull( driverUpdated );

		log( this.getClass(), "Update", driverUpdated.toString() );

		Assert.assertEquals( driverCreated, driverUpdated );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
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
		Driver driverCreated = createDriver( getDriver(), false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, true );
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
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		List<Order> ordersCreated = new ArrayList<Order>();

		for (int i = 0; i < getOrderList().size(); i++)
		{
			Order order = getOrderList().get( i );
			Customer customer = getCustomerList().get( i );

			Order orderCreated = OrderServiceTest.createOrder( order, productCreated, customer, false );
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
			Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
			orderCreated.setCurrentStage( Stage.IN_PREPARATION );
			assertOrderEquals( orderUpdated, orderCreated, true );

			orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
			Assert.assertNotNull( orderUpdated );
			Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
			orderCreated.setCurrentStage( Stage.IN_STOVE );
			assertOrderEquals( orderUpdated, orderCreated, true );
		}

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

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
			Assert.assertEquals( Stage.IN_DELIVERY, driverOrderFound.getCurrentStage() );
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

		Assert.assertEquals( RouteState.TSP_SUCCESS, driverRoute.getStatus() );
		Assert.assertEquals( Measurements.DURATION, driverRoute.getMeasurement() );
		Assert.assertEquals( 2855L, driverRoute.getMeasurementValue().longValue(), 120L );
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
			OrderServiceTest.deleteOrder( orderCreated, false );
		}

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
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
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = OrderServiceTest.createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

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
			Assert.assertEquals( Stage.IN_DELIVERY, driverOrderFound.getCurrentStage() );
		}
		orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		Assert.assertTrue( driverOrdersFound.size() >= 1 );
		assertContainsOrder( driverOrdersFound, orderCreated, false );

		// ==================================================
		// Bestellung löschen
		// ==================================================
		OrderServiceTest.deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
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
		Product productCreated = ProductServiceTest.createProduct( getProduct(), false );

		// ==================================================
		// Bestellung anlegen
		// ==================================================
		Order orderCreated = OrderServiceTest.createOrder( getOrder(), productCreated, getCustomer(), false );

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		Order orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_PREPARATION );
		assertOrderEquals( orderUpdated, orderCreated, true );

		orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
		Assert.assertNotNull( orderUpdated );
		Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
		orderCreated.setCurrentStage( Stage.IN_STOVE );
		assertOrderEquals( orderUpdated, orderCreated, true );

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

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
			Assert.assertEquals( Stage.IN_DELIVERY, driverOrderFound.getCurrentStage() );
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
		OrderServiceTest.deleteOrder( orderCreated, false );

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
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
		Driver driverCreated = createDriver( getDriver(), false );

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
		deleteDriver( driverCreated, false );
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
			Order orderCreated = OrderServiceTest.createOrder( order, productCreated, getCustomer(), false );
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
			Assert.assertEquals( Stage.IN_PREPARATION, orderUpdated.getCurrentStage() );
			orderCreated.setCurrentStage( Stage.IN_PREPARATION );
			assertOrderEquals( orderUpdated, orderCreated, true );

			orderUpdated = getOrderProxy().createNextOrderStage( orderCreated.getId() );
			Assert.assertNotNull( orderUpdated );
			Assert.assertEquals( Stage.IN_STOVE, orderUpdated.getCurrentStage() );
			orderCreated.setCurrentStage( Stage.IN_STOVE );
			assertOrderEquals( orderUpdated, orderCreated, true );
		}

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

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
			Assert.assertEquals( Stage.IN_DELIVERY, driverOrderFound.getCurrentStage() );
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
			OrderServiceTest.deleteOrder( orderCreated, false );
		}

		// ==================================================
		// Produkt löschen
		// ==================================================
		ProductServiceTest.deleteProduct( productCreated, false );

		// ==================================================
		// Fahrer löschen
		// ==================================================
		deleteDriver( driverCreated, false );
	}

}
