package edu.hm.lip.pizza.internal.converter;

import java.util.ArrayList;
import java.util.List;

import edu.hm.lip.pizza.api.object.ressources.Customer;
import edu.hm.lip.pizza.internal.object.entities.EntityCustomer;

/**
 * Konverter-Klasse fuer Customer-Objekte, um zwischen dem Service-Datenmodell und dem Entitaeten-Datenmodell zu
 * konvertieren.
 * 
 * @author Franz Mathauser
 */
public final class CustomerConverter
{

	private CustomerConverter()
	{

	}

	/**
	 * Konvertiert das Entity-Customer-Objekt in ein ProductConfiguration-Objekt im Service-Datenmodell.
	 * 
	 * @param eCustomer
	 *            Entity-Customer-Objekt
	 * @return Customer-Objekt aus dem Service-Datenmodell
	 */
	public static Customer convertEntityToServiceCustomer( EntityCustomer eCustomer )
	{
		if (eCustomer == null)
		{
			return null;
		}

		Customer customer = new Customer();
		customer.setId( eCustomer.getId() );
		customer.setGender( eCustomer.getGender() );
		customer.setCompany( eCustomer.getCompany() );
		customer.setDepartment( eCustomer.getDepartment() );
		customer.setLastname( eCustomer.getLastname() );
		customer.setForename( eCustomer.getForename() );
		customer.setStreet( eCustomer.getStreet() );
		customer.setZipcode( eCustomer.getZipcode() );
		customer.setCity( eCustomer.getCity() );
		customer.setLevel( eCustomer.getLevel() );
		customer.setPhone( eCustomer.getPhone() );
		customer.setEmail( eCustomer.getEmail() );

		return customer;
	}

	/**
	 * Konvertiert ein Customer-Objekt aus dem Service-Datenmodell in ein Entity-Customer-Objekt.
	 * 
	 * @param customer
	 *            Customer-Objekt aus dem Service-Datenmodell
	 * @return Entity-Customer-Objekt
	 */
	public static EntityCustomer convertServiceToEntityCustomer( Customer customer )
	{
		if (customer == null)
		{
			return null;
		}

		EntityCustomer eCustomer = new EntityCustomer();

		eCustomer.setId( customer.getId() );
		eCustomer.setGender( customer.getGender() );
		eCustomer.setCompany( customer.getCompany() );
		eCustomer.setDepartment( customer.getDepartment() );
		eCustomer.setLastname( customer.getLastname() );
		eCustomer.setForename( customer.getForename() );
		eCustomer.setStreet( customer.getStreet() );
		eCustomer.setZipcode( customer.getZipcode() );
		eCustomer.setCity( customer.getCity() );
		eCustomer.setLevel( customer.getLevel() );
		eCustomer.setPhone( customer.getPhone() );
		eCustomer.setEmail( customer.getEmail() );

		return eCustomer;
	}

	/**
	 * Konvertiert eine Liste von Entity-Customer-Objekten in eine Liste von Customer-Objekten im Service-Datenmodell.
	 * 
	 * @param eCustomerList
	 *            Liste von Entity-Customer-Objekten
	 * @return Liste von Customer-Objekten
	 */
	public static List<Customer> convertEntityToServiceCustomerList( List<EntityCustomer> eCustomerList )
	{
		List<Customer> customers = new ArrayList<Customer>();

		for (EntityCustomer eCustomer : eCustomerList)
		{
			customers.add( convertEntityToServiceCustomer( eCustomer ) );
		}

		return customers;
	}

}
