package edu.hm.lip.pizza.driver.util.location;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.overlays.DriverOverlay;

/**
 * Singletonimplementierung des LocationDrawers. Zeichnet die aktuelle Position auf der Karte neu.
 * 
 * @author Stefan Wörner
 */
public final class LocationDrawer
{

	private static LocationDrawer m_instance;

	private Context m_context;

	private MapView m_mapView;

	private static GeoPoint m_currentLocation;

	private static DriverOverlay m_currentLocationOverlay;

	/**
	 * Konstruktor.
	 * 
	 * @param context
	 *            Applikationskontext
	 * @param mapView
	 *            Kartenansicht
	 */
	private LocationDrawer( Context context, MapView mapView )
	{
		m_context = context;
		m_mapView = mapView;
		m_currentLocation = null;
		m_currentLocationOverlay = null;
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
	public static LocationDrawer getInstance( Context context, MapView mapView )
	{
		if (m_instance == null)
		{
			m_instance = new LocationDrawer( context, mapView );
		}
		return m_instance;
	}

	/**
	 * Zerstört die Singletoninstanz.
	 */
	public static void destroyInstance()
	{
		m_instance.m_context = null;
		m_instance.m_mapView = null;
		m_currentLocation = null;
		m_currentLocationOverlay = null;
		m_instance = null;
	}

	/**
	 * Liefert das Attribut currentLocation.
	 * 
	 * @return currentLocation
	 */
	public static GeoPoint getCurrentLocation()
	{
		return m_currentLocation;
	}

	/**
	 * Zeichnet die aktuelle Position auf der Karte anhand der übergebenen Position neu.
	 * 
	 * @param location
	 *            Neue Position
	 */
	public void updateCurrentLocation( Location location )
	{
		updateCurrentLocation( location, false );
	}

	/**
	 * Zeichnet die aktuelle Position auf der Karte anhand der übergebenen Position neu.
	 * 
	 * @param location
	 *            Neue Position
	 * @param center
	 *            Zentriere auf die neue Position
	 */
	public void updateCurrentLocation( Location location, boolean center )
	{
		if (location == null)
		{
			return;
		}

		// Auslesen der aktuellen Koordinaten
		int lat = (int) (location.getLatitude() * 1E6);
		int lon = (int) (location.getLongitude() * 1E6);
		// GeoPoint mit aktuellen Koordinaten erzeugen
		m_currentLocation = new GeoPoint( lat, lon );

		Log.d( this.getClass().getSimpleName(), "location: " + location.getLatitude() + "|" + location.getLongitude() );
		Log.d( this.getClass().getSimpleName(), "location time: " + location.getTime() );
		Log.d( this.getClass().getSimpleName(), "location accuracy: " + location.getAccuracy() );

		if (m_mapView != null)
		{
			List<Overlay> mapOverlays = m_mapView.getOverlays();

			// Prüfen ob das Overlay für die aktuelle Position ungleich null ist und bereits auf der Karte vorhanden ist
			if (m_currentLocationOverlay != null && mapOverlays.contains( m_currentLocationOverlay ))
			{
				// ... wenn vorhanden, dann von der Karte entfernen, da immer nur die aktuelle Position angezeigt werden
				// soll und keine Historie
				mapOverlays.remove( m_currentLocationOverlay );
			}

			// Markersymbol erzeugen welches auf der Karte für die aktuelle Position gezeichnet werden soll
			Drawable marker = m_context.getResources().getDrawable( R.drawable.car );
			// OverlayItem erzeugen für die aktuelle Position und angebe von Metadaten (Titel und Inhalt)
			OverlayItem overlayItem = new OverlayItem( m_currentLocation, "title", "snippet" ); // TODO Metadaten!
			// LocationOverlay erzeugen mit ausgewähltem Markerysmbol und erzeugtem OverlayItem
			m_currentLocationOverlay = new DriverOverlay( marker, m_context );
			m_currentLocationOverlay.setOverlay( overlayItem );

			// Overlay für die aktuelle Position auf die Karte bringen
			mapOverlays.add( m_currentLocationOverlay );
			m_currentLocationOverlay.populateOverlay();
			Log.d( this.getClass().getSimpleName(),
					"Drawing new location: " + location.getLatitude() + "|" + location.getLongitude() );

			// Wenn Follow-Feature eingeschaltet ist ...
			if (center || PreferencesStore.getFollowMePreference())
			{
				// ... dann karte auf aktuelle Position zentrieren
				m_mapView.getController().animateTo( m_currentLocation );
				Log.d( this.getClass().getSimpleName(),
						"Centering to new location: " + location.getLatitude() + "|" + location.getLongitude() );
			}
		}
	}

}
