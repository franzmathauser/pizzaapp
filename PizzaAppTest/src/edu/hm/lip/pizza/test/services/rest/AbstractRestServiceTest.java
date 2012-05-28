package edu.hm.lip.pizza.test.services.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.hm.lip.pizza.api.communication.request.ICustomerService;
import edu.hm.lip.pizza.api.communication.request.IDriverService;
import edu.hm.lip.pizza.api.communication.request.IOrderService;
import edu.hm.lip.pizza.api.communication.request.IProductService;
import edu.hm.lip.pizza.api.object.enumeration.Gender;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderLine;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.AbstractTest;

import junit.framework.Assert;

/**
 * Abstrakte Basisklasse für REST Service Tests.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractRestServiceTest extends AbstractTest
{

	private static final int NUM_LIST_ELEMENTS = 3;

	private static List<Driver> m_drivers = new ArrayList<Driver>();

	private static List<Customer> m_customers = new ArrayList<Customer>();

	private static List<Product> m_products = new ArrayList<Product>();

	private static List<Order> m_orders = new ArrayList<Order>();

	/**
	 * Initialisiert die Ressource Listen.
	 */
	@BeforeClass
	public static void initializeLists()
	{
		for (int i = 0; i < NUM_LIST_ELEMENTS; i++)
		{
			Driver driver = new Driver();
			driver.setId( null );
			driver.setName( "TestDriver" + i );
			// muss an entsprechender Stelle richtig gesetzt werden
			driver.setOrders( null );
			// muss an entsprechender Stelle richtig gesetzt werden
			driver.setGpsData( null );
			m_drivers.add( driver );

			Customer customer = new Customer();
			customer.setId( null );
			customer.setForename( "TestForname" + i );
			customer.setLastname( "TestLastname" + i );
			customer.setCity( "TestCity" + i );
			customer.setCompany( "TestCompany" + i );
			customer.setDepartment( "TestDepartment" + i );
			customer.setEmail( "Test" + i + "@mail.com" );
			customer.setGender( Gender.MALE );
			customer.setLevel( "TestLevel" + i );
			customer.setPhone( "01234567" + i );
			customer.setStreet( "TestStreet" + i );
			customer.setZipcode( "1000" + i );
			customer.setLat( "11." + i );
			customer.setLon( "11." + i );
			m_customers.add( customer );

			Product product = new Product();
			product.setId( null );
			product.setName( "TestName" + i );
			product.setDescription( "TestDescription" + i );
			product.setImageUrl( "testURL" + i + ".png" );
			product.setPriceL( "8.9" + i );
			product.setPriceXL( "10.9" + i );
			product.setPriceXXL( "12.9" + i );
			m_products.add( product );

			Order order = new Order();
			order.setId( null );
			order.setNote( "TestNote" + i );
			order.setCurrentStage( Stage.ORDERED );
			// muss an entsprechender Stelle richtig gesetzt werden
			order.setCustomer( null );
			order.setOrderDate( new Date() );
			// muss an entsprechender Stelle richtig gesetzt werden
			order.setOrderLines( null );
			// muss an entsprechender Stelle richtig gesetzt werden
			order.setPrice( null );
			m_orders.add( order );
		}
	}

	/**
	 * Gibt einen Fahrer zurück.
	 * 
	 * @return Fahrer
	 */
	protected Driver getDriver()
	{
		return m_drivers.get( 0 );
	}

	/**
	 * Gibt eine Fahrerliste zurück.
	 * 
	 * @return Fahrerliste
	 */
	protected List<Driver> getDriverList()
	{
		return m_drivers;
	}

	/**
	 * Gibt einen Kunden zurück.
	 * 
	 * @return Kunde
	 */
	protected Customer getCustomer()
	{
		return m_customers.get( 0 );
	}

	/**
	 * Gibt eine Kundenliste zurück.
	 * 
	 * @return Kundenliste
	 */
	protected List<Customer> getCustomerList()
	{
		return m_customers;
	}

	/**
	 * Gibt eine Produkt zurück.
	 * 
	 * @return Produkt
	 */
	protected Product getProduct()
	{
		return m_products.get( 0 );
	}

	/**
	 * Gibt eine Produktliste zurück.
	 * 
	 * @return Produktliste
	 */
	protected List<Product> getProductList()
	{
		return m_products;
	}

	/**
	 * Gibt eine Bestellung zurück.
	 * 
	 * @return Bestellung
	 */
	protected Order getOrder()
	{
		return m_orders.get( 0 );
	}

	/**
	 * Gibt eine Bestellungsliste zurück.
	 * 
	 * @return Bestellungsliste
	 */
	protected List<Order> getOrderList()
	{
		return m_orders;
	}

	/**
	 * @return
	 */
	protected IDriverService getDriverProxy()
	{
		return ProxyFactory.create( IDriverService.class, getRestUrl() );
	}

	/**
	 * @return
	 */
	protected ICustomerService getCustomerProxy()
	{
		return ProxyFactory.create( ICustomerService.class, getRestUrl() );
	}

	/**
	 * @return
	 */
	protected IProductService getProductProxy()
	{
		return ProxyFactory.create( IProductService.class, getRestUrl() );
	}

	/**
	 * @return
	 */
	protected IOrderService getOrderProxy()
	{
		return ProxyFactory.create( IOrderService.class, getRestUrl() );
	}

	/**
	 * @param driverCreated
	 * @param driver
	 */
	protected void assertDriverEqualsWithoutId( Driver driverCreated, Driver driver )
	{
		Assert.assertEquals( driverCreated.getName(), driver.getName() );
		Assert.assertEquals( driverCreated.getGpsData(), driver.getGpsData() );
		Assert.assertEquals( driverCreated.getOrders(), driver.getOrders() );
	}

	/**
	 * @param customerCreated
	 * @param customer
	 */
	protected void assertCustomerEqualsWithoutId( Customer customerCreated, Customer customer )
	{
		Assert.assertEquals( customerCreated.getAddressAsString(), customer.getAddressAsString() );
		Assert.assertEquals( customerCreated.getCity(), customer.getCity() );
		Assert.assertEquals( customerCreated.getCompany(), customer.getCompany() );
		Assert.assertEquals( customerCreated.getDepartment(), customer.getDepartment() );
		Assert.assertEquals( customerCreated.getEmail(), customer.getEmail() );
		Assert.assertEquals( customerCreated.getForename(), customer.getForename() );
		Assert.assertEquals( customerCreated.getLastname(), customer.getLastname() );
		Assert.assertEquals( customerCreated.getLat(), customer.getLat() );
		Assert.assertEquals( customerCreated.getLon(), customer.getLon() );
		Assert.assertEquals( customerCreated.getLevel(), customer.getLevel() );
		Assert.assertEquals( customerCreated.getPhone(), customer.getPhone() );
		Assert.assertEquals( customerCreated.getStreet(), customer.getStreet() );
		Assert.assertEquals( customerCreated.getZipcode(), customer.getZipcode() );
		Assert.assertEquals( customerCreated.getGender(), customer.getGender() );
	}

	/**
	 * @param productCreated
	 * @param product
	 */
	protected void assertProductEqualsWithoutId( Product productCreated, Product product )
	{
		Assert.assertEquals( productCreated.getName(), product.getName() );
		Assert.assertEquals( productCreated.getDescription(), product.getDescription() );
		Assert.assertEquals( productCreated.getImageUrl(), product.getImageUrl() );
		Assert.assertEquals( Double.parseDouble( productCreated.getPriceL() ), Double.parseDouble( product.getPriceL() ) );
		Assert.assertEquals( Double.parseDouble( productCreated.getPriceXL() ), Double.parseDouble( product.getPriceXL() ) );
		Assert.assertEquals( Double.parseDouble( productCreated.getPriceXXL() ), Double.parseDouble( product.getPriceXXL() ) );
	}

	/**
	 * @param orderCreated
	 * @param order
	 */
	protected void assertOrderEqualsWithoutId( Order orderCreated, Order order )
	{
		Assert.assertEquals( orderCreated.getNote(), order.getNote() );
		Assert.assertEquals( orderCreated.getPrice(), order.getPrice() );
		Assert.assertEquals( orderCreated.getCurrentStage(), order.getCurrentStage() );
		assertCustomerEqualsWithoutId( orderCreated.getCustomer(), order.getCustomer() );
		Assert.assertTrue( orderCreated.getOrderDate().getTime() >= order.getOrderDate().getTime() );
		assertOrderLinesEqualsWithoutId( orderCreated.getOrderLines(), order.getOrderLines() );
	}

	/**
	 * @param orderLinesCreated
	 * @param orderLines
	 */
	protected void assertOrderLinesEqualsWithoutId( List<OrderLine> orderLinesCreated, List<OrderLine> orderLines )
	{
		Assert.assertEquals( orderLinesCreated.size(), orderLines.size() );

		for (int i = 0; i < orderLines.size(); i++)
		{
			assertOrderLineEqualsWithoutId( orderLinesCreated.get( i ), orderLines.get( i ) );
		}
	}

	/**
	 * @param orderLineCreated
	 * @param orderLine
	 */
	protected void assertOrderLineEqualsWithoutId( OrderLine orderLineCreated, OrderLine orderLine )
	{
		Assert.assertEquals( orderLineCreated.getProductId(), orderLine.getProductId() );
		Assert.assertEquals( orderLineCreated.getQuantity(), orderLine.getQuantity() );
		Assert.assertEquals( orderLineCreated.getSize(), orderLine.getSize() );
	}

	/**
	 * Testet die CREATE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public abstract void testCreate() throws Exception;

	/**
	 * Testet die FIND ALL Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public abstract void testFindAll() throws Exception;

	/**
	 * Testet die FIND Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public abstract void testFind() throws Exception;

	/**
	 * Testet die UPDATE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public abstract void testUpdate() throws Exception;

	/**
	 * Testet die REMOVE Funktion.
	 * 
	 * @throws Exception
	 *             Testfehler
	 */
	@Test
	public abstract void testRemove() throws Exception;

}
