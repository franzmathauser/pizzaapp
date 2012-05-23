package edu.hm.lip.pizza.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import edu.hm.lip.pizza.domain.Address;
import edu.hm.lip.pizza.domain.google.GoogleDistanceMatrix;

/**
 * DistanceMatrixBroker stellt einen Restcall an die Google Maps Api. Der Respone des Restcalls liefert eine Matrix über
 * die Kosten der Wege in Form einer Matrix. Via JSON ObjectMapper werden die JSON Daten in ein Java Object gemapped.
 * 
 * @author Franz Mathauser
 */
public class DistanceMatrixBroker
{

	/**
	 * Delimiter zwischen origin oder destination Adressen.
	 */
	public static final String DESTINATION_DELIMITER = "|";

	private final List<Address> destinations;

	/**
	 * @param destinations
	 */
	public DistanceMatrixBroker( List<Address> destinations )
	{
		this.destinations = destinations;
	}

	/**
	 * Abfrage der GoogleDistanceApi nach DistanceMatrix Daten.
	 * 
	 * @return GoogleDistanceMatrix representation of request
	 * @throws Exception 
	 */
	public GoogleDistanceMatrix requestDistanceMatrix() throws Exception
	{
		String originsParam = "&origins=" + convertDestinations();
		String destinationsParam = "&destinations=" + convertDestinations();

		// ClientConfig config = new DefaultClientConfig();
		// Client c = Client.create( config );
		//
		// WebResource r = c
		// .resource( "http://maps.googleapis.com/maps/api/distancematrix/json?mode=driving&language=de-DE&sensor=false"
		// + originsParam + destinationsParam );
		// System.out.println( r.toString() );
		//
		// String responseString = r.accept( MediaType.APPLICATION_JSON_TYPE ).get( String.class );
		//
		// GoogleDistanceMatrix response = new ObjectMapper().readValue( responseString, GoogleDistanceMatrix.class );

		ClientRequest request = new ClientRequest( "http://maps.googleapis.com/maps/api/distancematrix/"
				+ "json?mode=driving&language=de-DE&sensor=false" + originsParam + destinationsParam );
		request.accept( MediaType.APPLICATION_JSON );
		ClientResponse<String> response = request.get( String.class );

		if (response.getStatus() != 200)
		{
			throw new RuntimeException( "Failed : HTTP error code : " + response.getStatus() );
		}

		GoogleDistanceMatrix matrix = new ObjectMapper().readValue( response.getEntity(), GoogleDistanceMatrix.class );

		return matrix;
	}

	/**
	 * Converts the given adresses for GET Parameters
	 * 
	 * @return
	 */
	@SuppressWarnings( "deprecation" )
	private String convertDestinations()
	{
		StringBuilder sb = new StringBuilder();

		for (Address destination : destinations)
		{
			sb.append( destination.getAddressString() + DESTINATION_DELIMITER );
		}
		return URLEncoder.encode( sb.toString() );
	}

}
