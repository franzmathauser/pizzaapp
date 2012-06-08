package edu.hm.lip.pizza.driver.service;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.exception.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exception.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resource.GPSData;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
import edu.hm.lip.pizza.driver.util.communication.HttpConnector;
import edu.hm.lip.pizza.driver.util.communication.JsonMapper;
import edu.hm.lip.pizza.driver.util.preferences.PreferencesStore;

/**
 * Service-Klasse welche die Trackingdaten an das Backend sendet.
 * 
 * @author Stefan Wörner
 */
public class DriverLocationService extends IntentService
{

	/**
	 * Broadcast-Konstante für die TRANSACTION_DONE Action des DriverLocation Service.
	 */
	public static final String TRANSACTION_DONE = DriverLocationService.class.getName() + ".TRANSACTION_DONE";

	private static final Object SERVICE_LOCK = new Object();

	/**
	 * Konstruktor.
	 */
	public DriverLocationService()
	{
		super( DriverLocationService.class.getName() );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent( Intent intent )
	{
		// Aufrufe Synchronisieren -> Reihenfolge wird eingehalten und die Server Request werden nacheinander
		// abgearbeitet
		synchronized (SERVICE_LOCK)
		{
			if (intent == null)
			{
				Log.e( this.getClass().getSimpleName(), "received intent was null." );
				return;
			}

			double lat = intent.getDoubleExtra( ExtraConstants.LATITUDE_EXTRA, Double.MIN_VALUE );
			double lon = intent.getDoubleExtra( ExtraConstants.LONGITUDE_EXTRA, Double.MIN_VALUE );

			if (lat == Double.MIN_VALUE && lon == Double.MIN_VALUE)
			{
				Log.e( this.getClass().getSimpleName(), "didn't receive position data (lat/lon)." );
				return;
			}

			try
			{
				GPSData gpsData = new GPSData();
				gpsData.setLat( lat );
				gpsData.setLon( lon );

				HttpEntity entity = new StringEntity( JsonMapper.toJSON( gpsData ) );

				String driverId = PreferencesStore.getSelectedDriverIdPreference();

				if (StringUtils.isBlank( driverId ))
				{
					String messagePrefix = getString( R.string.service_driverlocation_unable_to_send_data );
					String message = getString( R.string.service_driverlocation_no_driver_selected );
					String messageSuffix = getString( R.string.service_driverlocation_tracking_disabled );
					notifyTransactionDone( false, messagePrefix + " " + message + " " + messageSuffix );

					Log.e( this.getClass().getSimpleName(), message );
					return;
				}

				StringBuilder path = new StringBuilder();
				path.append( "drivers/" ).append( driverId ).append( "/gpsdata" );

				HttpConnector.doPostRequest( path.toString(), MediaType.APPLICATION_JSON, entity, MediaType.APPLICATION_JSON );
			}
			catch (HostnameNotSetException e)
			{
				String messagePrefix = getString( R.string.service_driverlocation_unable_to_send_data );
				String message = getString( R.string.service_driverlocation_hostname_not_set_message );
				String messageSuffix = getString( R.string.service_driverlocation_tracking_disabled );
				notifyTransactionDone( false, messagePrefix + " " + message + " " + messageSuffix );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (HttpStatusCodeException e)
			{
				String messagePrefix = getString( R.string.service_driverlocation_unable_to_send_data );
				String localizedMsg = getString( R.string.service_driverlocation_illegal_statuscode_message );
				String msgSubstitutions = e.getStatusCode() + " " + e.getReasonPhrase();
				String message = String.format( localizedMsg, msgSubstitutions );
				String messageSuffix = getString( R.string.service_driverlocation_tracking_disabled );
				notifyTransactionDone( false, messagePrefix + " " + message + " " + messageSuffix );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (IOException e)
			{
				String messagePrefix = getString( R.string.service_driverlocation_unable_to_send_data );
				String message = getString( R.string.service_driverlocation_connection_failed_message );
				String messageSuffix = getString( R.string.service_driverlocation_tracking_disabled );
				notifyTransactionDone( false, messagePrefix + " " + message + " " + messageSuffix );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
		}
	}

	private void notifyTransactionDone( boolean successful, String errorMsg )
	{
		Intent intent = new Intent( TRANSACTION_DONE );
		intent.putExtra( ExtraConstants.SUCCESSFUL_EXTRA, successful );
		intent.putExtra( ExtraConstants.ERROR_MSG_EXTRA, errorMsg );
		sendBroadcast( intent );
	}

}
