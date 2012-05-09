package edu.hm.lip.pizza.driver;

/**
 * Repräsentiert den applikationsweiten Configuration Store. Es sind alle Konfigurationsinformationen über diese
 * Utility-Klasse verfügbar. Der Configuration Store ist als Singleton implementiert und muss daher nicht instanziiert
 * werden.
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
	 * Privater Konstruktor.
	 */
	private ConfigStore()
	{
		m_trackEnabled = null;
		m_followEnabled = null;
		m_routeEnabled = null;
		m_trafficEnabled = null;
	}

	/**
	 * Git die aktuelle Instanz des Configuration Stores zurück. Falls noch keine Instanz existiert wird eine neue
	 * erzeugt.
	 * 
	 * @return Configuration Store Instanz
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
	 * Stetzt die komplette Konfiguration neu.
	 * 
	 * @param trackEnabled
	 *            Zeigt an ob die Tracking Funktion aktiv ist
	 * @param followEnabled
	 *            Zeigt an ob die Following Funktion aktiv ist
	 * @param routeEnabled
	 *            Zeigt an ob die Route Funktion aktiv ist
	 * @param trafficEnabled
	 *            Zeigt an ob die Traffic Funktion aktiv ist
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
	 * Liefert das Attribut trackEnabled.
	 * 
	 * @return trackEnabled
	 */
	public static Boolean getTrackEnabled()
	{
		return getInstance().m_trackEnabled;
	}

	/**
	 * Setzt das Attribut trackEnabled.
	 * 
	 * @param trackEnabled
	 *            zu setzender Wert für das Attribut trackEnabled
	 */
	public static void setTrackEnabled( Boolean trackEnabled )
	{
		getInstance().m_trackEnabled = trackEnabled;
	}

	/**
	 * Liefert das Attribut followEnabled.
	 * 
	 * @return followEnabled
	 */
	public static Boolean getFollowEnabled()
	{
		return getInstance().m_followEnabled;
	}

	/**
	 * Setzt das Attribut followEnabled.
	 * 
	 * @param followEnabled
	 *            zu setzender Wert für das Attribut followEnabled
	 */
	public static void setFollowEnabled( Boolean followEnabled )
	{
		getInstance().m_followEnabled = followEnabled;
	}

	/**
	 * Liefert das Attribut routeEnabled.
	 * 
	 * @return routeEnabled
	 */
	public static Boolean getRouteEnabled()
	{
		return getInstance().m_routeEnabled;
	}

	/**
	 * Setzt das Attribut routeEnabled.
	 * 
	 * @param routeEnabled
	 *            zu setzender Wert für das Attribut routeEnabled
	 */
	public static void setRouteEnabled( Boolean routeEnabled )
	{
		getInstance().m_routeEnabled = routeEnabled;
	}

	/**
	 * Liefert das Attribut trafficEnabled.
	 * 
	 * @return trafficEnabled
	 */
	public static Boolean getTrafficEnabled()
	{
		return getInstance().m_trafficEnabled;
	}

	/**
	 * Setzt das Attribut trafficEnabled.
	 * 
	 * @param trafficEnabled
	 *            zu setzender Wert für das Attribut trafficEnabled
	 */
	public static void setTrafficEnabled( Boolean trafficEnabled )
	{
		getInstance().m_trafficEnabled = trafficEnabled;
	}

}
