package edu.hm.lip.pizza.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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

	public DistanceMatrixBroker( List<Address> destinations )
	{
		this.destinations = destinations;
	}

	/**
	 * Abfrage der GoogleDistanceApi nach DistanceMatrix Daten.
	 * 
	 * @return GoogleDistanceMatrix representation of request
	 * @throws IOException fehler beim lesen des JSON-Response
	 */
	public GoogleDistanceMatrix requestDistanceMatrix() throws IOException
	{

		String origins = "&origins=" + convertDestinations();
		String destinations = "&destinations=" + convertDestinations();

		ClientConfig config = new DefaultClientConfig();
		Client c = Client.create( config );

		WebResource r = c
				.resource( "http://maps.googleapis.com/maps/api/distancematrix/json?mode=driving&language=de-DE&sensor=false"
						+ origins + destinations );
		System.out.println( r.toString() );

		String responseString = r.accept( MediaType.APPLICATION_JSON_TYPE ).get( String.class );

		GoogleDistanceMatrix response = new ObjectMapper().readValue( responseString, GoogleDistanceMatrix.class );

		return response;

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
