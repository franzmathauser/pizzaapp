package edu.hm.lip.pizza.driver;

/**
 * Applikationskonstanten.
 * 
 * @author Stefan Wörner
 */
public final class AppConstants
{

	private AppConstants()
	{

	}

	/**
	 * Minimale Distanz in Meter, bis eine Positionsänderung verarbeitet wird.
	 */
	public static final float LOCATION_NOTIFICATION_MIN_DISTANCE = 0; // TODO FINE TUNING

	/**
	 * Minimale Zeit in Millisekunden bis eine Positionsänderung verarbeitet wird.
	 */
	public static final long LOCATION_NOTIFICATION_MIN_TIME = 0;

	/**
	 * Toleranz für Positionszeitstempel (in Millisekunden).
	 */
	public static final long TIME_TOLERANCE = 5 * 60 * 1000;

	/**
	 * Toleranz für Positionsgenauigkeit (in Meter).
	 */
	public static final float ACCURACY_TOLERANCE = 50f;
}
