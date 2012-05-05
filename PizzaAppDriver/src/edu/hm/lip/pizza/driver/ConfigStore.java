package edu.hm.lip.pizza.driver;

/**
 * Represents the application wide configuration store. All configuration information can be accessed by and stored
 * within this class.
 * 
 * @author Stefan Wörner
 */
public final class ConfigStore
{

	private static ConfigStore m_instance;

	private Boolean m_trackEnabled;
	private Boolean m_followEnabled;
	private Boolean m_routeEnabled;
	private Boolean m_trafficEnabled;

	/**
	 * Private Constructor.
	 */
	private ConfigStore()
	{
		m_trackEnabled = null;
		m_followEnabled = null;
		m_routeEnabled = null;
		m_trafficEnabled = null;
	}

	/**
	 * Returns the current instance of the configruation store. If the instance not yet exists a new instance will be
	 * created.
	 * 
	 * @return
	 */
	private static ConfigStore getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new ConfigStore();
		}
		return m_instance;
	}

	/**
	 * Sets or restores the complete configuration store.
	 * 
	 * @param trackEnabled
	 *            indicates if the tracking function is enabled
	 * @param followEnabled
	 *            indicates if the following function is enabled
	 * @param routeEnabled
	 *            indicates if the route function is enabled
	 * @param trafficEnabled
	 *            indicates if the traffic function is enabled
	 */
	public static void restoreInstance( Boolean trackEnabled, Boolean followEnabled, Boolean routeEnabled, Boolean trafficEnabled )
	{
		ConfigStore config = getInstance();
		config.m_trackEnabled = trackEnabled;
		config.m_followEnabled = followEnabled;
		config.m_routeEnabled = routeEnabled;
		config.m_trafficEnabled = trafficEnabled;
	}

	/**
	 * Gets the track enabled flag.
	 * 
	 * @return trackEnabled
	 */
	public static Boolean getTrackEnabled()
	{
		return getInstance().m_trackEnabled;
	}

	/**
	 * Sets the track enabled flag.
	 * 
	 * @param trackEnabled
	 *            indicates if the tracking function is enabled
	 */
	public static void setTrackEnabled( Boolean trackEnabled )
	{
		getInstance().m_trackEnabled = trackEnabled;
	}

	/**
	 * Gets the follow enabled flag.
	 * 
	 * @return followEnabled
	 */
	public static Boolean getFollowEnabled()
	{
		return getInstance().m_followEnabled;
	}

	/**
	 * Sets the follow enabled flag.
	 * 
	 * @param followEnabled
	 *            indicates if the follow function is enabled
	 */
	public static void setFollowEnabled( Boolean followEnabled )
	{
		getInstance().m_followEnabled = followEnabled;
	}

	/**
	 * Gets the route enabled flag.
	 * 
	 * @return the routeEnabled
	 */
	public static Boolean getRouteEnabled()
	{
		return getInstance().m_routeEnabled;
	}

	/**
	 * Sets the route enabled flag.
	 * 
	 * @param routeEnabled
	 *            indicates if the route function is enabled
	 */
	public static void setRouteEnabled( Boolean routeEnabled )
	{
		getInstance().m_routeEnabled = routeEnabled;
	}

	/**
	 * Gets the traffic enabled flag.
	 * 
	 * @return the trafficEnabled
	 */
	public static Boolean getTrafficEnabled()
	{
		return getInstance().m_trafficEnabled;
	}

	/**
	 * Sets the traffic enabled flag.
	 * 
	 * @param trafficEnabled
	 *            indicates if the traffic function is enabled
	 */
	public static void setTrafficEnabled( Boolean trafficEnabled )
	{
		getInstance().m_trafficEnabled = trafficEnabled;
	}
}
