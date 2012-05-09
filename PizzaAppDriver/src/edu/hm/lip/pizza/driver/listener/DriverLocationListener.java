package edu.hm.lip.pizza.driver.listener;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.ConfigStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.overlays.DriverOverlay;

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

	private static GeoPoint currentLocation = null;

	private static DriverOverlay currentLocationOverlay = null;

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
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged( Location location )
	{
		// Auslesen der aktuellen Koordinaten
		int lat = (int) (location.getLatitude() * 1E6);
		int lon = (int) (location.getLongitude() * 1E6);
		// GeoPoint mit aktuellen Koordinaten erzeugen
		currentLocation = new GeoPoint( lat, lon );

		if (m_mapView != null)
		{
			List<Overlay> mapOverlays = m_mapView.getOverlays();

			// Prüfen ob das Overlay für die aktuelle Position ungleich null ist und bereits auf der Karte vorhanden ist
			if (currentLocationOverlay != null && mapOverlays.contains( currentLocationOverlay ))
			{
				// ... wenn vorhanden, dann von der Karte entfernen, da immer nur die aktuelle Position angezeigt werden
				// soll und keine Historie
				mapOverlays.remove( currentLocationOverlay );
			}

			// Markersymbol erzeugen welches auf der Karte für die aktuelle Position gezeichnet werden soll
			Drawable marker = m_context.getResources().getDrawable( R.drawable.car );
			// OverlayItem erzeugen für die aktuelle Position und angebe von Metadaten (Titel und Inhalt)
			OverlayItem overlayItem = new OverlayItem( currentLocation, "title", "snippet" ); // TODO Metadaten!
			// LocationOverlay erzeugen mit ausgewähltem Markerysmbol und erzeugtem OverlayItem
			currentLocationOverlay = new DriverOverlay( marker, m_context );
			currentLocationOverlay.setOverlay( overlayItem );

			// Overlay für die aktuelle Position auf die Karte bringen
			mapOverlays.add( currentLocationOverlay );
			currentLocationOverlay.populateOverlay();

			// Wenn Follow-Feature eingeschaltet ist ...
			if (ConfigStore.getFollowEnabled())
			{
				// ... dann karte auf aktuelle Position zentrieren
				m_mapView.getController().animateTo( currentLocation );
			}
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
		switch (status)
		{
			case LocationProvider.OUT_OF_SERVICE:
				Toast.makeText( m_context, provider + " provider is out of service.", Toast.LENGTH_SHORT ).show();
				break;

			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Toast.makeText( m_context, provider + " provider is temporarily unavailable.", Toast.LENGTH_SHORT ).show();
				break;

			case LocationProvider.AVAILABLE:
				Toast.makeText( m_context, provider + " provider is available.", Toast.LENGTH_SHORT ).show();
				break;

			default:
				break;
		}
	}
}
