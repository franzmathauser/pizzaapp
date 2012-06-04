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
	 * Toleranz für Positionszeitstempel (in Millisekunden).
	 */
	public static final long TIME_TOLERANCE = 5 * 60 * 1000;

	/**
	 * Toleranz für Positionsgenauigkeit (in Meter).
	 */
	public static final float ACCURACY_TOLERANCE = 50f;

	/**
	 * Animationsintervall für den SplashScreen (in Millisekunden).
	 */
	public static final int SPLASH_SCREEN_ANIMATION_INTERVAL = 2000;

	/**
	 * Sichtbarkeitsintervall für den SplashScreen (in Millisekunden).
	 */
	public static final int SPLASH_SCREEN_VISIBLE_INTERVAL = 5000;

}
