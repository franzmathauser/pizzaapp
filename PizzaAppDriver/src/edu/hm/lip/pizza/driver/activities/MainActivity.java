package edu.hm.lip.pizza.driver.activities;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.listener.DriverLocationListener;

/**
 * Diese Klasse repräsentiert die Haupt-Activity der Applikation. Sie beinhaltet die Kartenansicht und hat am rechten
 * Rand einen Slider für die konfiguration des Kartenverhaltens.
 * 
 * @author Stefan Wörner
 */
public class MainActivity extends MapActivity
{

	private MapView m_mapView;

	private MapController m_mapController;

	// private Button m_configHandle;
	// private SlidingDrawer m_configSlider;

	private LocationManager m_locationManager;

	private DriverLocationListener m_mapLocationListener;

	private MyLocationOverlay m_myLocationOverlay;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		// Preferences auslesen und wiederherstellen
		restoreMapBehaviorPreferences();

		// *********************************************************
		// * Map View **********************************************
		// *********************************************************
		// MapView auslesen
		m_mapView = (MapView) findViewById( R.id.main_mapview_id );
		// Default ZoomControls aktivieren
		m_mapView.setBuiltInZoomControls( true );

		// *********************************************************
		// * Location Manager **************************************
		// *********************************************************
		// LocationManager auslesen
		m_locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
		// Falls die TrackMe Funktion aktiviert ist, müssen alle benötigten LocationListener registriert werden
		if (PreferencesStore.getTrackMePreference())
		{
			registerLocationListener();
		}

		// *********************************************************
		// * Map Overlays ******************************************
		// *********************************************************
		m_myLocationOverlay = new MyLocationOverlay( this, m_mapView );
		m_myLocationOverlay.enableCompass();
		m_myLocationOverlay.disableMyLocation();
		m_mapView.getOverlays().add( m_myLocationOverlay );

		// m_mapLocationOverlay = new MapLocationOverlay( this, m_mapView );
		// m_mapLocationOverlay.enableCompass();
		// m_mapLocationOverlay.enableMyLocation();
		// m_mapView.getOverlays().add( m_mapLocationOverlay );

		// *********************************************************
		// * Map Controller ****************************************
		// *********************************************************
		// MapController auslesen
		m_mapController = m_mapView.getController();
		// Zoom Level festlegen. Wird initial auf 18 festgelegt (1 bedeutet Weltansicht - Bereich von 1 bis 21)
		m_mapController.setZoom( 18 );
		// Für den initialen MapExtent die letzte bekannte Koordinate über den GPS Provider auslesen
		Location lastLocation = m_locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
		// Falls eine Koordinate existiert, dann GeoPoint erzeugen und auf diesen zentrieren.
		if (lastLocation != null)
		{
			int lat = (int) (lastLocation.getLatitude() * 1E6);
			int lng = (int) (lastLocation.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint( lat, lng );
			m_mapController.animateTo( point );
		}

		// m_configHandle = (Button) findViewById( R.id.configHandle );
		// m_configSlider = (SlidingDrawer) findViewById( R.id.configSlider );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.MapActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		// LocationListener deregistrieren
		m_locationManager.removeUpdates( m_mapLocationListener );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart()
	{
		super.onStart();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop()
	{
		super.onStop();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.MapActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.MapActivity#onPause()
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Menü erstellen
		getMenuInflater().inflate( R.menu.menu, menu );

		// Intent für das Preferences Menü setzen
		Intent preferencesIntent = new Intent( getApplicationContext(), PreferencesActivity.class );
		menu.findItem( R.id.menu_preferences_id ).setIntent( preferencesIntent );

		return super.onCreateOptionsMenu( menu );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch (item.getItemId())
		{
			case R.id.menu_about_id:
				// startActivity( new Intent( this, About.class ) );
				return true;

			case R.id.menu_help_id:
				// startActivity( new Intent( this, Help.class ) );
				return true;

			case R.id.menu_preferences_id:
				startActivity( item.getIntent() );
				return true;

			default:
				return super.onOptionsItemSelected( item );
		}
	}

	/**
	 * Handler für Click Events in der Kartenkonfiguration. Wenn ein Configuration Item (z.B. Track, Follow, ...)
	 * selektiert oder deselektiert wird, wird diese Methode aufgerufen um das Event abzuarbeiten.
	 * 
	 * @param view
	 *            Die View von welcher das Event gefeuert wurde
	 */
	public void mapbehaviorClickHandler( View view )
	{
		CheckBox cb = (CheckBox) findViewById( view.getId() );

		switch (view.getId())
		{
			case R.id.mapbehavior_content_trackme_id:
				// Neue TrackMe Einstellung über den PreferencesStore speichern
				PreferencesStore.setTrackMePreference( cb.isChecked() );

				if (cb.isChecked())
				{
					registerLocationListener();
				}
				else
				{
					unregisterLocationListener();
				}
				break;

			case R.id.mapbehavior_content_followme_id:
				// Neue FollowMe Einstellung über den PreferencesStore speichern
				PreferencesStore.setFollowMePreference( cb.isChecked() );
				break;

			case R.id.mapbehavior_content_showroute_id:
				// Neue ShowRoute Einstellung über den PreferencesStore speichern
				PreferencesStore.setShowRoutePreference( cb.isChecked() );
				break;

			case R.id.mapbehavior_content_showtraffic_id:
				// Neue ShowTraffic Einstellung über den PreferencesStore speichern
				PreferencesStore.setShowTrafficPreference( cb.isChecked() );
				// Verkehrsinformationen ein- bzw. ausschalten
				m_mapView.setTraffic( cb.isChecked() );
				break;

			default:
				break;
		}
	}

	private void restoreMapBehaviorPreferences()
	{
		// MapBehaviors Checkboxen auslesen
		CheckBox cboxTrack = (CheckBox) findViewById( R.id.mapbehavior_content_trackme_id );
		CheckBox cboxFollow = (CheckBox) findViewById( R.id.mapbehavior_content_followme_id );
		CheckBox cboxRoute = (CheckBox) findViewById( R.id.mapbehavior_content_showroute_id );
		CheckBox cboxTraffic = (CheckBox) findViewById( R.id.mapbehavior_content_showtraffic_id );

		// Konfiguration wiederherstellen
		cboxTrack.setChecked( PreferencesStore.getTrackMePreference() );
		cboxFollow.setChecked( PreferencesStore.getFollowMePreference() );
		cboxRoute.setChecked( PreferencesStore.getShowRoutePreference() );
		cboxTraffic.setChecked( PreferencesStore.getShowTrafficPreference() );
	}

	/**
	 * Registriert alle benötigten Location Listener, bzw. zeigt einen Fehlerdialog an, falls die Provider deaktiviert
	 * sind.
	 */
	private void registerLocationListener()
	{
		if (!m_locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ))
		{
			// TODO tell user that provider is required for application to work
			// TODO open dialog (ok, cancel)
			// TODO on cancel: close application (destroy)
			// TODO on ok: go to provider enable config page
		}
		else
		{
			// Neuen MapLocationListener erzeugen um Positionsänderungen mitzubekommen
			m_mapLocationListener = new DriverLocationListener( this, m_mapView );
			// LocationListener für Updates über den GPS Provider registrieren
			// TODO finetuning für minDistance und minTime
			m_locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, m_mapLocationListener );
		}
	}

	/**
	 * Deregistriert alle Location Listener.
	 */
	private void unregisterLocationListener()
	{
		// LocationListener deregistrieren
		m_locationManager.removeUpdates( m_mapLocationListener );
	}
}
