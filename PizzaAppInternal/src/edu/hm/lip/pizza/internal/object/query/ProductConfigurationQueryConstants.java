package edu.hm.lip.pizza.internal.object.query;

/**
 * Konstanten für Queries der ProductConfiguration Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class ProductConfigurationQueryConstants
{

	private ProductConfigurationQueryConstants()
	{

	}

	/**
	 * Name der "Produktkonfiguration gefiltert nach Produkt und Größe"-Query.
	 */
	public static final String PRODUCT_CONFIG_BY_PRODUCT_AND_SIZE = "ProductConfigByProductAndSizeQuery";

	/**
	 * Produktkonfiguration gefiltert nach Produkt und Größe.
	 */
	public static final String PRODUCT_CONFIG_BY_PRODUCT_AND_SIZE_QUERY = "FROM EntityProductConfiguration "
			+ "WHERE product_id = :product_id and size = :size";

}
