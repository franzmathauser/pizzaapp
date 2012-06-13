package edu.hm.lip.pizza.internal.object.query;

/**
 * Konstanten für Queries der Driver Entität.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class DriverQueryConstants
{

	private DriverQueryConstants()
	{

	}

	/**
	 * Name der "Alle Fahrer"-Query.
	 */
	public static final String GET_ALL_DRIVERS = "GetAllDriversQuery";

	/**
	 * Alle Fahrer.
	 */
	public static final String GET_ALL_DRIVERS_QUERY = "SELECT d FROM EntityDriver d";

}
