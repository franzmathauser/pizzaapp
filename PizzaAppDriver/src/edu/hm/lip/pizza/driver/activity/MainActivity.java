package edu.hm.lip.pizza.driver.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.objects.resource.DriverRoute;
import edu.hm.lip.pizza.driver.objects.resource.Order;
import edu.hm.lip.pizza.driver.overlay.RouteOverlay;
import edu.hm.lip.pizza.driver.overlay.RoutePointOverlay;
import edu.hm.lip.pizza.driver.service.DriverLocationService;
import edu.hm.lip.pizza.driver.service.DriverRouteService;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
import edu.hm.lip.pizza.driver.util.location.LastLocationFinder;
import edu.hm.lip.pizza.driver.util.location.LocationDrawer;
import edu.hm.lip.pizza.driver.util.location.LocationProviderManager;
import edu.hm.lip.pizza.driver.util.preferences.PreferencesConstants;
import edu.hm.lip.pizza.driver.util.preferences.PreferencesStore;
import edu.hm.lip.pizza.driver.util.route.DriverRouteStore;

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

	private ProgressDialog m_driverRouteProgressDialog;

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
		DriverRouteStore.destroyInstance();

		// Deregistrieren des PreferenceChangeListeners
		getSharedPreferences( PreferencesConstants.FILENAME, Activity.MODE_PRIVATE ).unregisterOnSharedPreferenceChangeListener(
				this );

		if (PreferencesStore.getTrackMePreference())
		{
			// BroadcastReceiver für DriverLocation Service deregistrieren
			unregisterReceiver( m_driverLocationReceiver );
		}

		// BroadcastReceiver für DriverRoute Service deregistrieren
		unregisterReceiver( m_driverRouteReceiver );
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
		intent.putExtra( ExtraConstants.LATITUDE_EXTRA, lastLocation.getLatitude() );
		intent.putExtra( ExtraConstants.LONGITUDE_EXTRA, lastLocation.getLongitude() );
		startService( intent );

		// Bildschirmschoner solange die Activity aktiv ist ausschalten (Gilt nur für diese Activity!!)
		getWindow().addFlags( LayoutParams.FLAG_KEEP_SCREEN_ON );

		// Für Änderungen an den Einstellungen registrieren, um ggf. die LocationListener zu aktualisieren
		getSharedPreferences( PreferencesConstants.FILENAME, Activity.MODE_PRIVATE ).registerOnSharedPreferenceChangeListener(
				this );

		// BroadcastReceiver für DriverRoute Service registrieren
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction( DriverRouteService.TRANSACTION_DONE );
		registerReceiver( m_driverRouteReceiver, intentFilter );
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
		Intent intent = new Intent( this, DriverRouteService.class );
		startService( intent );

		m_driverRouteProgressDialog = ProgressDialog.show( this, getString( R.string.service_progress_dialog_wait_title ),
				getString( R.string.service_fetch_route_message ) );
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

		if (DriverRouteStore.getInstance().nextVisiblePartAvailable())
		{
			// Broadcast senden um Strecke zu zeichnen
			Intent intent = new Intent( DriverRouteService.TRANSACTION_DONE );
			intent.putExtra( ExtraConstants.SUCCESSFUL_EXTRA, true );
			intent.putExtra( ExtraConstants.REFRESH_EXTRA, true );
			sendBroadcast( intent );
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

			if (!successful)
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

	private BroadcastReceiver m_driverRouteReceiver = new BroadcastReceiver()
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
				// Auslesen ob Ansicht aktualisiert werden muss
				boolean refresh = intent.getBooleanExtra( ExtraConstants.REFRESH_EXTRA, false );

				if (refresh && DriverRouteStore.getInstance().getCurrentlyVisibleRoutePart() != Integer.MIN_VALUE)
				{
					// Routenpunkte zeichnen (Pizzeria, Kunden)
					drawRoutePoints();
					// Route bzw. Routenabschnitt zeichnen
					drawRoute();
					// Karte aktualisieren
					m_mapView.invalidate();
				}

				// Progress Dialog schließen
				m_driverRouteProgressDialog.dismiss();
			}
			else
			{
				// Progress Dialog schließen
				m_driverRouteProgressDialog.dismiss();

				// Fehlermeldung auslesen
				String message = intent.getStringExtra( ExtraConstants.ERROR_MSG_EXTRA );
				// Fehlermeldung anzeigen
				showErrorDialog( message );
			}
		}
	};

	private void drawRoutePoints()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();

		if (driverRouteStore.getPizzeriaPointOverlay() == null && driverRouteStore.getCustomerPointOverlay() == null)
		{
			DriverRoute driverRoute = driverRouteStore.getCurrentRoute();

			GeoPoint pizzaStoreGP = new GeoPoint( (int) (driverRoute.getOriginLat() * 1E6),
					(int) (driverRoute.getOriginLon() * 1E6) );

			Drawable pizzeriaMarker = getResources().getDrawable( R.drawable.ic_routepoint_pizzeria );
			pizzeriaMarker.setBounds( 0, -pizzeriaMarker.getIntrinsicHeight(), pizzeriaMarker.getIntrinsicWidth(), 0 );
			RoutePointOverlay pizzeriaRoutePoint = new RoutePointOverlay( pizzeriaMarker );
			OverlayItem pizzeriaOverlayItem = new OverlayItem( pizzaStoreGP, "", "" );
			pizzeriaRoutePoint.addOverlay( pizzeriaOverlayItem );

			m_mapView.getOverlays().add( pizzeriaRoutePoint );
			driverRouteStore.setPizzeriaPointOverlay( pizzeriaRoutePoint );

			Drawable customerMarker = getResources().getDrawable( R.drawable.ic_routepoint_customer );
			customerMarker.setBounds( customerMarker.getIntrinsicWidth() / -2, customerMarker.getIntrinsicHeight() / -2,
					customerMarker.getIntrinsicWidth() / 2, customerMarker.getIntrinsicHeight() / 2 );
			RoutePointOverlay customerRoutePoint = new RoutePointOverlay( customerMarker );

			for (Order order : driverRoute.getOrders())
			{
				GeoPoint customerGP = new GeoPoint( (int) (Double.parseDouble( order.getCustomer().getLat() ) * 1E6),
						(int) (Double.parseDouble( order.getCustomer().getLon() ) * 1E6) );

				OverlayItem customerOverlayItem = new OverlayItem( customerGP, "", "" );
				customerRoutePoint.addOverlay( customerOverlayItem );

				m_mapView.getOverlays().add( customerRoutePoint );
			}

			driverRouteStore.setCustomerPointOverlay( customerRoutePoint );
		}
	}

	// private void removeRoutePoints()
	// {
	// DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();
	// List<Overlay> mapOverlays = m_mapView.getOverlays();
	//
	// if (driverRouteStore.getPizzeriaPointOverlay() != null
	// && mapOverlays.contains( driverRouteStore.getPizzeriaPointOverlay() ))
	// {
	// mapOverlays.remove( driverRouteStore.getPizzeriaPointOverlay() );
	// driverRouteStore.setPizzeriaPointOverlay( null );
	// }
	//
	// if (driverRouteStore.getCustomerPointOverlay() != null
	// && mapOverlays.contains( driverRouteStore.getCustomerPointOverlay() ))
	// {
	// mapOverlays.remove( driverRouteStore.getCustomerPointOverlay() );
	// driverRouteStore.setCustomerPointOverlay( null );
	// }
	// }

	private void drawRoute()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();

		// Alte Route entfernen, falls vorhanden
		removeRoute();

		List<GeoPoint> routeGeoPoints = driverRouteStore.getCurrentRoutePartsGeoPoints().get(
				driverRouteStore.getCurrentlyVisibleRoutePart() );

		if (routeGeoPoints != null)
		{
			List<RouteOverlay> routeOverlays = new ArrayList<RouteOverlay>();

			GeoPoint startGP = routeGeoPoints.get( 0 );
			GeoPoint gp1;
			GeoPoint gp2 = startGP;
			for (int i = 1; i < routeGeoPoints.size(); i++)
			{
				gp1 = gp2;
				gp2 = routeGeoPoints.get( i );
				RouteOverlay routeOverlay = new RouteOverlay( gp1, gp2, Color.BLUE );

				m_mapView.getOverlays().add( routeOverlay );
				routeOverlays.add( routeOverlay );
			}

			driverRouteStore.addCurrentlyVisibleRouteOverlays( routeOverlays );
		}
	}

	private void removeRoute()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();
		List<Overlay> mapOverlays = m_mapView.getOverlays();

		if (driverRouteStore.getCurrentlyVisibleRouteOverlays() != null)
		{
			for (RouteOverlay routeOverlay : driverRouteStore.getCurrentlyVisibleRouteOverlays())
			{
				if (mapOverlays.contains( routeOverlay ))
				{
					mapOverlays.remove( routeOverlay );
					driverRouteStore.setCurrentlyVisibleRouteOverlays( null );
				}
			}
		}
	}

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
