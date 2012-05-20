package edu.hm.lip.pizza.driver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Klasse veraltet alle Preferences der PizzaApp. Hierzu zählen u.a. die MapBehavior Einstellungen, sowie die
 * Einstellungen im Hauptmenü.
 * 
 * @author Stefan Wörner
 */
public final class PreferencesStore
{

	/**
	 * Privater Konstruktor.
	 */
	private PreferencesStore()
	{

	}

	/**
	 * Methode zum generischen Auslesen der Preferences.
	 * 
	 * @return SharedPreferences
	 */
	private static SharedPreferences getPreferences()
	{
		return DriverApp.getAppContext().getSharedPreferences( PreferencesConstants.FILENAME, Activity.MODE_PRIVATE );
	}

	/**
	 * Methode zum generischen Speichern der Preferences.
	 * 
	 * @param key
	 *            Preference-Schlüssel
	 * @param value
	 *            Preference-Wert
	 */
	private static void setPrefernces( String key, Object value )
	{
		Editor preferencesEditor = getPreferences().edit();

		if (value instanceof Boolean)
		{
			preferencesEditor.putBoolean( key, (Boolean) value );
		}
		else if (value instanceof String)
		{
			preferencesEditor.putString( key, (String) value );
		}
		else if (value instanceof Integer)
		{
			preferencesEditor.putInt( key, (Integer) value );
		}
		else if (value instanceof Float)
		{
			preferencesEditor.putLong( key, (Long) value );
		}
		else if (value instanceof Float)
		{
			preferencesEditor.putFloat( key, (Float) value );
		}

		preferencesEditor.commit();
	}

	/**
	 * Liefert die TrackMe Preference.
	 * 
	 * @return trackMe Preference
	 */
	public static Boolean getTrackMePreference()
	{
		return getPreferences().getBoolean( DriverApp.getAppContext().getString( R.string.mapbehavior_content_trackme_key ),
				false );
	}

	/**
	 * Setzt die TrackMe Preference.
	 * 
	 * @param trackMePreference
	 *            Zu setzender Wert für die TrackMe Preference
	 */
	public static void setTrackMePreference( Boolean trackMePreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.mapbehavior_content_trackme_key ), trackMePreference );
	}

	/**
	 * Liefert die FollowMe Preference.
	 * 
	 * @return followMe Preference
	 */
	public static Boolean getFollowMePreference()
	{
		return getPreferences().getBoolean( DriverApp.getAppContext().getString( R.string.mapbehavior_content_followme_key ),
				false );
	}

	/**
	 * Setzt die FollowMe Preference.
	 * 
	 * @param followMePreference
	 *            Zu setzender Wert für die FollowMe Preference
	 */
	public static void setFollowMePreference( Boolean followMePreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.mapbehavior_content_followme_key ), followMePreference );
	}

	/**
	 * Liefert die ShowRoute Preference.
	 * 
	 * @return showRoute Preference
	 */
	public static Boolean getShowRoutePreference()
	{
		return getPreferences().getBoolean( DriverApp.getAppContext().getString( R.string.mapbehavior_content_showroute_key ),
				false );
	}

	/**
	 * Setzt die ShowRoute Preference.
	 * 
	 * @param showRoutePreference
	 *            Zu setzender Wert für die ShowRoute Preference
	 */
	public static void setShowRoutePreference( Boolean showRoutePreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.mapbehavior_content_showroute_key ), showRoutePreference );
	}

	/**
	 * Liefert die ShowTraffic Preference.
	 * 
	 * @return showTraffic Preference
	 */
	public static Boolean getShowTrafficPreference()
	{
		return getPreferences().getBoolean( DriverApp.getAppContext().getString( R.string.mapbehavior_content_showtraffic_key ),
				false );
	}

	/**
	 * Setzt die ShowTraffic Preference.
	 * 
	 * @param showTrafficPreference
	 *            Zu setzender Wert für die ShowTraffic Preference
	 */
	public static void setShowTrafficPreference( Boolean showTrafficPreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.mapbehavior_content_showtraffic_key ), showTrafficPreference );
	}

	/**
	 * Liefert die ServerHostname Preference.
	 * 
	 * @return serverHostname Preference
	 */
	public static String getServerHostnamePreference()
	{
		return getPreferences().getString( DriverApp.getAppContext().getString( R.string.pref_category_server_hostname_key ), "" );
	}

	/**
	 * Setzt die ServerHostname Preference.
	 * 
	 * @param serverHostnamePreference
	 *            Zu setzender Wert für die ServerHostname Preference
	 */
	public static void setServerHostnamePreference( String serverHostnamePreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_server_hostname_key ),
				serverHostnamePreference );
	}

	/**
	 * Liefert die ServerPort Preference.
	 * 
	 * @return serverPort Preference
	 */
	public static String getServerPortPreference()
	{
		return getPreferences().getString( DriverApp.getAppContext().getString( R.string.pref_category_server_port_key ), "" );
	}

	/**
	 * Setzt die ServerPort Preference.
	 * 
	 * @param serverPortPreference
	 *            Zu setzender Wert für die ServerPort Preference
	 */
	public static void setServerPortPreference( String serverPortPreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_server_port_key ), serverPortPreference );
	}

	/**
	 * Liefert die DriverId Preference.
	 * 
	 * @return driverId Preference
	 */
	public static String getDriverIdPreference()
	{
		return getPreferences().getString( DriverApp.getAppContext().getString( R.string.pref_category_driver_id_key ), "" );
	}

	/**
	 * Setzt die DriverId Preference.
	 * 
	 * @param driverIdPreference
	 *            Zu setzender Wert für die DriverId Preference
	 */
	public static void setDriverIdPreference( String driverIdPreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_driver_id_key ), driverIdPreference );
	}

}
