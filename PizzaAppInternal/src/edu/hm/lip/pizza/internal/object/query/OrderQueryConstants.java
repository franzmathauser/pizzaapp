package edu.hm.lip.pizza.internal.object.query;

/**
 * Konstanten für Queries der Order Entität.
 * 
 * @author Stefan Wörner
 */
public final class OrderQueryConstants
{

	private OrderQueryConstants()
	{

	}

	/**
	 * Name der "Alle Bestellungen"-Query.
	 */
	public static final String GET_ALL_ORDERS = "GetAllOrdersQuery";

	/**
	 * Alle Bestellungen.
	 */
	public static final String GET_ALL_ORDERS_QUERY = "SELECT o FROM EntityOrder o";

	/**
	 * Name der "Bestellungen gefiltert nach Fahrer und Status"-Query.
	 */
	public static final String ORDERS_BY_DRIVER_AND_STAGE = "OrdersByDriverAndStageQuery";

	/**
	 * Bestellungen gefiltert nach Fahrer und Status.
	 */
	public static final String ORDERS_BY_DRIVER_AND_STAGE_QUERY = "SELECT o FROM EntityOrder o WHERE o.driver.id = :driver_id AND NOT EXISTS "
			+ "(FROM EntityOrderStage AS s WHERE s.order = o AND s.stage = :stage)";

	/**
	 * Name der "Bestellungen gefiltert Status"-Query.
	 */
	public static final String ORDERS_BY_STAGE = "OrdersByStageQuery";

	/**
	 * Bestellungen gefiltert Status.
	 */
	public static final String ORDERS_BY_STAGE_QUERY = "SELECT o FROM EntityOrder o WHERE NOT EXISTS "
			+ "(FROM EntityOrderStage AS s WHERE s.order = o AND s.stage = :stage)";

}
