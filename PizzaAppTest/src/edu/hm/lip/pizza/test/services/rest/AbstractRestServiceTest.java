package edu.hm.lip.pizza.test.services.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;

import edu.hm.lip.pizza.api.object.enumeration.Gender;
import edu.hm.lip.pizza.api.object.enumeration.Size;
import edu.hm.lip.pizza.api.object.enumeration.Stage;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.api.object.resource.GPSData;
import edu.hm.lip.pizza.api.object.resource.Order;
import edu.hm.lip.pizza.api.object.resource.OrderLine;
import edu.hm.lip.pizza.api.object.resource.Product;
import edu.hm.lip.pizza.test.AbstractTest;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

/**
 * Abstrakte Basisklasse für REST Service Tests.
 * 
 * @author Stefan Wörner
 */
public abstract class AbstractRestServiceTest extends AbstractTest
{

	private static final int NUM_LIST_ELEMENTS = 5;

	private static List<Driver> m_drivers = new ArrayList<Driver>();

	private static List<Customer> m_customers = new ArrayList<Customer>();

	private static List<Product> m_products = new ArrayList<Product>();

	private static List<Order> m_orders = new ArrayList<Order>();

	private static List<GPSData> m_gpsData = new ArrayList<GPSData>();

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
			customer.setLat( "48." + i );
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
			// muss an entsprechender Stelle richtig gesetzt werden
			order.setOrderDate( null );
			// muss an entsprechender Stelle richtig gesetzt werden
			order.setOrderLines( null );
			// muss an entsprechender Stelle richtig gesetzt werden
			order.setPrice( null );
			m_orders.add( order );

