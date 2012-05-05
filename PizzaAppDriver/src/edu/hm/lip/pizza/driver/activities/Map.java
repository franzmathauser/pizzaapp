package edu.hm.lip.pizza.driver.activities;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import edu.hm.lip.pizza.driver.ConfigStore;
import edu.hm.lip.pizza.driver.PreferencesConstants;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.listener.MapLocationListener;

/**
 * This is the main activity of the application. It displays the map an has a silder for configuration items on the
 * right side.
 * 
 * @author Stefan Wörner
 */
public class Map extends MapActivity
{

	private MapView m_mapView;
	private MapController m_mapController;

	// private Button m_configHandle;
	// private SlidingDrawer m_configSlider;

	private LocationManager m_locationManager;
	private MapLocationListener m_mapLocationListener;
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

		// initial load of the application preferences
		loadPreferences();

		// *********************************************************
		// * Map View **********************************************
		// *********************************************************
		// get the map view
		m_mapView = (MapView) findViewById( R.id.mapview );
		// enable ZoomControls
		m_mapView.setBuiltInZoomControls( true );

		// *********************************************************
		// * Location Manager **************************************
		// *********************************************************
		// get the location manager service
		m_locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
		// if tracking function is enabled, register all required location listener
		if (ConfigStore.getTrackEnabled())
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

		// *********************************************************
		// * Map Controller ****************************************
		// *********************************************************
		// get the map controller
		m_mapController = m_mapView.getController();
		// set zoom level to 18 (1 means world view)
		m_mapController.setZoom( 18 );
		// get last know location of the gps location provider for the initial map extent
		Location lastLocation = m_locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
		// if last known location exists go to that point
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

		// unregister location listener
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

		// load or restore application preferences from preferences store
		loadPreferences();
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

		// store application preferences in preferences file
		savePreferences();
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
	 * Handles the configuration changes. If a configuration item (e.g. track, follow ...) is checked or unchecked this
	 * method is called to process the click event.
	 * 
	 * @param view
	 *            the view from which the event was fired
	 */
	public void configClickHandler( View view )
	{
		CheckBox cb = (CheckBox) findViewById( view.getId() );

		switch (view.getId())
		{
			case R.id.configTrack:
				// store track configuration in the config store
				ConfigStore.setTrackEnabled( cb.isChecked() );

				if (cb.isChecked())
				{
					registerLocationListener();
				}
				else
				{
					unregisterLocationListener();
				}
				break;

			case R.id.configFollow:
				// store follow configuration in the config store
				ConfigStore.setFollowEnabled( cb.isChecked() );
				break;

			case R.id.configRoute:
				// store route configuration in the config store
				ConfigStore.setRouteEnabled( cb.isChecked() );
				break;

			case R.id.configTraffic:
				// store traffic configuration in the config store
				ConfigStore.setTrafficEnabled( cb.isChecked() );
				// enable/disable traffic information on the map
				m_mapView.setTraffic( cb.isChecked() );
				break;

			default:
				break;
		}
	}

	/**
	 * Loads or restores all application preferences from the application preferences file.
	 */
	private void loadPreferences()
	{
		// get the shared prefences settings for our application
		SharedPreferences settings = getSharedPreferences( PreferencesConstants.FILENAME, MODE_PRIVATE );

		// read preferences from file
		boolean trackEnabled = settings.getBoolean( PreferencesConstants.TRACK_CONFIG, true );
		boolean followEnabled = settings.getBoolean( PreferencesConstants.FOLLOW_CONFIG, false );
		boolean routeEnabled = settings.getBoolean( PreferencesConstants.ROUTE_CONFIG, false );
		boolean trafficEnabled = settings.getBoolean( PreferencesConstants.TRAFFIC_CONFIG, false );

		// store preferences in configuration store
		ConfigStore.restoreInstance( trackEnabled, followEnabled, routeEnabled, trafficEnabled );

		// get the configuration checkboxes
		CheckBox cboxTrack = (CheckBox) findViewById( R.id.configTrack );
		CheckBox cboxFollow = (CheckBox) findViewById( R.id.configFollow );
		CheckBox cboxRoute = (CheckBox) findViewById( R.id.configRoute );
		CheckBox cboxTraffic = (CheckBox) findViewById( R.id.configTraffic );

		// restore the configuration checkbox values
		cboxTrack.setChecked( trackEnabled );
		cboxFollow.setChecked( followEnabled );
		cboxRoute.setChecked( routeEnabled );
		cboxTraffic.setChecked( trafficEnabled );
	}

	/**
	 * Saves all application preferences in an application preferences file.
	 */
	private void savePreferences()
	{
		// get the shared prefences settings for our application and save changes by the editor
		SharedPreferences settings = getSharedPreferences( PreferencesConstants.FILENAME, MODE_PRIVATE );
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean( PreferencesConstants.TRACK_CONFIG, ConfigStore.getTrackEnabled() );
		editor.putBoolean( PreferencesConstants.FOLLOW_CONFIG, ConfigStore.getFollowEnabled() );
		editor.putBoolean( PreferencesConstants.ROUTE_CONFIG, ConfigStore.getRouteEnabled() );
		editor.putBoolean( PreferencesConstants.TRAFFIC_CONFIG, ConfigStore.getTrafficEnabled() );

		// finally commit the edits
		editor.commit();
	}

	/**
	 * Registers all required location listener.
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
			// create new MapLocationListener to get notifications concerning the current location
			m_mapLocationListener = new MapLocationListener( this, m_mapView );
			// register location listener for updates using the GPS Provider
			m_locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, m_mapLocationListener );
		}
	}

	/**
	 * Unregisters all location listener.
	 */
	private void unregisterLocationListener()
	{
		// unregister location listener
		m_locationManager.removeUpdates( m_mapLocationListener );
	}
}
