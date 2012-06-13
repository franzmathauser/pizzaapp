package edu.hm.lip.pizza.internal.object.query;

/**
 * Konstanten für Queries der Customer Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class CustomerQueryConstants
{

	private CustomerQueryConstants()
	{

	}

	/**
	 * Name der "Alle Kunden"-Query.
	 */
	public static final String GET_ALL_CUSTOMERS = "GetAllCustomersQuery";

	/**
	 * Alle Kunden.
	 */
	public static final String GET_ALL_CUSTOMERS_QUERY = "SELECT c FROM EntityCustomer c";

}
