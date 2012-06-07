package edu.hm.lip.pizza.driver.activity;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.PreferencesConstants;
import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.service.DriverLocationService;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
import edu.hm.lip.pizza.driver.util.location.LastLocationFinder;
import edu.hm.lip.pizza.driver.util.location.LocationDrawer;
import edu.hm.lip.pizza.driver.util.location.LocationProviderManager;

/**
 * Diese Klasse repräsentiert die Haupt-Activity der Applikation. Sie beinhaltet die Kartenansicht und hat am rechten
 * Rand einen Slider für die konfiguration des Kartenverhaltens.
 * 
 * @author Stefan Wörner
 */
public class MainActivity extends MapActivity implements OnSharedPreferenceChangeListener
{

	private MapView m_mapView;

	private MapController m_mapController;

	private MyLocationOverlay m_myLocationOverlay;

	// private Button m_configHandle;
	// private SlidingDrawer m_configSlider;

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

		// MapView auslesen
		m_mapView = (MapView) findViewById( R.id.main_mapview_id );
		// MapController auslesen
		m_mapController = m_mapView.getController();
		// MyLocationOverlay erzeugen um Kompass auf der Karte anzuzeigen
		m_myLocationOverlay = new MyLocationOverlay( this, m_mapView );

		// Preferences auslesen und wiederherstellen
		restoreMapBehaviorPreferences();

		// Default ZoomControls aktivieren
		m_mapView.setBuiltInZoomControls( true );
		// Zoom Level festlegen. Wird initial auf 18 festgelegt (1 bedeutet Weltansicht - Bereich von 1 bis 21)
		m_mapController.setZoom( 18 );

		// Kompass auf Karte ablegen
		m_myLocationOverlay.enableCompass();
		m_myLocationOverlay.disableMyLocation();
		m_mapView.getOverlays().add( m_myLocationOverlay );

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

		// TODO in onStop kopieren, wenn Server Kommunikation über Service erfolgt?
		// LocationListener deregistrieren
		LocationProviderManager.getInstance( this, m_mapView ).unregisterLocationListener();

		// Utility-Klassen löschen
		LocationDrawer.destroyInstance();
		LocationProviderManager.destroyInstance();
		LastLocationFinder.destroyInstance();

		// Deregistrieren des PreferenceChangeListeners
		getSharedPreferences( PreferencesConstants.FILENAME, Activity.MODE_PRIVATE ).unregisterOnSharedPreferenceChangeListener(
				this );

		// BroadcastReceiver für DriverLocation Service deregistrieren
		unregisterReceiver( m_driverLocationReceiver );
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

		// Alle benötigten LocationListener registrieren
		LocationProviderManager.getInstance( this, m_mapView ).registerLocationListener();

		if (PreferencesStore.getTrackMePreference())
		{
			// BroadcastReceiver für DriverLocation Service registrieren
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction( DriverLocationService.TRANSACTION_DONE );
			registerReceiver( m_driverLocationReceiver, intentFilter );
		}

		// Letzte bekannte Position auslesen
		Location lastLocation = LastLocationFinder.getInstance( this ).getLastLocation();

		// Letzte bekannte Position zeichnen lassen
		LocationDrawer.getInstance( this, m_mapView ).updateCurrentLocation( lastLocation, true );

		// Letzte bekannte Position an Server senden
		Intent intent = new Intent( this, DriverLocationService.class );
		intent.putExtra( ExtraConstants.EXTRA_LATITUDE, lastLocation.getLatitude() );
		intent.putExtra( ExtraConstants.EXTRA_LONGITUDE, lastLocation.getLongitude() );
		startService( intent );

		// Bildschirmschoner solange die Activity aktiv ist ausschalten (Gilt nur für diese Activity!!)
		getWindow().addFlags( LayoutParams.FLAG_KEEP_SCREEN_ON );

		// Für Änderungen an den Einstellungen registrieren, um ggf. die LocationListener zu aktualisieren
		getSharedPreferences( PreferencesConstants.FILENAME, Activity.MODE_PRIVATE ).registerOnSharedPreferenceChangeListener(
				this );
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

		// Bildschirmschoner wieder einschalten
		getWindow().clearFlags( LayoutParams.FLAG_KEEP_SCREEN_ON );
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
	 * Handler für Click Events des MapMode Buttons auf der Karte.
	 * 
	 * @param view
	 *            Die View von welcher das Event gefeuert wurde
	 */
	public void mapModeClickHandler( View view )
	{
		m_mapView.setSatellite( !m_mapView.isSatellite() );
	}

