package edu.hm.lip.pizza.driver.listener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.ConfigStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.overlays.CarOverlay;

/**
 * Custom location listener to receive the location updates.
 * 
 * @author Stefan Wörner
 */
public class MapLocationListener implements LocationListener
{

	private MapView m_mapView;
	private Context m_context;

	/**
	 * Constructor.
	 * 
	 * @param contex
	 *            the context of this location listener
	 * @param mapView
	 *            the map view of the application
	 */
	public MapLocationListener( Context contex, MapView mapView )
	{
		m_mapView = mapView;
		m_context = contex;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged( Location location )
	{
		if (ConfigStore.getFollowEnabled())
		{
			// List<Overlay> mapOverlays
			Drawable icon = m_context.getResources().getDrawable( R.drawable.car );
			CarOverlay carOverlay = new CarOverlay( icon, m_context );
			
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint( lat, lng );
			
			OverlayItem overlayItem = new OverlayItem( point, "title", "snippet" );
			carOverlay.updateOverlay( overlayItem );
			
			// FIXME not always add new overlay!? -> only one car icon -> compare with google implementation mylocationoverlay
			m_mapView.getOverlays().add( carOverlay );
			m_mapView.getController().animateTo( point );
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled( String provider )
	{
		Toast.makeText( m_context, provider + " disabled", Toast.LENGTH_SHORT ).show();
		// TODO tell user that provider is required for application to work
		// TODO open dialog (ok, cancel)
		// TODO on cancel: close application (destroy)
		// TODO on ok: go to provider enable config page
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled( String provider )
	{
		Toast.makeText( m_context, provider + " enabled", Toast.LENGTH_SHORT ).show();
		// TODO reactivate all registrations?
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged( String provider, int status, Bundle extras )
	{
		// TODO failover to network provider if gps provider out of service
		// TODO go back to gps provider if available again
	}
}
