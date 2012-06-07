package edu.hm.lip.pizza.driver.listener;

import com.google.android.maps.MapView;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import edu.hm.lip.pizza.driver.AppConstants;
import edu.hm.lip.pizza.driver.services.DriverLocationService;
import edu.hm.lip.pizza.driver.util.location.LocationDrawer;

/**
 * LocationListener für die aktuelle Position des Fahrers auf der Karte. Es wird immer nur die aktuelle Position
 * angezeigt und keine Historie.
 * 
 * @author Stefan Wörner
 */
public class DriverLocationListener implements LocationListener
{

	private Context m_context;

	private MapView m_mapView;

	private LocationManager m_locationManager;

	// private static long m_lastLocationTime = Long.MAX_VALUE;
	//
	// private static float m_lastLocationAccuracy = Float.MIN_VALUE;
	//
	// private static boolean m_gpsProviderAvailable = true;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Applikationskontext
	 * @param mapView
	 *            Kartenansicht
	 */
	public DriverLocationListener( Context context, MapView mapView )
	{
		m_mapView = mapView;
		m_context = context;
		m_locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged( Location location )
	{
		Log.d( this.getClass().getSimpleName(), "location: " + location.getLatitude() + "|" + location.getLongitude() );
		Log.d( this.getClass().getSimpleName(), "provider: " + location.getProvider() );
		Log.d( this.getClass().getSimpleName(), "location time: " + location.getTime() );
		Log.d( this.getClass().getSimpleName(), "location accuracy: " + location.getAccuracy() );

		// Wenn aktuelle Position nicht vom GPS Provider kommt, dieser aber aktiv ist, muss der zeitliche
		// Tolleranzbereich geprüft werden
		if (m_locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )
				&& !LocationManager.GPS_PROVIDER.equals( location.getProvider() ))
		{
			// Letzte bekannte Position vom GPS Provider anfordern
			Location lastGpsLocation = m_locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );

			// Wenn GPS Positionszeitstempel neuer ist als der neue Positionszeitstempel abzüglich der Toleranz,
			// dann GPS Position verwenden

			// Wenn GPS Position verfügbar und diese einen neueren Zeitstempel besitzt als die aktuelle Position
			// abzüglich der Tolleranz, dann kann die aktuelle Position ignoriert werden.
			if (lastGpsLocation != null && lastGpsLocation.getTime() > location.getTime() - AppConstants.TIME_TOLERANCE_2_MINUTES)
			{
				Log.d( this.getClass().getSimpleName(), "location timestamp out of tolerance range" );
				return;
			}
		}

		// // Wenn GPS Provider aktiviert ist UND auch verfügbar ist, dann muss genauer nachgeprüft werden ob das
		// // Positionsupdate verwendet werden soll oder nicht
		// if (m_locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) && m_gpsProviderAvailable)
		// {
		// // Wenn gespeicherter Positionszeitstempel und gespeicherte Positionsgenauigkeit nicht mehr den Anfangswert
		// // besitzen muss eine genauere Prüfung durchgeführt werden
		// if (m_lastLocationTime != Long.MAX_VALUE && m_lastLocationAccuracy != Float.MIN_VALUE)
		// {
		// // Wenn der neue Positionszeitstempel älter als der letzte verarbeitete Zeitstempel ist, dann
		// // Positionsänderung ignorieren
		// if (location.getTime() < m_lastLocationTime)
		// {
		// Toast.makeText( m_context, "location time too old", Toast.LENGTH_SHORT ).show();
		// Log.d( this.getClass().getSimpleName(), "location time too old" );
		// return;
		// }
		//
		// // Wenn der neue Positionszeitstempel abzüglich des Tolleranzbereichs älter als der letzte verarbeitete
		// // Zeitstempel ist und die neue Positionsgenauigkeit ungenauer ist, dann Positionsänderung ignorieren
		// if (location.getTime() - AppConstants.TIME_TOLERANCE < m_lastLocationTime
		// && location.getAccuracy() > m_lastLocationAccuracy + AppConstants.ACCURACY_TOLERANCE)
		// {
		// Toast.makeText( m_context, "location accuracy too inaccurate", Toast.LENGTH_SHORT ).show();
		// Log.d( this.getClass().getSimpleName(), "location accuracy too inaccurate" );
		// return;
		// }
		// }
		// // Wenn gespeicherter Positionszeitstempel und gespeicherte Positionsgenauigkeit noch den Anfangswert
		// // besitzen muss eine genauere Prüfung durchgeführt werden
		// else if (m_lastLocationTime == Long.MAX_VALUE && m_lastLocationAccuracy == Float.MIN_VALUE)
		// {
		// // Letzte bekannte Position vom GPS Provider anfordern
		// Location lastGpsLocation = m_locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
		//
		// // Wenn GPS Positionszeitstempel neuer ist als der neue Positionszeitstempel abzüglich der Toleranz,
		// // dann GPS Position verwenden
		// if (lastGpsLocation != null && lastGpsLocation.getTime() > location.getTime() - AppConstants.TIME_TOLERANCE)
		// {
		// location = lastGpsLocation;
		// Log.d( this.getClass().getSimpleName(), "using newer gps location for initial positioning" );
		// Log.d( this.getClass().getSimpleName(), "gps location: " + lastGpsLocation.getLatitude() + "|"
		// + lastGpsLocation.getLongitude() );
		// Log.d( this.getClass().getSimpleName(), "gps location time: " + lastGpsLocation.getTime() );
		// Log.d( this.getClass().getSimpleName(), "gps location accuracy: " + lastGpsLocation.getAccuracy() );
		// }
		// }
		// }
		//
		// m_lastLocationTime = location.getTime();
		// m_lastLocationAccuracy = location.getAccuracy();

