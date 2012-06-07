package edu.hm.lip.pizza.driver.util.location;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.MapView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.listener.DriverLocationListener;

/**
 * Singletonimplementierung des ProviderManagers. Verwaltet die bzw. den aktuell verwendeten Provider und bietet
 * Funktionen zum registrieren und deregistrieren an.
 * 
 * @author Stefan Wörner
 */
public final class LocationProviderManager
{

	private static LocationProviderManager m_instance;

	private Context m_context;

	private MapView m_mapView;

	private LocationManager m_locationManager;

	private DriverLocationListener m_gpsLocationListener;

	private DriverLocationListener m_networkLocationListener;

	private static List<LocationListener> m_locationListeners;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Applikationskontext
	 * @param mapView
	 *            Kartenansicht
	 */
	private LocationProviderManager( Context context, MapView mapView )
	{
		m_context = context;
		m_mapView = mapView;
		m_locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
		m_gpsLocationListener = new DriverLocationListener( m_context, m_mapView );
		m_networkLocationListener = new DriverLocationListener( m_context, m_mapView );
		m_locationListeners = new ArrayList<LocationListener>();
	}

	/**
	 * Gibt die aktuelle oder falls noch nicht vorhanden eine neue Instanz des LocationDrawer zurück.
	 * 
	 * @param context
	 *            Applikationskontext
	 * @param mapView
	 *            Kartenansicht
	 * @return Instanz des LocationDrawer
	 */
	public static LocationProviderManager getInstance( Context context, MapView mapView )
	{
		if (m_instance == null)
		{
			m_instance = new LocationProviderManager( context, mapView );
		}
		return m_instance;
	}

	/**
	 * Zerstört die Singletoninstanz.
	 */
	public static void destroyInstance()
	{
		m_instance.m_context = null;
		m_instance.m_gpsLocationListener = null;
		m_instance.m_networkLocationListener = null;
		m_instance.m_locationManager = null;
		m_instance.m_mapView = null;
		m_locationListeners = null;
		m_instance = null;
	}

	/**
	 * Registriert alle benötigten Location Listener, bzw. zeigt einen Fehlerdialog an, falls die Provider deaktiviert
	 * sind.
	 */
	public void registerLocationListener()
	{
		// Falls die TrackMe Funktion aktiviert ist, müssen alle benötigten LocationListener registriert werden
		if (PreferencesStore.getTrackMePreference() && isProviderAvailable())
		{
			if (!m_locationListeners.contains( m_gpsLocationListener ))
			{
				// LocationListener für Updates über den GPS Provider registrieren
				m_locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
						PreferencesStore.getTrackingTimeIntervalPreference(),
						PreferencesStore.getTrackingDistanceIntervalPreference(), m_gpsLocationListener );
				Log.d( LocationProviderManager.class.getSimpleName(), "using " + LocationManager.GPS_PROVIDER
						+ " provider for tracking" );

				// GPS LocationListener in Liste der LocationListener aufnehmen
				m_locationListeners.add( m_gpsLocationListener );
				Log.d( LocationProviderManager.class.getSimpleName(), "registered gps location listener" );
			}

			if (!m_locationListeners.contains( m_networkLocationListener ))
			{
				// LocationListener für Updates über den NETWORK Provider registrieren
				m_locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,
						PreferencesStore.getTrackingTimeIntervalPreference(),
						PreferencesStore.getTrackingDistanceIntervalPreference(), m_networkLocationListener );
				Log.d( LocationProviderManager.class.getSimpleName(), "using " + LocationManager.NETWORK_PROVIDER
						+ " provider for tracking" );

				// NETWORK LocationListener in Liste der LocationListener aufnehmen
				m_locationListeners.add( m_networkLocationListener );
				Log.d( LocationProviderManager.class.getSimpleName(), "registered network location listener" );
			}
		}
	}

	/**
	 * Deregistriert alle Location Listener.
	 */
	public void unregisterLocationListener()
	{
		// Es müssen alle vorhandenen LocationListener deregistriert werden
		for (LocationListener locationListener : m_locationListeners)
		{
			m_locationManager.removeUpdates( locationListener );
			Log.d( LocationProviderManager.class.getSimpleName(), "unregistered location listener" );
		}
		m_locationListeners.clear();
	}

	private boolean isProviderAvailable()
	{
		if (!m_locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )
				&& !m_locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER ))
		{
			// Wenn beide Provider nicht aktiviert sind muss der Benutzer aufgefordert werden diese zu aktivieren
			AlertDialog.Builder builder = new AlertDialog.Builder( m_context );
			builder.setMessage( R.string.provider_disabled_message ).setCancelable( false );
			builder.setPositiveButton( R.string.provider_disabled_bt_positive, new DialogInterface.OnClickListener()
			{

				public void onClick( DialogInterface dialog, int id )
				{
					// Wenn OK Button geklickt wird, dann wird die Konfigurationsseite für die Location Settings
					// aufgerufen
					dialog.cancel();
					Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
					m_context.startActivity( intent );
				}
			} );
			builder.setNegativeButton( R.string.provider_disabled_bt_negative, new DialogInterface.OnClickListener()
			{

				public void onClick( DialogInterface dialog, int id )
				{
					// Wenn Exit Button geklickt wird, wird die Applikation beendet und die TrackMe Preference auf false
					// gesetzt
					if (m_context instanceof Activity)
					{
						PreferencesStore.setTrackMePreference( Boolean.FALSE );
						((Activity) m_context).finish();
					}
				}
			} );

			// Hinweisdialog anzeigen
			builder.create().show();
			return false;
		}

		// Es ist mindestens ein Provider verfügbar
		return true;
	}

}
