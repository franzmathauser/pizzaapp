package edu.hm.lip.pizza.tsp.domain.google;

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

	/**
	 * Liefert das Attribut destinationAddresses.
	 * 
	 * @return destinationAddresses
	 */
	public List<String> getDestinationAddresses()
	{
		return destinationAddresses;
	}

	/**
	 * Setzt das Attribut destinationAddresses.
	 * 
	 * @param destinationAddresses
	 *            zu setzender Wert für das Attribut destinationAddresses
	 */
	public void setDestinationAddresses( List<String> destinationAddresses )
	{
		this.destinationAddresses = destinationAddresses;
	}

	/**
	 * Liefert das Attribut originAddresses.
	 * 
	 * @return originAddresses
	 */
	public List<String> getOriginAddresses()
	{
		return originAddresses;
	}

	/**
	 * Setzt das Attribut originAddresses.
	 * 
	 * @param originAddresses
	 *            zu setzender Wert für das Attribut originAddresses
	 */
	public void setOriginAddresses( List<String> originAddresses )
	{
		this.originAddresses = originAddresses;
	}

	/**
	 * Liefert das Attribut distanceRows.
	 * 
	 * @return distanceRows
	 */
	public List<GoogleDistanceRow> getDistanceRows()
	{
		return distanceRows;
	}

	/**
	 * Setzt das Attribut distanceRows.
	 * 
	 * @param distanceRows
	 *            zu setzender Wert für das Attribut distanceRows
	 */
	public void setDistanceRows( List<GoogleDistanceRow> distanceRows )
	{
		this.distanceRows = distanceRows;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "GoogleDistanceMatrix [destinationAddresses=" + destinationAddresses + ", originAddresses=" + originAddresses
				+ ", distanceRows=" + distanceRows + "]";
	}

}
