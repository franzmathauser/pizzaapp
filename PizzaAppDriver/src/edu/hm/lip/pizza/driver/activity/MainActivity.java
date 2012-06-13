package edu.hm.lip.pizza.driver.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.exception.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exception.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resource.DriverRoute;
import edu.hm.lip.pizza.driver.objects.resource.Order;
import edu.hm.lip.pizza.driver.overlay.RouteOverlay;
import edu.hm.lip.pizza.driver.overlay.RoutePointOverlay;
import edu.hm.lip.pizza.driver.service.DriverLocationService;
import edu.hm.lip.pizza.driver.service.DriverRouteService;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
import edu.hm.lip.pizza.driver.util.communication.HttpConnector;
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
 * @author Franz Mathauser, Stefan Wörner
 */
public class MainActivity extends MapActivity implements OnSharedPreferenceChangeListener
{

	private MapView m_mapView;

	private MapController m_mapController;

	private MyLocationOverlay m_myLocationOverlay;

	private ProgressDialog m_driverRouteProgressDialog;

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

		if (lastLocation != null)
		{
			// Letzte bekannte Position an Server senden
			Intent intent = new Intent( this, DriverLocationService.class );
			intent.putExtra( ExtraConstants.LATITUDE_EXTRA, lastLocation.getLatitude() );
			intent.putExtra( ExtraConstants.LONGITUDE_EXTRA, lastLocation.getLongitude() );
			startService( intent );
		}

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

		// Intent für das About Menü setzen
		Intent aboutIntent = new Intent( getApplicationContext(), AboutActivity.class );
		menu.findItem( R.id.menu_about_id ).setIntent( aboutIntent );

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
				startActivity( item.getIntent() );
				return true;

