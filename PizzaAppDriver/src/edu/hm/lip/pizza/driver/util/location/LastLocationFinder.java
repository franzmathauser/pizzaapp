package edu.hm.lip.pizza.driver.util.location;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.AppConstants;

/**
 * Singletonimplementierung des LastLocationFinder. Sucht die letzte bekannte Position anhand des besten Providers.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public final class LastLocationFinder
{

	private static LastLocationFinder m_instance;

	private Context m_context;

	private LocationManager m_locationManager;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Applikationskontext
	 * @param mapView
	 *            Kartenansicht
	 */
	private LastLocationFinder( Context context )
	{
		m_context = context;
		m_locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
	}

	/**
	 * Gibt die aktuelle oder falls noch nicht vorhanden eine neue Instanz des LocationDrawer zurück.
	 * 
	 * @param context
	 *            Applikationskontext
	 * @return Instanz des LocationDrawer
	 */
	public static LastLocationFinder getInstance( Context context )
	{
		if (m_instance == null)
		{
			m_instance = new LastLocationFinder( context );
		}
		return m_instance;
	}

	/**
	 * Zerstört die Singletoninstanz.
	 */
	public static void destroyInstance()
	{
		m_instance.m_context = null;
		m_instance.m_locationManager = null;
		m_instance = null;
	}

	/**
	 * Gibt die letzte bekannte Position zurück oder NULL falls nichts gefunden wurde.
	 * 
	 * @return letzte bekannte Position
	 */
	public Location getLastLocation()
	{
		Location lastLocation = null;

		// Letzte bekannte Position vom GPS Provider anfordern
		Location lastGpsLocation = m_locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
		// Letzte bekannte Position vom NETWORK Provider anfordern
		Location lastNetworkLocation = m_locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );

		// Wenn beide Positionen verfügbar sind, prüfen welche zu verwenden ist
		if (lastGpsLocation != null && lastNetworkLocation != null)
		{
			Log.d( this.getClass().getSimpleName(),
					"gps location: " + lastGpsLocation.getLatitude() + "|" + lastGpsLocation.getLongitude() );
			Log.d( this.getClass().getSimpleName(), "gps location time: " + lastGpsLocation.getTime() );
			Log.d( this.getClass().getSimpleName(), "gps location accuracy: " + lastGpsLocation.getAccuracy() );

			Log.d( this.getClass().getSimpleName(), "network location: " + lastNetworkLocation.getLatitude() + "|"
					+ lastNetworkLocation.getLongitude() );
			Log.d( this.getClass().getSimpleName(), "network location time: " + lastNetworkLocation.getTime() );
			Log.d( this.getClass().getSimpleName(), "network location accuracy: " + lastNetworkLocation.getAccuracy() );

			Log.d( this.getClass().getSimpleName(), "location time difference (gps-network): "
					+ (lastGpsLocation.getTime() - lastNetworkLocation.getTime()) );

			// Wenn GPS Positionszeitstempel neuer ist als der NETWORK Positionszeitstempel abzüglich der Toleranz,
			// dann GPS Position verwenden
			if (lastGpsLocation.getTime() > lastNetworkLocation.getTime() - AppConstants.TIME_TOLERANCE_2_MINUTES)
			{
				// Toast.makeText( m_context, "used gps provider for last known location", Toast.LENGTH_SHORT ).show();
				Log.d( this.getClass().getSimpleName(), "used gps provider for last known location" );

				lastLocation = lastGpsLocation;
			}
			// Ansonsten NETWORK Postion verwenden
			else
			{
				// Toast.makeText( m_context, "used network provider for last known location", Toast.LENGTH_SHORT
				// ).show();
				Log.d( this.getClass().getSimpleName(), "used network provider for last known location" );

				lastLocation = lastNetworkLocation;
			}
		}
		// Sonst wenn GPS Positionen verfügbar ist -> verwenden
		else if (lastGpsLocation != null)
		{
			Log.d( this.getClass().getSimpleName(), "gps location time: " + lastGpsLocation.getTime() );
			Log.d( this.getClass().getSimpleName(), "gps location accuracy: " + lastGpsLocation.getAccuracy() );

			// Toast.makeText( m_context, "used gps provider for last known location", Toast.LENGTH_SHORT ).show();
			Log.d( this.getClass().getSimpleName(), "used gps provider for last known location" );

			lastLocation = lastGpsLocation;
		}
		// Sonst wenn NETWORK Positionen verfügbar ist -> verwenden
		else if (lastNetworkLocation != null)
		{
			Log.d( this.getClass().getSimpleName(), "network location time: " + lastNetworkLocation.getTime() );
			Log.d( this.getClass().getSimpleName(), "network location accuracy: " + lastNetworkLocation.getAccuracy() );

			// Toast.makeText( m_context, "used network provider for last known location", Toast.LENGTH_SHORT ).show();
			Log.d( this.getClass().getSimpleName(), "used network provider for last known location" );

			lastLocation = lastNetworkLocation;
		}
		// Sonst -> Aufgeben!
		else
		{
			Toast.makeText( m_context, "unable to get last known location", Toast.LENGTH_SHORT ).show();
			Log.d( this.getClass().getSimpleName(), "unable to get last known location" );

			lastLocation = null;
		}

		return lastLocation;
	}

	/**
	 * Gibt die letzte bekannte Position zurück oder NULL falls nichts gefunden wurde.
	 * 
	 * @return letzte bekannte Position als GeoPoint
	 */
	public GeoPoint getLastLocationAsGeoPoint()
	{
		Location lastLocation = getLastLocation();

		if (lastLocation != null)
		{
			// Auslesen der Koordinaten
			int lat = (int) (lastLocation.getLatitude() * 1E6);
			int lon = (int) (lastLocation.getLongitude() * 1E6);
			// GeoPoint mit Koordinaten erzeugen
			return new GeoPoint( lat, lon );
		}
		else
		{
			return null;
		}
	}
}
