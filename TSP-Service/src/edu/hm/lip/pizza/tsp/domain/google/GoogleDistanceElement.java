package edu.hm.lip.pizza.tsp.domain.google;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Repräsentiert ein Distance-Element des Json-Response der GoogleMaps API.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GoogleDistanceElement
{

	private GoogleDistance distance;

	private GoogleDurration duration;

	/**
	 * Status Codes die durch Google übergeben werden können. OK gibt an, dass die Antwort ein gültiges result enthält. <br />
	 * NOT_FOUND gibt an, dass der Ursprungsort und/oder Zielort dieses Paares nicht geocodiert werden konnte.<br />
	 * ZERO_RESULTS gibt an, dass zwischen Ursprungsort und Zielort keine Route gefunden werden konnte.<br />
	 */
	private String status;

	/**
	 * Liefert das Attribut distance.
	 * 
	 * @return distance
	 */
	public GoogleDistance getDistance()
	{
		return distance;
	}

	/**
	 * Setzt das Attribut distance.
	 * 
	 * @param distance
	 *            zu setzender Wert für das Attribut distance
	 */
	public void setDistance( GoogleDistance distance )
	{
		this.distance = distance;
	}

	/**
	 * Liefert das Attribut duration.
	 * 
	 * @return duration
	 */
	public GoogleDurration getDuration()
	{
		return duration;
	}

	/**
	 * Setzt das Attribut duration.
	 * 
	 * @param duration
	 *            zu setzender Wert für das Attribut duration
	 */
	public void setDuration( GoogleDurration duration )
	{
		this.duration = duration;
	}

	/**
	 * Liefert das Attribut status.
	 * 
	 * @return status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Setzt das Attribut status.
	 * 
	 * @param status
	 *            zu setzender Wert für das Attribut status
	 */
	public void setStatus( String status )
	{
		this.status = status;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "GoogleDistanceRow [distance=" + distance + ", duration=" + duration + ", status=" + status + "]";
	}

}
