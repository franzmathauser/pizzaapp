package edu.hm.lip.pizza.driver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import edu.hm.lip.pizza.driver.objects.resources.Driver;

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
	public static String getSelectedDriverIdPreference()
	{
		return getPreferences().getString( DriverApp.getAppContext().getString( R.string.pref_category_driver_id_key ), "" );
	}

	/**
	 * Setzt die DriverId Preference.
	 * 
	 * @param driverIdPreference
	 *            Zu setzender Wert für die DriverId Preference
	 */
	public static void setSelectedDriverIdPreference( String driverIdPreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_driver_id_key ), driverIdPreference );
	}

	/**
	 * Liefert die DriverList Preference.
	 * 
	 * @return driverList Preference
	 */
	public static List<Driver> getDriverListPreference()
	{
		// Return Liste initialisieren
		List<Driver> driversListPreference = new ArrayList<Driver>();

		// Preferences auslesen
		String storedDriversPreference = getPreferences().getString(
				DriverApp.getAppContext().getString( R.string.pref_category_driver_list_key ), "" );

		// Wenn Preferences vorhanden sind, dann parsen
		if (StringUtils.isNotBlank( storedDriversPreference ))
		{
			for (String storedDriver : StringUtils.split( storedDriversPreference, PreferencesConstants.DRIVER_SEPERATOR ))
			{
				String[] storedDriverAttributes = StringUtils.split( storedDriver,
						PreferencesConstants.DRIVER_ATTRIBUTE_SEPERATOR );

				Driver driver = new Driver();
				driver.setId( Integer.parseInt( storedDriverAttributes[0] ) );
				driver.setName( storedDriverAttributes[1] );

				driversListPreference.add( driver );
			}
		}

		return driversListPreference;
	}

	/**
	 * Setzt die DriverList Preference.
	 * 
	 * @param driverListPreference
	 *            Zu setzender Wert für die DriverList Preference
	 */
	public static void setDriverListPreference( List<Driver> driverListPreference )
	{
		// Wenn Liste null ist, dann ignorieren und Verarbeitung abbrechen
		if (driverListPreference == null)
		{
			return;
		}

		// Driver für Speicherung "serialisieren"
		StringBuilder sb = new StringBuilder();
		for (Driver driver : driverListPreference)
		{
			if (StringUtils.isNotBlank( driver.getId().toString() ) && StringUtils.isNotBlank( driver.getName() ))
			{
				sb.append( driver.getId() ).append( PreferencesConstants.DRIVER_ATTRIBUTE_SEPERATOR ).append( driver.getName() );
				sb.append( PreferencesConstants.DRIVER_SEPERATOR );
			}
		}
		sb.deleteCharAt( sb.length() - 1 );

		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_driver_list_key ), sb.toString() );
	}

	/**
	 * Liefert die TrackingDistanceInterval Preference.
	 * 
	 * @return TrackingDistanceInterval Preference
	 */
	public static Integer getTrackingDistanceIntervalPreference()
	{
		return getPreferences().getInt(
				DriverApp.getAppContext().getString( R.string.pref_category_tracking_distance_interval_key ), 25 );
	}

	/**
	 * Setzt die TrackingDistanceInterval Preference.
	 * 
	 * @param trackingDistanceIntervalPreference
	 *            Zu setzender Wert für die TrackingDistanceInterval Preference
	 */
	public static void setTrackingDistanceIntervalPreference( Integer trackingDistanceIntervalPreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_tracking_distance_interval_key ),
				trackingDistanceIntervalPreference );
	}

	/**
	 * Liefert die TrackingTimeInterval Preference.
	 * 
	 * @return TrackingTimeInterval Preference
	 */
	public static Integer getTrackingTimeIntervalPreference()
	{
		return getPreferences().getInt( DriverApp.getAppContext().getString( R.string.pref_category_driver_id_key ), 0 );
	}

	/**
	 * Setzt die TrackingTimeInterval Preference.
	 * 
	 * @param trackingTimeIntervalPreference
	 *            Zu setzender Wert für die TrackingTimeInterval Preference
	 */
	public static void setTrackingTimeIntervalPreference( Integer trackingTimeIntervalPreference )
	{
		setPrefernces( DriverApp.getAppContext().getString( R.string.pref_category_driver_id_key ),
				trackingTimeIntervalPreference );
	}

}
