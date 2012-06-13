package edu.hm.lip.pizza.driver.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.android.maps.GeoPoint;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import edu.hm.lip.pizza.driver.R;
import edu.hm.lip.pizza.driver.exception.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exception.HttpStatusCodeException;
import edu.hm.lip.pizza.driver.objects.resource.DriverRoute;
import edu.hm.lip.pizza.driver.objects.resource.Order;
import edu.hm.lip.pizza.driver.service.extra.ExtraConstants;
import edu.hm.lip.pizza.driver.util.communication.HttpConnector;
import edu.hm.lip.pizza.driver.util.communication.JsonMapper;
import edu.hm.lip.pizza.driver.util.preferences.PreferencesStore;
import edu.hm.lip.pizza.driver.util.route.DriverRouteStore;

/**
 * Service-Klasse welche die Routeninformationen vom Backend holt.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
public class DriverRouteService extends IntentService
{

	/**
	 * Broadcast-Konstante für die TRANSACTION_DONE Action des Route Service.
	 */
	public static final String TRANSACTION_DONE = DriverRouteService.class.getName() + ".TRANSACTION_DONE";

	private static final Object SERVICE_LOCK = new Object();

	/**
	 * Konstruktor.
	 */
	public DriverRouteService()
	{
		super( DriverRouteService.class.getName() );
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
			DriverRoute driverRoute = null;
			Map<Integer, List<GeoPoint>> routePartsGeoPoints = null;

			try
			{
				String driverId = PreferencesStore.getSelectedDriverIdPreference();

				if (StringUtils.isBlank( driverId ))
				{
					String message = getString( R.string.service_driverroute_no_driver_selected );
					notifyTransactionDone( false, message );

					Log.e( this.getClass().getSimpleName(), message );
					return;
				}

				// Pfad zusammenbauen
				StringBuilder path = new StringBuilder();
				path.append( "drivers/" + driverId + "/route" );

				// GetRequest absetzen
				HttpResponse response = HttpConnector.doGetRequest( path.toString(), MediaType.APPLICATION_JSON );

				if (response.getEntity() != null)
				{
					// Routen Objekte erzeugen
					driverRoute = JsonMapper.fromJSON( response.getEntity().getContent(), DriverRoute.class );

					if (driverRoute != null)
					{
						// KML für die Route laden
						routePartsGeoPoints = loadKML( driverRoute );
					}
				}
				else
				{
					// Keine Route gefunden
					String localizedMsg = getString( R.string.service_driverroute_no_route_available );
					notifyTransactionDone( false, localizedMsg );
				}
			}
			catch (HostnameNotSetException e)
			{
				String message = getString( R.string.service_driverroute_hostname_not_set_message );
				notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (HttpStatusCodeException e)
			{
				String localizedMsg = getString( R.string.service_driverroute_illegal_statuscode_message );
				String msgSubstitutions = e.getStatusCode() + " " + e.getReasonPhrase();
				String message = String.format( localizedMsg, msgSubstitutions );
				notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}
			catch (Exception e)
			{
				String message = getString( R.string.service_driverroute_connection_failed_message );
				notifyTransactionDone( false, message );

				Log.e( this.getClass().getSimpleName(), e.getMessage() );
				return;
			}

			if (driverRoute != null && routePartsGeoPoints != null && !routePartsGeoPoints.isEmpty())
			{
				// TODO evtl. in Preferences speichern, sodass nach erneutem Öffnen der App Route wieder da ist
				DriverRouteStore.getInstance().setCurrentRoute( driverRoute );
				DriverRouteStore.getInstance().setAllRouteGeoPoints( routePartsGeoPoints );
				DriverRouteStore.getInstance().setCurrentRoutePartNumber( 0 );
				notifyTransactionDone( true, true );
			}
			else
			{
				notifyTransactionDone( true, false );
			}
		}
	}

	private Map<Integer, List<GeoPoint>> loadKML( DriverRoute driverRoute ) throws IOException, ParserConfigurationException,
			SAXException
	{
		Map<Integer, List<GeoPoint>> kmlRouteGeoPoints = new HashMap<Integer, List<GeoPoint>>();
		int counter = 0;

		for (int i = 0; i < driverRoute.getOrders().size(); i++)
		{
			Order currentOrder = driverRoute.getOrders().get( i );

			if (i == 0)
			{
				kmlRouteGeoPoints.put(
						counter,
						loadRouteParts( Double.toString( driverRoute.getOriginLat() ), Double.toString( driverRoute
								.getOriginLon() ), currentOrder.getCustomer().getLat(), currentOrder.getCustomer().getLon() ) );
				counter++;
			}

			if (i + 1 < driverRoute.getOrders().size())
			{
				Order nextOrder = driverRoute.getOrders().get( i + 1 );
				kmlRouteGeoPoints.put(
						counter,
						loadRouteParts( currentOrder.getCustomer().getLat(), currentOrder.getCustomer().getLon(), nextOrder
								.getCustomer().getLat(), nextOrder.getCustomer().getLon() ) );
				counter++;
			}

			if (i + 1 == driverRoute.getOrders().size())
			{
				kmlRouteGeoPoints.put(
						counter,
						loadRouteParts( currentOrder.getCustomer().getLat(), currentOrder.getCustomer().getLon(),
								Double.toString( driverRoute.getOriginLat() ), Double.toString( driverRoute.getOriginLon() ) ) );
				counter++;
			}
		}

		return kmlRouteGeoPoints;
	}

	private List<GeoPoint> loadRouteParts( String fromLat, String fromLon, String toLat, String toLon ) throws IOException,
			ParserConfigurationException, SAXException
	{
		List<GeoPoint> kmlRouteGeoPoints = new ArrayList<GeoPoint>();

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append( "http://maps.google.com/maps?f=d&hl=de" );

		sbUrl.append( "&saddr=" ); // FROM
		sbUrl.append( fromLat );
		sbUrl.append( "," );
		sbUrl.append( fromLon );
		sbUrl.append( "&daddr=" ); // TO
		sbUrl.append( toLat );
		sbUrl.append( "," );
		sbUrl.append( toLon );

		sbUrl.append( "&ie=UTF8&0&om=0&output=kml" );
		Log.d( this.getClass().getSimpleName(), "Google KML URL=" + sbUrl.toString() );

		URL url = new URL( sbUrl.toString() );
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod( "GET" );
		urlConnection.setDoOutput( true );
		urlConnection.setDoInput( true );
		urlConnection.connect();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse( urlConnection.getInputStream() );

		if (doc.getElementsByTagName( "GeometryCollection" ).getLength() > 0)
		{
			String path = doc.getElementsByTagName( "GeometryCollection" ).item( 0 ).getFirstChild().getFirstChild()
					.getFirstChild().getNodeValue();
			Log.d( this.getClass().getSimpleName(), "path=" + path );

			String[] pairs = path.split( " " );

			for (int i = 0; i < pairs.length; i++)
			{
				String[] lngLat = pairs[i].split( "," );
				kmlRouteGeoPoints.add( new GeoPoint( (int) (Double.parseDouble( lngLat[1] ) * 1E6), (int) (Double
						.parseDouble( lngLat[0] ) * 1E6) ) );
			}
		}

		Log.d( this.getClass().getSimpleName(), "found GeoPoints: " + kmlRouteGeoPoints.size() );
		return kmlRouteGeoPoints;
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
