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

	// Darf nicht weniger als 5 sein!!!
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
			customer.setCompany( "TestCompany" + i );
			customer.setDepartment( "TestDepartment" + i );
			customer.setEmail( "Test" + i + "@mail.com" );
			customer.setGender( Gender.MALE );
			customer.setLevel( "TestLevel" + i );
			customer.setPhone( "01234567" + i );
			customer.setLat( null );
			customer.setLon( null );
			setAddressFields( customer, i );
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

	private static void setAddressFields( Customer customer, int counter )
	{
		if (counter % NUM_LIST_ELEMENTS == 0)
		{
			customer.setCity( "München" );
			customer.setStreet( "Oberbiberger Str. 10" );
			customer.setZipcode( "81547" );
		}
		if (counter % NUM_LIST_ELEMENTS == 1)
		{
			customer.setCity( "München" );
			customer.setStreet( "Adams-Lehmannstr. 16" );
			customer.setZipcode( "80797" );
		}
		if (counter % NUM_LIST_ELEMENTS == 2)
		{
			customer.setCity( "München" );
			customer.setStreet( "Lothstr. 64" );
			customer.setZipcode( "80335" );
		}
		if (counter % NUM_LIST_ELEMENTS == 3)
		{
			customer.setCity( "Schliersee" );
			customer.setStreet( "Schatzelweg 12" );
			customer.setZipcode( "83727" );
		}
		if (counter % NUM_LIST_ELEMENTS == 4)
		{
			customer.setCity( "München" );
			customer.setStreet( "Pestalozzistr. 50" );
			customer.setZipcode( "80469" );
		}
		if (counter % NUM_LIST_ELEMENTS == 5)
		{
			customer.setCity( "Seefeld" );
			customer.setStreet( "Schloßhof 14" );
			customer.setZipcode( "82229" );
		}
		if (counter % NUM_LIST_ELEMENTS == 6)
		{
			customer.setCity( "Grünwald" );
			customer.setStreet( "Tölzerstr. 1" );
			customer.setZipcode( "82031" );
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
	protected static void addOrderLine( Order order, Product product )
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
	protected static void completeGPSData( GPSData gpsData, Driver driver )
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
	protected static void completeDriver( Driver driver, List<GPSData> gpsData, List<Order> orders )
	{
		driver.setGpsData( gpsData );
		driver.setOrders( orders );
	}

	/**
	 * Aktualisert die Koordinaten des Kunden.
	 * 
	 * @param customerReceived
	 *            Empfangender Kunde
	 * @param customerSent
	 *            Gesendeter Kunde
	 */
	protected static void updateCustomerCoordinates( Customer customerReceived, Customer customerSent )
	{
		customerSent.setLat( customerReceived.getLat() );
		customerSent.setLon( customerReceived.getLon() );
	}

	/**
	 * Gibt einen Fahrer zurück.
	 * 
	 * @return Fahrer
	 */
	protected static Driver getDriver()
	{
		return m_drivers.get( 0 );
	}

	/**
	 * Gibt eine Fahrerliste zurück.
	 * 
	 * @return Fahrerliste
	 */
	protected static List<Driver> getDriverList()
	{
		return m_drivers;
	}

	/**
	 * Gibt einen Kunden zurück.
	 * 
	 * @return Kunde
	 */
	protected static Customer getCustomer()
	{
		return m_customers.get( 0 );
	}

	/**
	 * Gibt eine Kundenliste zurück.
	 * 
	 * @return Kundenliste
	 */
	protected static List<Customer> getCustomerList()
	{
		return m_customers;
	}

	/**
	 * Gibt eine Produkt zurück.
	 * 
	 * @return Produkt
	 */
	protected static Product getProduct()
	{
		return m_products.get( 0 );
	}

	/**
	 * Gibt eine Produktliste zurück.
	 * 
	 * @return Produktliste
	 */
	protected static List<Product> getProductList()
	{
		return m_products;
	}

	/**
	 * Gibt eine Bestellung zurück.
	 * 
	 * @return Bestellung
	 */
	protected static Order getOrder()
	{
		return m_orders.get( 0 );
	}

	/**
	 * Gibt eine Bestellungsliste zurück.
	 * 
	 * @return Bestellungsliste
	 */
	protected static List<Order> getOrderList()
	{
		return m_orders;
	}

	/**
	 * Gibt ein GPS-Datum zurück.
	 * 
	 * @return GPS-Datum
	 */
	protected static GPSData getGPSData()
	{
		return m_gpsData.get( 0 );
	}

	/**
	 * Gibt eine GPS-Datenliste zurück.
	 * 
	 * @return GPS-Datenliste
	 */
	protected static List<GPSData> getGPSDataList()
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
	protected static void assertDriverEquals( Driver driverReceived, Driver driverSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( driverSent.getId(), driverReceived.getId() );
		}
		Assert.assertEquals( driverSent.getName(), driverReceived.getName() );
		Assert.assertEquals( driverSent.getGpsData(), driverReceived.getGpsData() );
		Assert.assertEquals( driverSent.getOrders(), driverReceived.getOrders() );
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
	protected static void assertCustomerEquals( Customer customerReceived, Customer customerSent, boolean assertId )
	{
		updateCustomerCoordinates( customerReceived, customerSent );

		if (assertId)
		{
			Assert.assertEquals( customerSent.getId(), customerReceived.getId() );
		}
		Assert.assertEquals( customerSent.getAddressAsString(), customerReceived.getAddressAsString() );
		Assert.assertEquals( customerSent.getCity(), customerReceived.getCity() );
		Assert.assertEquals( customerSent.getCompany(), customerReceived.getCompany() );
		Assert.assertEquals( customerSent.getDepartment(), customerReceived.getDepartment() );
		Assert.assertEquals( customerSent.getEmail(), customerReceived.getEmail() );
		Assert.assertEquals( customerSent.getForename(), customerReceived.getForename() );
		Assert.assertEquals( customerSent.getLastname(), customerReceived.getLastname() );
		Assert.assertEquals( customerSent.getLat(), customerReceived.getLat() );
		Assert.assertEquals( customerSent.getLon(), customerReceived.getLon() );
		Assert.assertEquals( customerSent.getLevel(), customerReceived.getLevel() );
		Assert.assertEquals( customerSent.getPhone(), customerReceived.getPhone() );
		Assert.assertEquals( customerSent.getStreet(), customerReceived.getStreet() );
		Assert.assertEquals( customerSent.getZipcode(), customerReceived.getZipcode() );
		Assert.assertEquals( customerSent.getGender(), customerReceived.getGender() );
	}

	/**
	 * Prüft Produkt auf Gleichheit.
	 * 
	 * @param productReceived
	 *            Empfangenes Produkt
	 * @param productSent
	 *            Gesendetes Produkt
	 * @param assertId
	 *            ID in Assertion miteinbeziehen
	 */
	protected static void assertProductEquals( Product productReceived, Product productSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( productSent.getId(), productReceived.getId() );
		}
		Assert.assertEquals( productSent.getName(), productReceived.getName() );
		Assert.assertEquals( productSent.getDescription(), productReceived.getDescription() );
		Assert.assertEquals( productSent.getImageUrl(), productReceived.getImageUrl() );
		Assert.assertEquals( Double.parseDouble( productSent.getPriceL() ), Double.parseDouble( productReceived.getPriceL() ) );
		Assert.assertEquals( Double.parseDouble( productSent.getPriceXL() ), Double.parseDouble( productReceived.getPriceXL() ) );
		Assert.assertEquals( Double.parseDouble( productSent.getPriceXXL() ), Double.parseDouble( productReceived.getPriceXXL() ) );
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
	protected static void assertOrderEquals( Order orderReceived, Order orderSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( orderSent.getId(), orderReceived.getId() );
		}
		Assert.assertEquals( orderSent.getNote(), orderReceived.getNote() );
		Assert.assertEquals( orderSent.getPrice(), orderReceived.getPrice() );
		Assert.assertEquals( orderSent.getCurrentStage(), orderReceived.getCurrentStage() );
		Assert.assertEquals( orderSent.getOrderDate().getTime(), orderReceived.getOrderDate().getTime(), 2000L );

		assertCustomerEquals( orderReceived.getCustomer(), orderSent.getCustomer(), false );
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
	protected static void assertContainsOrder( List<Order> ordersReceived, Order orderSent, boolean assertId )
	{
		boolean contained = false;

		for (Order orderReceived : ordersReceived)
		{
			try
			{
				if (assertId)
				{
					Assert.assertEquals( orderSent.getId(), orderReceived.getId() );
				}
				Assert.assertEquals( orderSent.getNote(), orderReceived.getNote() );
				Assert.assertEquals( orderSent.getPrice(), orderReceived.getPrice() );
				Assert.assertEquals( orderSent.getCurrentStage(), orderReceived.getCurrentStage() );
				Assert.assertEquals( orderSent.getOrderDate().getTime(), orderReceived.getOrderDate().getTime(), 2000L );

				assertCustomerEquals( orderReceived.getCustomer(), orderSent.getCustomer(), false );
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
	protected static void assertOrderLinesEquals( List<OrderLine> orderLinesReceived, List<OrderLine> orderLinesSent,
			boolean assertId )
	{
		Assert.assertEquals( orderLinesSent.size(), orderLinesReceived.size() );

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
	protected static void assertOrderLineEquals( OrderLine orderLineReceived, OrderLine orderLineSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( orderLineSent.getId(), orderLineReceived.getId() );
		}
		Assert.assertEquals( orderLineSent.getProductId(), orderLineReceived.getProductId() );
		Assert.assertEquals( orderLineSent.getQuantity(), orderLineReceived.getQuantity() );
		Assert.assertEquals( orderLineSent.getSize(), orderLineReceived.getSize() );
	}

	/**
	 * Prüft OrderStage String auf Gleichheit.
	 * 
	 * @param receivedString
	 *            Empfangener OrderStage String
	 * @param expectedStage
	 *            Erwartete OrderStage
	 */
	protected static void assertNextOrderStage( String receivedString, Stage expectedStage )
	{
		Assert.assertNotNull( expectedStage );
		Assert.assertNotNull( receivedString );

		String expectedString = "{\"nextStage\": " + expectedStage.name() + "}";
		Assert.assertEquals( expectedString, receivedString );
	}

	/**
	 * Prüft OrderStage String auf Gleichheit.
	 * 
	 * @param receivedString
	 *            Empfangener OrderStage String
	 * @param expectedStage
	 *            Erwartete OrderStage
	 */
	protected static void assertPreviousOrderStage( String receivedString, Stage expectedStage )
	{
		Assert.assertNotNull( expectedStage );
		Assert.assertNotNull( receivedString );

		String expectedString = "{\"previousStage\": " + expectedStage.name() + "}";
		Assert.assertEquals( expectedString, receivedString );
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
	protected static void assertGPSDataEquals( GPSData gpsDataReceived, GPSData gpsDataSent, boolean assertId )
	{
		if (assertId)
		{
			Assert.assertEquals( gpsDataSent.getId(), gpsDataReceived.getId() );
		}
		Assert.assertEquals( gpsDataSent.getLat(), gpsDataReceived.getLat() );
		Assert.assertEquals( gpsDataSent.getLon(), gpsDataReceived.getLon() );
		Assert.assertEquals( gpsDataSent.getDate().getTime(), gpsDataReceived.getDate().getTime(), 2000L );

		assertDriverEquals( gpsDataReceived.getDriver(), gpsDataSent.getDriver(), false );
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
	protected static void assertContainsGPSData( List<GPSData> gpsDataListReceived, GPSData gpsDataSent, boolean assertId,
			boolean updateId )
	{
		boolean contained = false;

		for (GPSData gpsDataReceived : gpsDataListReceived)
		{
			try
			{
				if (assertId)
				{
					Assert.assertEquals( gpsDataSent.getId(), gpsDataReceived.getId() );
				}
				Assert.assertEquals( gpsDataSent.getLat(), gpsDataReceived.getLat() );
				Assert.assertEquals( gpsDataSent.getLon(), gpsDataReceived.getLon() );
				Assert.assertEquals( gpsDataSent.getDate().getTime(), gpsDataReceived.getDate().getTime(), 2000L );

				assertDriverEquals( gpsDataReceived.getDriver(), gpsDataSent.getDriver(), false );

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
