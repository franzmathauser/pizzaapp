package edu.hm.lip.pizza.driver;

/**
 * Holds all application wide preferences constants.
 * 
 * @author Stefan Wörner
 */
public final class PreferencesConstants
{

	/**
	 * Private Contructor.
	 */
	private PreferencesConstants()
	{

	}

	/**
	 * Filename of the application prefernces file.
	 */
	public static final String FILENAME = "PizzaAppDriver_PrefFile";

	/**
	 * Propertyname of the track configuration item.
	 */
	public static final String TRACK_CONFIG = "TrackEnabled";

	/**
	 * Propertyname of the follow configuration item.
	 */
	public static final String FOLLOW_CONFIG = "FollowEnabled";

	/**
	 * Propertyname of the route configuration item.
	 */
	public static final String ROUTE_CONFIG = "RouteEnabled";

	/**
	 * Propertyname of the traffic configuration item.
	 */
	public static final String TRAFFIC_CONFIG = "TrafficEnabled";

}