		// Neue Location zeichnen lassen
		LocationDrawer.getInstance( m_context, m_mapView ).updateCurrentLocation( location );

		Intent intent = new Intent( m_context, DriverLocationService.class );
		intent.putExtra( DriverLocationService.EXTRA_LATITUDE, location.getLatitude() );
		intent.putExtra( DriverLocationService.EXTRA_LONGITUDE, location.getLongitude() );
		m_context.startService( intent );

		// TODO Server Request asynchron -> Service
		// try
		// {
		// GPSData gpsData = new GPSData();
		// gpsData.setLat( location.getLatitude() );
		// gpsData.setLon( location.getLongitude() );
		//
		// HttpEntity entity = new StringEntity( JsonMapper.toJSON( gpsData ) );
		//
		// String driverId = PreferencesStore.getDriverIdPreference();
		//
		// if (StringUtils.isBlank( driverId ))
		// {
		// // TODO Throw Exception
		// }
		//
		// StringBuilder path = new StringBuilder();
		// path.append( "drivers/" ).append( driverId ).append( "/gpsdata" );
		//
		// HttpConnector.doPostRequest( path.toString(), MediaType.APPLICATION_JSON, entity, MediaType.APPLICATION_JSON
		// );
		// }
		// catch (HostnameNotSetException e)
		// {
		// // TODO Notification!
		// // TODO Tracking abschalten?
		// return;
		// }
		// catch (HttpStatusCodeException e)
		// {
		// // TODO Notification!
		// // TODO Tracking abschalten?
		// Log.e( this.getClass().getSimpleName(), e.getMessage() );
		// }
		// catch (IOException e)
		// {
		// // TODO Benutzerbenachrichtigung
		// Log.e( this.getClass().getSimpleName(), e.getMessage() );
		//
		// for (StackTraceElement element : e.getStackTrace())
		// {
		// Log.e( this.getClass().getSimpleName(), element.toString() );
		// }
		// }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled( String provider )
	{
		Log.d( this.getClass().getSimpleName(), provider + " provider disabled" );

		// if (LocationManager.GPS_PROVIDER.equals( provider ))
		// {
		// m_gpsProviderAvailable = false;
		// }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled( String provider )
	{
		Log.d( this.getClass().getSimpleName(), provider + " provider enabled" );

		// if (LocationManager.GPS_PROVIDER.equals( provider ))
		// {
		// m_gpsProviderAvailable = true;
		// }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged( String provider, int status, Bundle extras )
	{
		// // TODO failover to network provider if gps provider out of service
		// // TODO go back to gps provider if available again
		// switch (status)
		// {
		// case LocationProvider.OUT_OF_SERVICE:
		// Log.d( this.getClass().getSimpleName(), provider + " provider is out of service" );
		// if (LocationManager.GPS_PROVIDER.equals( provider ))
		// {
		// m_gpsProviderAvailable = false;
		// }
		// break;
		//
		// case LocationProvider.TEMPORARILY_UNAVAILABLE:
		// Log.d( this.getClass().getSimpleName(), provider + " provider is temporarily unavailable" );
		// if (LocationManager.GPS_PROVIDER.equals( provider ))
		// {
		// m_gpsProviderAvailable = false;
		// }
		// break;
		//
		// case LocationProvider.AVAILABLE:
		// Log.d( this.getClass().getSimpleName(), provider + " provider is available" );
		// if (LocationManager.GPS_PROVIDER.equals( provider ))
		// {
		// m_gpsProviderAvailable = true;
		// }
		// break;
		//
		// default:
		// break;
		// }
	}

}
