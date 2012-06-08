package edu.hm.lip.pizza.driver.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.exception.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exception.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resource.Driver;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
import edu.hm.lip.pizza.driver.util.communication.HttpConnector;
import edu.hm.lip.pizza.driver.util.communication.JsonMapper;
import edu.hm.lip.pizza.driver.util.preferences.PreferencesStore;

/**
 * Service-Klasse welche die Fahrerinformationen vom Backend holt.
 * 
 * @author Stefan Wörner
 */
public class DriverInfoService extends IntentService
{

	/**
	 * Broadcast-Konstante für die TRANSACTION_DONE Action des DriverInfo Service.
	 */
	public static final String TRANSACTION_DONE = DriverInfoService.class.getName() + ".TRANSACTION_DONE";

	private static final Object SERVICE_LOCK = new Object();

	/**
	 * Konstruktor.
	 */
	public DriverInfoService()
	{
		super( DriverInfoService.class.getName() );
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
			List<Driver> fetchedDrivers = null;

			try
			{
				// Pfad zusammenbauen
				StringBuilder path = new StringBuilder();
				path.append( "drivers" );

				// GetRequest absetzen
				HttpResponse response = HttpConnector.doGetRequest( path.toString(), MediaType.APPLICATION_JSON );

				if (response.getEntity() != null)
				{
					// Liste mit Driver Objekten erzeugen
					fetchedDrivers = JsonMapper.fromJSONArray( response.getEntity().getContent(), Driver.class );
				}
				else
				{
					// Keine Fahrer gefunden
					String localizedMsg = getString( R.string.service_driverinfo_no_driver_available );
					notifyTransactionDone( false, localizedMsg );
				}
			}
			catch (HostnameNotSetException e)
			{
				String message = getString( R.string.service_driverinfo_hostname_not_set_message );
				notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (HttpStatusCodeException e)
			{
				String localizedMsg = getString( R.string.service_driverinfo_illegal_statuscode_message );
				String msgSubstitutions = e.getStatusCode() + " " + e.getReasonPhrase();
				String message = String.format( localizedMsg, msgSubstitutions );
				notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (IOException e)
			{
				String message = getString( R.string.service_driverinfo_connection_failed_message );
				notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}

			if (fetchedDrivers != null && isUpdateNecessary( fetchedDrivers ))
			{
				PreferencesStore.setDriverListPreference( fetchedDrivers );
				notifyTransactionDone( true, true );
			}
			else
			{
				notifyTransactionDone( true, false );
			}
		}
	}

	private boolean isUpdateNecessary( List<Driver> fetchedDrivers )
	{
		boolean update = false;

		// Aktuell gespeicherte Preferences auslesen
		List<Driver> storedDriverList = PreferencesStore.getDriverListPreference();

		// Wenn Anzahl gleich muss weiter geprüft werden
		if (storedDriverList.size() == fetchedDrivers.size())
		{
			// Über neue Driver iterieren und prüfen ob dieser bereits in den gespeicherten enthalten ist
			for (Driver driver : fetchedDrivers)
			{
				if (!storedDriverList.contains( driver ))
				{
					update = true;
					break;
				}
			}
		}
		// ... sonst ist update durchzuführen
		else
		{
			update = true;
		}

		return update;
	}

	private void notifyTransactionDone( boolean successful, boolean refresh )
	{
		notifyTransactionDone( successful, refresh, null );
	}

	private void notifyTransactionDone( boolean successful, String errorMsg )
	{
		notifyTransactionDone( successful, false, errorMsg );
	}

	private void notifyTransactionDone( boolean successful, boolean refresh, String errorMsg )
	{
		Intent intent = new Intent( TRANSACTION_DONE );
		intent.putExtra( ExtraConstants.SUCCESSFUL_EXTRA, successful );
		intent.putExtra( ExtraConstants.REFRESH_EXTRA, refresh );
		intent.putExtra( ExtraConstants.ERROR_MSG_EXTRA, errorMsg );
		sendBroadcast( intent );
	}

}
