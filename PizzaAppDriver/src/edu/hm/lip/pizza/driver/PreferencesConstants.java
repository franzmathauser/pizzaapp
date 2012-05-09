package edu.hm.lip.pizza.driver;

/**
 * KLasse enthält alle applikationsweit verwendeten Konstanten.
 * 
 * @author Stefan Wörner
 */
public final class PreferencesConstants
{

	/**
	 * Privater Kontructor.
	 */
	private PreferencesConstants()
	{

	}

	/**
	 * Dateiname des Preferences File der Applikation.
	 */
	public static final String FILENAME = "PizzaAppDriver_PrefFile";

	/**
	 * Propertyname des Track Configuration Item.
	 */
	public static final String TRACK_CONFIG = "TrackEnabled";

	/**
	 * Propertyname des Follow Configuration Item.
	 */
	public static final String FOLLOW_CONFIG = "FollowEnabled";

	/**
	 * Propertyname des Route Configuration Item.
	 */
	public static final String ROUTE_CONFIG = "RouteEnabled";

	/**
	 * Propertyname des Traffic Configuration Item.
	 */
	public static final String TRAFFIC_CONFIG = "TrafficEnabled";

}
