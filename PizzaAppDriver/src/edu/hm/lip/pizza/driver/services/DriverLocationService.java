package edu.hm.lip.pizza.driver.services;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.exceptions.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exceptions.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resources.GPSData;
import edu.hm.lip.pizza.driver.util.communication.HttpConnector;
import edu.hm.lip.pizza.driver.util.communication.JsonMapper;

/**
 * Service-Klasse welche die Trackingdaten an das Backend sendet.
 * 
 * @author Stefan Wörner
 */
public class DriverLocationService extends IntentService
{

	/**
	 *
	 */
	public static final String EXTRA_LATITUDE = "lat";

	/**
	 *
	 */
	public static final String EXTRA_LONGITUDE = "lon";

	/**
	 * Konstruktor.
	 */
	public DriverLocationService()
	{
		super( DriverLocationService.class.getSimpleName() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent( Intent intent )
	{
		if (intent == null)
		{
			// TODO Fehlerhandling
			// stopSelf();
			return;
		}

		double lat = intent.getDoubleExtra( EXTRA_LATITUDE, Double.MIN_VALUE );
		double lon = intent.getDoubleExtra( EXTRA_LONGITUDE, Double.MIN_VALUE );

		if (lat == Double.MIN_VALUE && lon == Double.MIN_VALUE)
		{
			// TODO Fehlerhandling
			// stopSelf();
			return;
		}

		try
		{
			GPSData gpsData = new GPSData();
			gpsData.setLat( lat );
			gpsData.setLon( lon );

			HttpEntity entity = new StringEntity( JsonMapper.toJSON( gpsData ) );

			String driverId = PreferencesStore.getDriverIdPreference();

			if (StringUtils.isBlank( driverId ))
			{
				// TODO Throw Exception
				// TODO Fehlerhandling
				stopSelf();
				return;
			}

			StringBuilder path = new StringBuilder();
			path.append( "drivers/" ).append( driverId ).append( "/gpsdata" );

			HttpConnector.doPostRequest( path.toString(), MediaType.APPLICATION_JSON, entity, MediaType.APPLICATION_JSON );
		}
		catch (HostnameNotSetException e)
		{
			// TODO Notification!
			// TODO Tracking abschalten?
			Toast.makeText( this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
			return;
		}
		catch (HttpStatusCodeException e)
		{
			// TODO Notification!
			// TODO Tracking abschalten?
			Toast.makeText( this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
			Log.e( this.getClass().getSimpleName(), e.getMessage() );
		}
		catch (IOException e)
		{
			// TODO Benutzerbenachrichtigung
			Toast.makeText( this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
			Log.e( this.getClass().getSimpleName(), e.getMessage() );

			for (StackTraceElement element : e.getStackTrace())
			{
				Log.e( this.getClass().getSimpleName(), element.toString() );
			}
		}

		// stopSelf();
	}

}