			GPSData gpsData = new GPSData();
			gpsData.setId( null );
			gpsData.setLat( Double.parseDouble( "48." + i ) );
			gpsData.setLon( Double.parseDouble( "11." + i ) );
			// muss an entsprechender Stelle richtig gesetzt werden
			gpsData.setDriver( null );
			// muss an entsprechender Stelle richtig gesetzt werden
			gpsData.setDate( null );
			m_gpsData.add( gpsData );
		}
	}

	/**
	 * Fügt der Order eine neue Orderline hinzu.
	 * 
	 * @param order
	 *            Bestellung
	 * @param product
	 *            Produkt
	 */
	protected void addOrderLine( Order order, Product product )
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
		order.setOrderDate( new Date() );
	}

	/**
	 * Vervollständigt die fehlenden Attributfelder des GPS Datums.
	 * 
	 * @param gpsData
	 *            GPS Datum
	 * @param driver
	 *            Fahrer
	 */
	protected void completeGPSData( GPSData gpsData, Driver driver )
	{
		gpsData.setDriver( driver );
		gpsData.setDate( new Date() );
	}

	/**
	 * Vervollständigt die fehlenden Attributfelder des Fahrers.
	 * 
	 * @param driver
	 *            Fahrer
	 * @param gpsData
	 *            GPS Daten
	 * @param orders
	 *            Bestellungen
	 */
	protected void completeDriver( Driver driver, List<GPSData> gpsData, List<Order> orders )
	{
		driver.setGpsData( gpsData );
		driver.setOrders( orders );
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
	 * Gibt ein GPS-Datum zurück.
	 * 
	 * @return GPS-Datum
	 */
	protected GPSData getGPSData()
	{
		return m_gpsData.get( 0 );
	}

	/**
	 * Gibt eine GPS-Datenliste zurück.
	 * 
	 * @return GPS-Datenliste
	 */
	protected List<GPSData> getGPSDataList()
	{
		return m_gpsData;
	}

	/**
	 * Prüft Fahrer auf Gleichheit.
	 * 
	 * @param driverReceived
	 *            Empfangener Fahrer
	 * @param driverSent
	 *            Gesendeter Fahrer
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertDriverEquals( Driver driverReceived, Driver driverSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( driverReceived.getId(), driverSent.getId() );
		}
		Assert.assertEquals( driverReceived.getName(), driverSent.getName() );
		Assert.assertEquals( driverReceived.getGpsData(), driverSent.getGpsData() );
		Assert.assertEquals( driverReceived.getOrders(), driverSent.getOrders() );
	}

	/**
	 * Prüft Kunden auf Gleichheit.
	 * 
	 * @param customerReceived
	 *            Empfangener Kunde
	 * @param customerSent
	 *            Gesendeter Kunde
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertCustomerEquals( Customer customerReceived, Customer customerSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( customerReceived.getId(), customerSent.getId() );
		}
		Assert.assertEquals( customerReceived.getAddressAsString(), customerSent.getAddressAsString() );
		Assert.assertEquals( customerReceived.getCity(), customerSent.getCity() );
		Assert.assertEquals( customerReceived.getCompany(), customerSent.getCompany() );
		Assert.assertEquals( customerReceived.getDepartment(), customerSent.getDepartment() );
		Assert.assertEquals( customerReceived.getEmail(), customerSent.getEmail() );
		Assert.assertEquals( customerReceived.getForename(), customerSent.getForename() );
		Assert.assertEquals( customerReceived.getLastname(), customerSent.getLastname() );
		Assert.assertEquals( customerReceived.getLat(), customerSent.getLat() );
		Assert.assertEquals( customerReceived.getLon(), customerSent.getLon() );
		Assert.assertEquals( customerReceived.getLevel(), customerSent.getLevel() );
		Assert.assertEquals( customerReceived.getPhone(), customerSent.getPhone() );
		Assert.assertEquals( customerReceived.getStreet(), customerSent.getStreet() );
		Assert.assertEquals( customerReceived.getZipcode(), customerSent.getZipcode() );
		Assert.assertEquals( customerReceived.getGender(), customerSent.getGender() );
	}

	/**
	 * Prüft Produkt auf Gleichheit.
	 * 
	 * @param productReceived
	 *            Empfangenes Produkt
	 * @param productSend
	 *            Gesendetes Produkt
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertProductEquals( Product productReceived, Product productSend, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( productReceived.getId(), productSend.getId() );
		}
		Assert.assertEquals( productReceived.getName(), productSend.getName() );
		Assert.assertEquals( productReceived.getDescription(), productSend.getDescription() );
		Assert.assertEquals( productReceived.getImageUrl(), productSend.getImageUrl() );
		Assert.assertEquals( Double.parseDouble( productReceived.getPriceL() ), Double.parseDouble( productSend.getPriceL() ) );
		Assert.assertEquals( Double.parseDouble( productReceived.getPriceXL() ), Double.parseDouble( productSend.getPriceXL() ) );
		Assert.assertEquals( Double.parseDouble( productReceived.getPriceXXL() ), Double.parseDouble( productSend.getPriceXXL() ) );
	}

	/**
	 * Prüft Bestellung auf Gleichheit. Für das Bestelldatum wird ein Zeitversatz von 2 Sekunden berücksichtigt.
	 * 
	 * @param orderReceived
	 *            Empfangene Bestellung
	 * @param orderSent
	 *            Gesendete Bestellung
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertOrderEquals( Order orderReceived, Order orderSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( orderReceived.getId(), orderSent.getId() );
		}
		Assert.assertEquals( orderReceived.getNote(), orderSent.getNote() );
		Assert.assertEquals( orderReceived.getPrice(), orderSent.getPrice() );
		Assert.assertEquals( orderReceived.getCurrentStage(), orderSent.getCurrentStage() );
		assertCustomerEquals( orderReceived.getCustomer(), orderSent.getCustomer(), false );
		Assert.assertEquals( orderReceived.getOrderDate().getTime(), orderSent.getOrderDate().getTime(), 2000L );
		assertOrderLinesEquals( orderReceived.getOrderLines(), orderSent.getOrderLines(), false );
	}

	/**
	 * Prüft ob Bestellung in Liste enthalten ist. Für das Bestelldatum wird ein Zeitversatz von 2 Sekunden
	 * berücksichtigt.
	 * 
	 * @param ordersReceived
	 *            Empfangene Bestellungen
	 * @param orderSent
	 *            Gesendete Bestellung
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertContainsOrder( List<Order> ordersReceived, Order orderSent, boolean assertId )
	{
		boolean contained = false;

		for (Order orderReceived : ordersReceived)
		{
			try
			{
				if (assertId)
				{
					Assert.assertEquals( orderReceived.getId(), orderSent.getId() );
				}
				Assert.assertEquals( orderReceived.getNote(), orderSent.getNote() );
				Assert.assertEquals( orderReceived.getPrice(), orderSent.getPrice() );
				Assert.assertEquals( orderReceived.getCurrentStage(), orderSent.getCurrentStage() );
				assertCustomerEquals( orderReceived.getCustomer(), orderSent.getCustomer(), false );
				Assert.assertEquals( orderReceived.getOrderDate().getTime(), orderSent.getOrderDate().getTime(), 2000L );
				assertOrderLinesEquals( orderReceived.getOrderLines(), orderSent.getOrderLines(), false );

				contained = true;
				break;
			}
			catch (AssertionFailedError e)
			{
				contained = false;
			}
		}

		Assert.assertTrue( contained );
	}

	/**
	 * Prüft Bestellzeilen auf Gleichheit.
	 * 
	 * @param orderLinesReceived
	 *            Empfangene Bestellzeilen
	 * @param orderLinesSent
	 *            Gesendete Bestellzeilen
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertOrderLinesEquals( List<OrderLine> orderLinesReceived, List<OrderLine> orderLinesSent, boolean assertId )
	{
		Assert.assertEquals( orderLinesReceived.size(), orderLinesSent.size() );

		for (int i = 0; i < orderLinesSent.size(); i++)
		{
			assertOrderLineEquals( orderLinesReceived.get( i ), orderLinesSent.get( i ), assertId );
		}
	}

	/**
	 * Prüft Bestellzeile auf Gleichheit.
	 * 
	 * @param orderLineReceived
	 *            Empfangene Bestellzeile
	 * @param orderLineSent
	 *            Gesendete Bestellzeile
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertOrderLineEquals( OrderLine orderLineReceived, OrderLine orderLineSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( orderLineReceived.getId(), orderLineSent.getId() );
		}
		Assert.assertEquals( orderLineReceived.getProductId(), orderLineSent.getProductId() );
		Assert.assertEquals( orderLineReceived.getQuantity(), orderLineSent.getQuantity() );
		Assert.assertEquals( orderLineReceived.getSize(), orderLineSent.getSize() );
	}

	/**
	 * Prüft OrderStage String auf Gleichheit.
	 * 
	 * @param receivedString
	 *            Empfangener OrderStage String
	 * @param expectedStage
	 *            Erwartete OrderStage
	 */
	protected void assertNextOrderStage( String receivedString, Stage expectedStage )
	{
		Assert.assertNotNull( expectedStage );
		Assert.assertNotNull( receivedString );

		String expectedString = "{\"nextStage\": " + expectedStage.name() + "}";
		Assert.assertEquals( receivedString, expectedString );
	}

	/**
	 * Prüft OrderStage String auf Gleichheit.
	 * 
	 * @param receivedString
	 *            Empfangener OrderStage String
	 * @param expectedStage
	 *            Erwartete OrderStage
	 */
	protected void assertPreviousOrderStage( String receivedString, Stage expectedStage )
	{
		Assert.assertNotNull( expectedStage );
		Assert.assertNotNull( receivedString );

		String expectedString = "{\"previousStage\": " + expectedStage.name() + "}";
		Assert.assertEquals( receivedString, expectedString );
	}

	/**
	 * Prüft Bestellung auf Gleichheit. Für das Bestelldatum wird ein Zeitversatz von 2 Sekunden berücksichtigt.
	 * 
	 * @param gpsDataReceived
	 *            Empfangene Bestellung
	 * @param gpsDataSent
	 *            Gesendete Bestellung
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected void assertGPSDataEquals( GPSData gpsDataReceived, GPSData gpsDataSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( gpsDataReceived.getId(), gpsDataSent.getId() );
		}
		Assert.assertEquals( gpsDataReceived.getLat(), gpsDataSent.getLat() );
		Assert.assertEquals( gpsDataReceived.getLon(), gpsDataSent.getLon() );
		assertDriverEquals( gpsDataReceived.getDriver(), gpsDataSent.getDriver(), false );
		Assert.assertEquals( gpsDataReceived.getDate().getTime(), gpsDataSent.getDate().getTime(), 2000L );
	}

	/**
	 * Prüft ob GPS Datum in Liste enthalten ist. Für das Datumsfeld wird ein Zeitversatz von 2 Sekunden berücksichtigt.
	 * 
	 * @param gpsDataListReceived
	 *            Empfangene GPS Daten
	 * @param gpsDataSent
	 *            Gesendetes GPS Datum
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 * @param updateId
	 *            ID nach fundenenem Element aktualisieren
	 */
	protected void assertContainsGPSData( List<GPSData> gpsDataListReceived, GPSData gpsDataSent, boolean assertId,
			boolean updateId )
	{
		boolean contained = false;

		for (GPSData gpsDataReceived : gpsDataListReceived)
		{
			try
			{
				if (assertId)
				{
					Assert.assertEquals( gpsDataReceived.getId(), gpsDataSent.getId() );
				}
				Assert.assertEquals( gpsDataReceived.getLat(), gpsDataSent.getLat() );
				Assert.assertEquals( gpsDataReceived.getLon(), gpsDataSent.getLon() );
				assertDriverEquals( gpsDataReceived.getDriver(), gpsDataSent.getDriver(), false );
				Assert.assertEquals( gpsDataReceived.getDate().getTime(), gpsDataSent.getDate().getTime(), 2000L );

				if (updateId)
				{
					gpsDataSent.setId( gpsDataReceived.getId() );
				}
				contained = true;
				break;
			}
			catch (AssertionFailedError e)
			{
				contained = false;
			}
		}

		Assert.assertTrue( contained );
	}

}
