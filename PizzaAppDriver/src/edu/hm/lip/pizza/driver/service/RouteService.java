package edu.hm.lip.pizza.driver.service;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.exception.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exception.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resource.DriverRoute;
import edu.hm.lip.pizza.driver.util.communication.HttpConnector;
import edu.hm.lip.pizza.driver.util.communication.JsonMapper;

/**
 * Service-Klasse welche die Routeninformationen vom Backend holt.
 * 
 * @author Stefan Wörner
 */
public class RouteService extends IntentService
{

	/**
	 * Broadcast-Konstante für die TRANSACTION_DONE Action des Route Service.
	 */
	public static final String TRANSACTION_DONE = RouteService.class.getName() + ".TRANSACTION_DONE";

	private static final Object SERVICE_LOCK = new Object();

	/**
	 * Konstruktor.
	 */
	public RouteService()
	{
		super( RouteService.class.getName() );
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
			// TODO Store für Route erzeugen
			// TODO evtl. in Preferences speichern, sodass nach erneutem Öffnen der App Route wieder da ist
			// TODO Mehrmaliges abfragen unterbinden?!
			DriverRoute driverRoute = null;

			try
			{
				String driverId = PreferencesStore.getSelectedDriverIdPreference();

				// Pfad zusammenbauen
				StringBuilder path = new StringBuilder();
				path.append( "drivers/" + driverId + "/route" );

				// GetRequest absetzen
				HttpResponse response = HttpConnector.doGetRequest( path.toString(), MediaType.APPLICATION_JSON );

				if (response.getEntity() != null)
				{
					// Routen Objekte erzeugen
					driverRoute = JsonMapper.fromJSON( response.getEntity().getContent(), DriverRoute.class );
				}
				else
				{
					// Keine Route gefunden
					// String localizedMsg = getString( R.string.service_driverinfo_no_driver_available );
					// notifyTransactionDone( false, localizedMsg );
				}
			}
			catch (HostnameNotSetException e)
			{
				// String message = getString( R.string.service_driverinfo_hostname_not_set_message );
				// notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (HttpStatusCodeException e)
			{
				// String localizedMsg = getString( R.string.service_driverinfo_illegal_statuscode_message );
				// String msgSubstitutions = e.getStatusCode() + " " + e.getReasonPhrase();
				// String message = String.format( localizedMsg, msgSubstitutions );
				// notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (IOException e)
			{
				// String message = getString( R.string.service_driverinfo_connection_failed_message );
				// notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}

			// if (fetchedDrivers != null && isUpdateNecessary( fetchedDrivers ))
			// {
			// PreferencesStore.setDriverListPreference( fetchedDrivers );
			// notifyTransactionDone( true, true );
			// }
			// else
			// {
			// notifyTransactionDone( true, false );
			// }
		}
	}

}
