package edu.hm.lip.pizza.api.object.ressources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "GPSData" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { "application/xml", "application/json", "text/xml" } )
@Consumes( { "application/xml", "application/json", "text/xml" } )
public class GPSData 
{

	private Integer id;

	private Double lat;

	private Double lon;

	private Date date;

	private Driver driver;

	/**
	 * Liefert das Attribut id.
	 * 
	 * @return id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * Setzt das Attribut id.
	 * 
	 * @param id
	 *            zu setzender Wert für das Attribut id
	 */
	public void setId( Integer id )
	{
		this.id = id;
	}

	/**
	 * Liefert das Attribut lat.
	 * 
	 * @return lat
	 */
	public Double getLat()
	{
		return lat;
	}

	/**
	 * Setzt das Attribut lat.
	 * 
	 * @param lat
	 *            zu setzender Wert für das Attribut lat
	 */
	public void setLat( Double lat )
	{
		this.lat = lat;
	}

	/**
	 * Liefert das Attribut lon.
	 * 
	 * @return lon
	 */
	public Double getLon()
	{
		return lon;
	}

	/**
	 * Setzt das Attribut lon.
	 * 
	 * @param lon
	 *            zu setzender Wert für das Attribut lon
	 */
	public void setLon( Double lon )
	{
		this.lon = lon;
	}

	/**
	 * Liefert das Attribut date.
	 * 
	 * @return date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Setzt das Attribut date.
	 * 
	 * @param date
	 *            zu setzender Wert für das Attribut date
	 */
	public void setDate( Date date )
	{
		this.date = date;
	}

	/**
	 * Liefert das Attribut driver.
	 * 
	 * @return driver
	 */
	public Driver getDriver()
	{
		return driver;
	}

	/**
	 * Setzt das Attribut driver.
	 * 
	 * @param driver
	 *            zu setzender Wert für das Attribut driver
	 */
	public void setDriver( Driver driver )
	{
		this.driver = driver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		return "GPSData [id=" + id + ", lat=" + lat + ", lon=" + lon + ", date=" + date + ", driver=" + driver + "]";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		// TODO Auto-generated method stub
		return false;
	}
}