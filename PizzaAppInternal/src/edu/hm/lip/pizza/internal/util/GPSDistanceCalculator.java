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
	 * Entfernungsberechnung zweier Punkte auf einer Kugeloberfläche in meter.
	 * 
	 * @param lat1
	 *            Source Längengrad
	 * @param lon1
	 *            Source Breitengrad
	 * @param lat2
	 *            Destination Längengrad
	 * @param lon2
	 *            Destination Breitengrad
	 * @return Distance in metern
	 */
	public static Double calculate( Double lat1, Double lon1, Double lat2, Double lon2 )
	{
		double earthRadius = 3958.75;
		double dLat = Math.toRadians( lat2 - lat1 );
		double dLng = Math.toRadians( lon2 - lon1 );
		double a = Math.sin( dLat / 2 ) * Math.sin( dLat / 2 ) + Math.cos( Math.toRadians( lat1 ) )
				* Math.cos( Math.toRadians( lat2 ) ) * Math.sin( dLng / 2 ) * Math.sin( dLng / 2 );
		double c = 2 * Math.atan2( Math.sqrt( a ), Math.sqrt( 1 - a ) );
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return dist * meterConversion;
	}

}
