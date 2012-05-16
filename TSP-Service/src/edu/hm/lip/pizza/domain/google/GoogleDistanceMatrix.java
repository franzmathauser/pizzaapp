package edu.hm.lip.pizza.domain.google;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Repräsentiert die Distance-Matrix des Json-Response der GoogleMaps API.
 * 
 * @author Franz Mathauser
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GoogleDistanceMatrix
{

	/**
	 * List of destination Adressen
	 */
	@JsonProperty( "destination_addresses" )
	private List<String> destinationAddresses;

	/**
	 * List of origin Adressen
	 */
	@JsonProperty( "origin_addresses" )
	private List<String> originAddresses;

	/**
	 * List of Distance Informations
	 */
	@JsonProperty( "rows" )
	private List<GoogleDistanceRow> distanceRows;

	public List<String> getDestinationAddresses()
	{
		return destinationAddresses;
	}

	public void setDestinationAddresses( List<String> destinationAddresses )
	{
		this.destinationAddresses = destinationAddresses;
	}

	public List<String> getOriginAddresses()
	{
		return originAddresses;
	}

	public void setOriginAddresses( List<String> originAddresses )
	{
		this.originAddresses = originAddresses;
	}

	public List<GoogleDistanceRow> getDistanceRows()
	{
		return distanceRows;
	}

	public void setDistanceRows( List<GoogleDistanceRow> distanceRows )
	{
		this.distanceRows = distanceRows;
	}

	@Override
	public String toString()
	{
		return "GoogleDistanceMatrix [destinationAddresses=" + destinationAddresses + ", originAddresses=" + originAddresses
				+ ", distanceRows=" + distanceRows + "]";
	}

}
