package edu.hm.lip.pizza.driver.util;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import edu.hm.lip.pizza.driver.PreferencesStore;
import edu.hm.lip.pizza.driver.exceptions.HostnameNotSetException;
import edu.hm.lip.pizza.driver.exceptions.HttpStatusCodeException;

/**
 * Utility-Klasse um Verbindungen zum Server aufzubauen und Requests abzusetzen.
 * 
 * @author Stefan Wörner
 */
public final class HttpConnector
{

	/**
	 * Privater Konstruktor.
	 */
	private HttpConnector()
	{

	}

	/**
	 * Baut eine Verbindung zum Server auf und sendet einen GET Request. Der zugehörige HttpReponse wird zurückgegeben.
	 * 
	 * @param path
	 *            URI Pfad für Ressource
	 * @param acceptType
	 *            Accept Header MediaType
	 * @return HttpResponse
	 * @throws IOException
	 *             Fehler bei der IO Verarbeitung
	 */
	public static HttpResponse doGetRequest( String path, String acceptType ) throws IOException
	{
		// Host und Port aus den Preferences auslesen
		String host = PreferencesStore.getServerHostnamePreference();
		String port = PreferencesStore.getServerPortPreference();

		if (StringUtils.isBlank( host ))
		{
			throw new HostnameNotSetException( "an hostname is required for server requests." );
		}

		if (StringUtils.isBlank( port ))
		{
			port = "80";
		}

		// URL zusammenbauen
		StringBuilder url = new StringBuilder();
		url.append( "http://" ).append( host ).append( ":" ).append( port );
		url.append( "/" ).append( path );

		// HttpClient instanziieren
		HttpClient httpClient = new DefaultHttpClient();

		// GetRequest erzeugen
		HttpGet getRequest = new HttpGet( url.toString() );
		getRequest.addHeader( HttpHeaders.ACCEPT, acceptType );

		// GetRequest absenden und Response entgegennehmen
		HttpResponse response = httpClient.execute( getRequest );

		// Auf erwarteten StatusCode prüfen
		if (response.getStatusLine().getStatusCode() != 200)
		{
			StringBuilder sb = new StringBuilder();
			sb.append( "GetRequest: status code was: " );
			sb.append( response.getStatusLine().getStatusCode() );
			sb.append( " - expected: 200" );

			// TODO 200er Statuscodes prüfen
			Log.w( HttpConnector.class.getName(), sb.toString() );
		}
		if (response.getStatusLine().getStatusCode() >= 400)
		{
			StringBuilder sb = new StringBuilder();
			sb.append( "ERROR GetRequest - status code was: " );
			sb.append( response.getStatusLine().getStatusCode() );
			sb.append( " (" );
			sb.append( response.getStatusLine().getReasonPhrase() );
			sb.append( ") for address: " );
			sb.append( url.toString() );

			throw new HttpStatusCodeException( sb.toString() );
		}

		// Verbindung wieder schließen
		httpClient.getConnectionManager().shutdown();

		// Reponse zurückgeben
		return response;
	}

	/**
	 * Baut eine Verbindung zum Server auf und sendet einen POST Request. Der zugehörige HttpReponse wird zurückgegeben.
	 * 
	 * @param path
	 *            URI Pfad für Ressource
	 * @param acceptType
	 *            Accept Header MediaType
	 * @param entity
	 *            HttpEntity welche per POST an den Server gesendet werden soll
	 * @param contentType
	 *            ContentType Header MediaType
	 * @return HttpResponse
	 * @throws IOException
	 *             Fehler bei der IO Verarbeitung
	 */
	public static HttpResponse doPostRequest( String path, String acceptType, HttpEntity entity, String contentType )
			throws IOException
	{
		// Host und Port aus den Preferences auslesen
		String host = PreferencesStore.getServerHostnamePreference();
		String port = PreferencesStore.getServerPortPreference();

		if (StringUtils.isBlank( host ))
		{
			throw new HostnameNotSetException( "an hostname is required for server requests." );
		}

		if (StringUtils.isBlank( port ))
		{
			port = "80";
		}

		// URL zusammenbauen
		StringBuilder url = new StringBuilder();
		url.append( "http://" ).append( host ).append( ":" ).append( port );
		url.append( "/" ).append( path );

		// HttpClient instanziieren
		HttpClient httpClient = new DefaultHttpClient();

		// PutRequest erzeugen
		HttpPost postRequest = new HttpPost( url.toString() );
		postRequest.addHeader( HttpHeaders.ACCEPT, acceptType );
		postRequest.addHeader( HttpHeaders.CONTENT_TYPE, contentType );
		postRequest.setEntity( entity );

		// GetRequest absenden und Response entgegennehmen
		HttpResponse response = httpClient.execute( postRequest );

		// Auf erwarteten StatusCode prüfen
		if (response.getStatusLine().getStatusCode() != 201)
		{
			StringBuilder sb = new StringBuilder();
			sb.append( "PutRequest: status code was: " );
			sb.append( response.getStatusLine().getStatusCode() );
			sb.append( " - expected: 201" );

			// TODO 200er Statuscodes prüfen
			Log.w( HttpConnector.class.getName(), sb.toString() );
		}
		if (response.getStatusLine().getStatusCode() >= 400)
		{
			StringBuilder sb = new StringBuilder();
			sb.append( "ERROR PutRequest - status code: " );
			sb.append( response.getStatusLine().getStatusCode() );
			sb.append( " (" );
			sb.append( response.getStatusLine().getReasonPhrase() );
			sb.append( ") for address: " );
			sb.append( url.toString() );

			throw new HttpStatusCodeException( sb.toString() );
		}

		// Verbindung wieder schließen
		httpClient.getConnectionManager().shutdown();

		// Reponse zurückgeben
		return response;
	}
}