	/**
	 * Handler für Click Events des CurrentLocation Buttons auf der Karte.
	 * 
	 * @param view
	 *            Die View von welcher das Event gefeuert wurde
	 */
	public void currentLocationClickHandler( View view )
	{
		// letzte gezeichnete Position auslesen
		GeoPoint currentLocation = LocationDrawer.getCurrentLocation();

		// Falls verfügbar, dann zentrieren
		if (currentLocation != null)
		{
			m_mapController.animateTo( currentLocation );
			return;
		}

		// ... sonst letzte bekannte Position suchen
		currentLocation = LastLocationFinder.getInstance( this ).getLastLocationAsGeoPoint();

		// Falls verfügbar, dann zentrieren
		if (currentLocation != null)
		{
			m_mapController.animateTo( currentLocation );
			return;
		}
		// ... sonst Fehler melden
		else
		{
			Toast.makeText( this, "no location available", Toast.LENGTH_SHORT ).show();
		}
	}

	/**
	 * Handler für Click Events des LoadRoute Buttons auf der Karte.
	 * 
	 * @param view
	 *            Die View von welcher das Event gefeuert wurde
	 */
	public void loadRouteClickHandler( View view )
	{
		// TODO CurrentLocation Click Handler
	}

	/**
	 * Handler für Click Events des SendDelivered Buttons auf der Karte.
	 * 
	 * @param view
	 *            Die View von welcher das Event gefeuert wurde
	 */
	public void sendDeliveredClickHandler( View view )
	{
		// TODO CurrentLocation Click Handler
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
					LocationProviderManager.getInstance( this, m_mapView ).registerLocationListener();

					// BroadcastReceiver für DriverLocation Service registrieren
					IntentFilter intentFilter = new IntentFilter();
					intentFilter.addAction( DriverLocationService.TRANSACTION_DONE );
					registerReceiver( m_driverLocationReceiver, intentFilter );
				}
				else
				{
					LocationProviderManager.getInstance( this, m_mapView ).unregisterLocationListener();

					// BroadcastReceiver für DriverLocation Service deregistrieren
					unregisterReceiver( m_driverLocationReceiver );
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
	 * {@inheritDoc}
	 * 
	 * @see android.content.SharedPreferences.OnSharedPreferenceChangeListener#onSharedPreferenceChanged(android.content.SharedPreferences,
	 *      java.lang.String)
	 */
	@Override
	public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key )
	{
		// Wenn die Preferences zu den Tracking Intervallen geändert werden müssen die Location Listener aktualisiert
		// werden, sprich unregister und register
		if (getString( R.string.pref_category_tracking_distance_interval_key ).equals( key )
				|| getString( R.string.pref_category_tracking_time_interval_key ).equals( key ))
		{
			LocationProviderManager.getInstance( this, m_mapView ).unregisterLocationListener();
			LocationProviderManager.getInstance( this, m_mapView ).registerLocationListener();
		}
	}

	private BroadcastReceiver m_driverLocationReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive( Context context, Intent intent )
		{
			if (intent == null)
			{
				return;
			}

			// Status auslesen
			boolean successful = intent.getBooleanExtra( ExtraConstants.SUCCESSFUL_EXTRA, false );

			if (successful)
			{
				return;
			}
			else
			{
				// Tracking ausschalten
				PreferencesStore.setTrackMePreference( Boolean.FALSE );
				((CheckBox) findViewById( R.id.mapbehavior_content_trackme_id )).setChecked( Boolean.FALSE );
				// Locationlistener deregistrieren
				LocationProviderManager.getInstance( MainActivity.this, m_mapView ).unregisterLocationListener();
				// BroadcastReceiver für DriverLocation Service deregistrieren
				unregisterReceiver( m_driverLocationReceiver );

				// Fehlermeldung auslesen
				String message = intent.getStringExtra( ExtraConstants.ERROR_MSG_EXTRA );
				// Fehlermeldung anzeigen
				showErrorDialog( message );
			}
		}
	};

	private void showErrorDialog( String message )
	{
		// Wenn beide Provider nicht aktiviert sind muss der Benutzer aufgefordert werden diese zu aktivieren
		AlertDialog.Builder builder = new AlertDialog.Builder( this );
		builder.setMessage( message ).setCancelable( false );
		builder.setPositiveButton( R.string.service_errordialog_bt_ok, new DialogInterface.OnClickListener()
		{

			public void onClick( DialogInterface dialog, int id )
			{
				// Wenn OK Button geklickt wird, Dialog schließen
				dialog.cancel();
			}
		} );
		// Hinweisdialog anzeigen
		builder.create().show();
	}

}
