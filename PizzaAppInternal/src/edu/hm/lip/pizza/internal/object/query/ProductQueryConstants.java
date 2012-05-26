package edu.hm.lip.pizza.internal.object.query;

/**
 * Konstanten für Queries der Product Entität.
 * 
 * @author Stefan Wörner
 */
public final class ProductQueryConstants
{

	private ProductQueryConstants()
	{

	}

	/**
	 * Name der "Alle Produkte"-Query.
	 */
	public static final String GET_ALL_PRODUCTS = "GetAllProductsQuery";

	/**
	 * Alle Produkte.
	 */
	public static final String GET_ALL_PRODUCTS_QUERY = "SELECT p FROM EntityProduct p";

}
