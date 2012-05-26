package edu.hm.lip.pizza.internal.object.query;

/**
 * Konstanten für Queries der GPSData Entität.
 * 
 * @author Stefan Wörner
 */
public final class GPSDataQueryConstants
{

	private GPSDataQueryConstants()
	{

	}

	/**
	 * Name der "Alle GPS-Daten"-Query.
	 */
	public static final String GET_ALL_GPSDATA = "GetAllGPSDataQuery";

	/**
	 * Alle GPS-Daten.
	 */
	public static final String GET_ALL_GPSDATA_QUERY = "SELECT g FROM EntityGPSData g";

}
