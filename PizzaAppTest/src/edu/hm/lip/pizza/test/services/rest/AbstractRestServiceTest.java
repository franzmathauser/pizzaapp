package edu.hm.lip.pizza.test.services.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.hm.lip.pizza.api.object.enumeration.Gender;
import edu.hm.lip.pizza.api.object.resource.Customer;
import edu.hm.lip.pizza.api.object.resource.Driver;
import edu.hm.lip.pizza.test.AbstractTest;

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

			m_drivers.add( driver );
		}

		for (int i = 0; i < NUM_LIST_ELEMENTS; i++)
		{
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
			customer.setZipcode( "0000" + i );
			customer.setLat( "11." + i );
			customer.setLon( "11." + i );

			m_customers.add( customer );
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
