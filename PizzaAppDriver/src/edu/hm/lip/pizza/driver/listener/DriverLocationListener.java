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
import edu.hm.lip.pizza.driver.service.DriverLocationService;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
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

			// Wenn GPS Position verfügbar und diese einen neueren Zeitstempel besitzt als die aktuelle Position
			// abzüglich der Tolleranz, dann kann die aktuelle Position ignoriert werden.
			if (lastGpsLocation != null && lastGpsLocation.getTime() > location.getTime() - AppConstants.TIME_TOLERANCE_2_MINUTES)
			{
				Log.d( this.getClass().getSimpleName(), "location timestamp out of tolerance range" );
				return;
			}
		}

		// Neue Location zeichnen lassen
		LocationDrawer.getInstance( m_context, m_mapView ).updateCurrentLocation( location );

		// Position an Server senden lassen
		Intent intent = new Intent( m_context, DriverLocationService.class );
		intent.putExtra( ExtraConstants.LATITUDE_EXTRA, location.getLatitude() );
		intent.putExtra( ExtraConstants.LONGITUDE_EXTRA, location.getLongitude() );
		m_context.startService( intent );
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
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged( String provider, int status, Bundle extras )
	{
		return;
	}

}