				// case R.id.menu_help_id:
				// // startActivity( new Intent( this, Help.class ) );
				// return true;

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
		if (!DriverRouteStore.getInstance().isRoutingActive())
		{
			Intent intent = new Intent( this, DriverRouteService.class );
			startService( intent );

			m_driverRouteProgressDialog = ProgressDialog.show( this, getString( R.string.service_progress_dialog_wait_title ),
					getString( R.string.service_fetch_route_message ) );
		}
	}

	/**
	 * Handler für Click Events des SendDelivered Buttons auf der Karte.
	 * 
	 * @param view
	 *            Die View von welcher das Event gefeuert wurde
	 */
	public void sendDeliveredClickHandler( View view )
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();

		// Prüfen ob weiterer Routenabschnitt verfügbar ist
		if (driverRouteStore.isNextRoutePartNumberAvailable())
		{
			String message = String.format( getString( R.string.main_senddelivered_message ),
					driverRouteStore.getCurrentOrderInfoString() );
			final int orderId = driverRouteStore.getCurrentOrder().getId();

			// Wenn beide Provider nicht aktiviert sind muss der Benutzer aufgefordert werden diese zu aktivieren
			AlertDialog.Builder builder = new AlertDialog.Builder( this );
			builder.setTitle( R.string.main_senddelivered_title );
			builder.setMessage( message );
			builder.setCancelable( false );
			builder.setPositiveButton( R.string.main_senddelivered_bt_positive, new DialogInterface.OnClickListener()
			{

				public void onClick( DialogInterface dialog, int id )
				{
					// Dialog schließen
					dialog.cancel();

					// Delivered Flag an Server senden
					sendDelivered( orderId );

					// Zum nächsten Routenabschnitt wechseln
					DriverRouteStore.getInstance().nextRoutePart();

					// Broadcast senden um Strecke zu zeichnen
					Intent drsIntent = new Intent( DriverRouteService.TRANSACTION_DONE );
					drsIntent.putExtra( ExtraConstants.SUCCESSFUL_EXTRA, true );
					drsIntent.putExtra( ExtraConstants.REFRESH_EXTRA, true );
					sendBroadcast( drsIntent );
				}
			} );
			builder.setNegativeButton( R.string.main_senddelivered_bt_negative, new DialogInterface.OnClickListener()
			{

				public void onClick( DialogInterface dialog, int id )
				{
					// Dialog schließen und nichts unternehmen
					dialog.cancel();
				}
			} );

			// Hinweisdialog anzeigen
			builder.create().show();
		}
		else
		{
			if (driverRouteStore.isRoutingActive())
			{
				// Wenn beide Provider nicht aktiviert sind muss der Benutzer aufgefordert werden diese zu aktivieren
				AlertDialog.Builder builder = new AlertDialog.Builder( this );
				builder.setTitle( R.string.main_senddelivered_back_pizzeria_title );
				builder.setMessage( R.string.main_senddelivered_back_pizzeria_message );
				builder.setCancelable( false );
				builder.setPositiveButton( R.string.main_senddelivered_bt_positive, new DialogInterface.OnClickListener()
				{

					public void onClick( DialogInterface dialog, int id )
					{
						// Dialog schließen
						dialog.cancel();

						// Route Store reset
						DriverRouteStore.getInstance().resetStore();

						// Broadcast senden um Strecke zu zeichnen
						Intent drsIntent = new Intent( DriverRouteService.TRANSACTION_DONE );
						drsIntent.putExtra( ExtraConstants.SUCCESSFUL_EXTRA, true );
						drsIntent.putExtra( ExtraConstants.REFRESH_EXTRA, true );
						sendBroadcast( drsIntent );
					}
				} );
				builder.setNegativeButton( R.string.main_senddelivered_bt_negative, new DialogInterface.OnClickListener()
				{

					public void onClick( DialogInterface dialog, int id )
					{
						// Dialog schließen und nichts unternehmen
						dialog.cancel();
					}
				} );

				// Hinweisdialog anzeigen
				builder.create().show();
			}
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

			// case R.id.mapbehavior_content_showroute_id:
			// // Neue ShowRoute Einstellung über den PreferencesStore speichern
			// PreferencesStore.setShowRoutePreference( cb.isChecked() );
			// break;

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
		// CheckBox cboxRoute = (CheckBox) findViewById( R.id.mapbehavior_content_showroute_id );
		CheckBox cboxTraffic = (CheckBox) findViewById( R.id.mapbehavior_content_showtraffic_id );

		// Konfiguration wiederherstellen
		cboxTrack.setChecked( PreferencesStore.getTrackMePreference() );
		cboxFollow.setChecked( PreferencesStore.getFollowMePreference() );
		// cboxRoute.setChecked( PreferencesStore.getShowRoutePreference() );
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

				if (refresh)
				{
					// Route bzw. Routenabschnitt zeichnen
					drawRoute();
					// Routenpunkte zeichnen (Pizzeria, Kunden)
					drawRoutePoints();
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

	private void sendDelivered( int orderId )
	{
		try
		{
			StringBuilder path = new StringBuilder();
			path.append( "orders/" ).append( orderId ).append( "/delivered" );

			HttpConnector.doPutRequest( path.toString(), MediaType.APPLICATION_JSON, null, null );
		}
		catch (HostnameNotSetException e)
		{
			String message = getString( R.string.service_senddelivered_hostname_not_set_message );
			showErrorDialog( message );

			Log.e( this.getClass().getSimpleName(), e.getMessage() );
			return;
		}
		catch (HttpStatusCodeException e)
		{
			String localizedMsg = getString( R.string.service_senddelivered_illegal_statuscode_message );
			String msgSubstitutions = e.getStatusCode() + " " + e.getReasonPhrase();
			String message = String.format( localizedMsg, msgSubstitutions );
			showErrorDialog( message );

			Log.e( this.getClass().getSimpleName(), e.getMessage() );
			return;
		}
		catch (IOException e)
		{
			String message = getString( R.string.service_senddelivered_connection_failed_message );
			showErrorDialog( message );

			Log.e( this.getClass().getSimpleName(), e.getMessage() );
			return;
		}
	}

	private void drawRoutePoints()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();
		DriverRoute driverRoute = driverRouteStore.getCurrentRoute();

		// Alte Routenpunkte entfernen, falls vorhanden
		removeRoutePoints();

		if (driverRoute != null)
		{
			GeoPoint pizzaStoreGP = new GeoPoint( (int) (driverRoute.getOriginLat() * 1E6),
					(int) (driverRoute.getOriginLon() * 1E6) );

			Drawable pizzeriaMarker = getResources().getDrawable( R.drawable.ic_routepoint_pizzeria );
			pizzeriaMarker.setBounds( 0, -pizzeriaMarker.getIntrinsicHeight(), pizzeriaMarker.getIntrinsicWidth(), 0 );
			RoutePointOverlay pizzeriaRoutePoint = new RoutePointOverlay( pizzeriaMarker );
			OverlayItem pizzeriaOverlayItem = new OverlayItem( pizzaStoreGP, "", "" );
			pizzeriaRoutePoint.addOverlay( pizzeriaOverlayItem );

			m_mapView.getOverlays().add( pizzeriaRoutePoint );
			driverRouteStore.setVisiblePizzeriaPointOverlay( pizzeriaRoutePoint );

			for (Order order : driverRoute.getOrders())
			{
				Drawable customerMarker = getResources().getDrawable( R.drawable.ic_routepoint_customer );
				customerMarker.setBounds( customerMarker.getIntrinsicWidth() / -2, -customerMarker.getIntrinsicHeight(),
						customerMarker.getIntrinsicWidth() / 2, 0 );
				RoutePointOverlay customerRoutePoint = new RoutePointOverlay( customerMarker );

				GeoPoint customerGP = new GeoPoint( (int) (Double.parseDouble( order.getCustomer().getLat() ) * 1E6),
						(int) (Double.parseDouble( order.getCustomer().getLon() ) * 1E6) );

				OverlayItem customerOverlayItem = new OverlayItem( customerGP, "", "" );
				customerRoutePoint.addOverlay( customerOverlayItem );

				m_mapView.getOverlays().add( customerRoutePoint );
				driverRouteStore.addVisibleCustomerPointOverlays( customerRoutePoint );
			}
		}
	}

	private void removeRoutePoints()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();
		List<Overlay> mapOverlays = m_mapView.getOverlays();

		if (driverRouteStore.getVisiblePizzeriaPointOverlay() != null
				&& mapOverlays.contains( driverRouteStore.getVisiblePizzeriaPointOverlay() ))
		{
			mapOverlays.remove( driverRouteStore.getVisiblePizzeriaPointOverlay() );
			driverRouteStore.setVisiblePizzeriaPointOverlay( null );
		}

		if (driverRouteStore.getVisibleCustomerPointOverlays() != null)
		{
			for (RoutePointOverlay routePointOverlay : driverRouteStore.getVisibleCustomerPointOverlays())
			{
				if (mapOverlays.contains( routePointOverlay ))
				{
					mapOverlays.remove( routePointOverlay );
				}
			}
			driverRouteStore.setVisibleCustomerPointOverlays( null );
		}
	}

	private void drawRoute()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();

		// Alte Route entfernen, falls vorhanden
		removeRoute();

		List<GeoPoint> routeGeoPoints = driverRouteStore.getCurrentRoutePartGeoPoints();

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

			driverRouteStore.addVisibleRouteOverlays( routeOverlays );
		}
	}

	private void removeRoute()
	{
		DriverRouteStore driverRouteStore = DriverRouteStore.getInstance();
		List<Overlay> mapOverlays = m_mapView.getOverlays();

		if (driverRouteStore.getVisibleRouteOverlays() != null)
		{
			for (RouteOverlay routeOverlay : driverRouteStore.getVisibleRouteOverlays())
			{
				if (mapOverlays.contains( routeOverlay ))
				{
					mapOverlays.remove( routeOverlay );
				}
			}
			driverRouteStore.setVisibleRouteOverlays( null );
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
