package edu.hm.lip.pizza.test.services.rest.pure;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
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
import edu.hm.lip.pizza.test.JsonMapper;
import edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter.Measurements;

import junit.framework.Assert;

/**
 * Testklasse für den Driver REST-WebService.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class DriverServiceTest extends AbstractRestServicePureTest implements IRestServiceDefaultTestFunctions
{

	/**
	 * Legt den übergebenen Fahrer an.
	 * 
	 * @param driver
	 *            Anzulegender Fahrer
	 * @param log
	 *            Ausgabe ins Log
	 * @return Angelegter Fahrer
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static Driver createDriver( Driver driver, boolean log ) throws Exception
	{
		ClientRequest request = getClient( "drivers" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driver ) );

		ClientResponse<String> response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Driver driverCreated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
	 * @throws Exception
	 *             Fehler beim Löschen
	 */
	protected static void deleteDriver( Driver driverCreated, boolean log ) throws Exception
	{
		ClientResponse<String> response = getClient( "drivers/" + driverCreated.getId() ).delete( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		if (log)
		{
			log( DriverServiceTest.class, "Remove", driverCreated.toString() );
		}

		response = getClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
	 * @throws Exception
	 * @see edu.hm.lip.pizza.test.services.rest.IRestServiceDefaultTestFunctions#testFindAll()
	 */
	@Test
	@Override
	public void testFindAll() throws Exception
	{
		ClientResponse<String> response = null;

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
		response = getClient( "drivers" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Driver> driversFound = JsonMapper.fromJSONArray( response.getEntity(), Driver.class );
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
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

		// ==================================================
		// Fahrer auslesen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Driver driverFound = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driver = getDriver();
		Driver driverCreated = createDriver( driver, false );

		// ==================================================
		// Fahrer aktualisieren
		// ==================================================
		driverCreated.setName( driver.getName() + "_Updated" );

		request = getClient( "drivers/" + driverCreated.getId() );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( driverCreated ) );

		response = request.put( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		Driver driverUpdated = JsonMapper.fromJSON( response.getEntity(), Driver.class );
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
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testGetRoute() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

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

			request = getClient( "drivers/" + driverCreated.getId() + "/orders" );
			request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( orderId ) );

			response = request.post( String.class );
			Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

			orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		}

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() + "/orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> driverOrdersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
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
		response = getClient( "drivers/" + driverCreated.getId() + "/route" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		DriverRoute driverRoute = JsonMapper.fromJSON( response.getEntity(), DriverRoute.class );
		Assert.assertNotNull( driverRoute );

		log( this.getClass(), "Get_Route", driverRoute.toString() );

		Assert.assertEquals( RouteState.TSP_SUCCESS, driverRoute.getStatus() );
		Assert.assertEquals( Measurements.DURATION, driverRoute.getMeasurement() );
		Assert.assertEquals( 2855L, driverRoute.getMeasurementValue().longValue(), 300L );
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
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testAddOrder() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

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

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

		// ==================================================
		// Dem Fahrer Bestellung zuordnen
		// ==================================================
		OrderId orderId = new OrderId();
		orderId.setId( orderCreated.getId() );

		request = getClient( "drivers/" + driverCreated.getId() + "/orders" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( orderId ) );

		response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		orderCreated.setCurrentStage( Stage.IN_DELIVERY );

		log( this.getClass(), "Add_Order", driverCreated.toString() );
		log( this.getClass(), "Add_Order", orderCreated.toString() );

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() + "/orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> driverOrdersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
		Assert.assertNotNull( driverOrdersFound );

		for (Order driverOrderFound : driverOrdersFound)
		{
			Assert.assertEquals( Stage.IN_DELIVERY, driverOrderFound.getCurrentStage() );
		}

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
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testRemoveOrder() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

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

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

		// ==================================================
		// Dem Fahrer Bestellung zuordnen
		// ==================================================
		OrderId orderId = new OrderId();
		orderId.setId( orderCreated.getId() );

		request = getClient( "drivers/" + driverCreated.getId() + "/orders" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( orderId ) );

		response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		orderCreated.setCurrentStage( Stage.IN_DELIVERY );

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() + "/orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> driverOrdersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
		Assert.assertNotNull( driverOrdersFound );

		for (Order driverOrderFound : driverOrdersFound)
		{
			Assert.assertEquals( Stage.IN_DELIVERY, driverOrderFound.getCurrentStage() );
		}

		Assert.assertTrue( driverOrdersFound.size() >= 1 );
		assertContainsOrder( driverOrdersFound, orderCreated, false );

		// ==================================================
		// Zuordnung wieder auflösen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() + "/orders/" + orderCreated.getId() ).delete( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		log( this.getClass(), "Remove_Order", driverCreated.toString() );
		log( this.getClass(), "Remove_Order", orderCreated.toString() );

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() + "/orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		driverOrdersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
		Assert.assertTrue( driverOrdersFound.size() == 0 );

		// ==================================================
		// Bestellung auslesen
		// ==================================================
		response = getClient( "orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> ordersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
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
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testCreateGPSData() throws Exception
	{
		ClientRequest request = null;
		ClientResponse<String> response = null;

		// ==================================================
		// Fahrer anlegen
		// ==================================================
		Driver driverCreated = createDriver( getDriver(), false );

		// ==================================================
		// GPSDaten hinzufügen
		// ==================================================
		GPSData gpsData = getGPSData();
		completeGPSData( gpsData, driverCreated );

		request = getClient( "drivers/" + driverCreated.getId() + "/gpsdata" );
		request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( gpsData ) );

		response = request.post( String.class );
		Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

		log( this.getClass(), "Create_GPS_Data", driverCreated.toString() );
		log( this.getClass(), "Create_GPS_Data", gpsData.toString() );

		// ==================================================
		// GPSDaten prüfen
		// ==================================================
		request = getClient( "gpsdatas" );

		response = request.get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<GPSData> gpsDataListFound = JsonMapper.fromJSONArray( response.getEntity(), GPSData.class );
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
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public void testGetUndeliveredOrders() throws Exception
	{
		ClientRequest request = null;
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
			Order orderCreated = OrderServiceTest.createOrder( order, productCreated, getCustomer(), false );
			ordersCreated.add( orderCreated );
		}

		// ==================================================
		// Bestellung aktualisieren
		// ==================================================
		for (int i = 0; i < ordersCreated.size(); i++)
		{
			Order orderCreated = ordersCreated.get( i );

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

			request = getClient( "drivers/" + driverCreated.getId() + "/orders" );
			request.body( MediaType.APPLICATION_JSON, JsonMapper.toJSON( orderId ) );

			response = request.post( String.class );
			Assert.assertEquals( HttpStatus.SC_NO_CONTENT, response.getStatus() );

			orderCreated.setCurrentStage( Stage.IN_DELIVERY );
		}

		// ==================================================
		// Bestellung des Fahrers auslesen
		// ==================================================
		response = getClient( "drivers/" + driverCreated.getId() + "/orders" ).get( String.class );
		Assert.assertEquals( HttpStatus.SC_OK, response.getStatus() );

		List<Order> driverOrdersFound = JsonMapper.fromJSONArray( response.getEntity(), Order.class );
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
