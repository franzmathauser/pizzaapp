package edu.hm.lip.pizza.internal.util;

/**
 * Klasse berechnet die Distanz zweier Punkte auf einer Kugeloberfläche.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class GPSDistanceCalculator
{

	private GPSDistanceCalculator()
	{
	}

	/**
	 * Erdradius in Kilometern.
	 */
	private static Double EARTH_RADIUS = 6378.388;

	/**
	 * Entfernungsberechnung zweier Punkte auf einer Kugeloberfläche.
	 * 
	 * @param lat1
	 *            Source Längengrad
	 * @param lon1
	 *            Source Breitengrad
	 * @param lat2
	 *            Destination Längengrad
	 * @param lon2
	 *            Destination Breitengrad
	 * @return Distance in Kilometern
	 */
	public static Double calculate( Double lat1, Double lon1, Double lat2, Double lon2 )
	{
		return EARTH_RADIUS
				* Math.acos( Math.sin( lat1 ) * Math.sin( lat2 ) + Math.cos( lat1 ) * Math.cos( lat2 ) * Math.cos( lon2 - lon1 ) );
	}
}
