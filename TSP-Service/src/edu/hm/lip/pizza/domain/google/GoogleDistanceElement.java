package edu.hm.lip.pizza.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Repräsentiert ein Distance-Element des Json-Response der GoogleMaps API.
 * 
 * @author Franz Mathauser
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GoogleDistanceElement
{

	private GoogleDistance distance;

	private GoogleDurration duration;

	/**
	 * Status Codes die durch Google übergeben werden können.
	 * 
	 * OK gibt an, dass die Antwort ein gültiges result enthält. <br />
	 * NOT_FOUND gibt an, dass der Ursprungsort und/oder Zielort dieses Paares nicht geocodiert werden konnte.<br /> 
	 * ZERO_RESULTS gibt an, dass zwischen Ursprungsort und Zielort keine Route gefunden werden konnte.<br />
	 */
	private String status;

	public GoogleDistance getDistance()
	{
		return distance;
	}

	public GoogleDurration getDuration()
	{
		return duration;
	}

	public String getStatus()
	{
		return status;
	}

	public void setDistance( GoogleDistance distance )
	{
		this.distance = distance;
	}

	public void setDuration( GoogleDurration duration )
	{
		this.duration = duration;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return "GoogleDistanceRow [distance=" + distance + ", duration=" + duration + ", status=" + status + "]";
	}

}
