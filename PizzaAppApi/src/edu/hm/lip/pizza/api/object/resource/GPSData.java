package edu.hm.lip.pizza.api.object.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;

/**
 * Resource für die GPS-Daten eines Fahrers. Es werden alle benötigten Geo-Information in dieser Klasse gehalten, wie
 * beispielsweise die lat/lon Koordinaten und der zugehörige Zeitstempel.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "GPSData" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class GPSData extends AbstractRessourceObject
{

	private static final long serialVersionUID = -988635383766944140L;

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
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				this.getClass(), new String[] { "driver" } );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), new String[] { "driver" } );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ReflectionToStringBuilder rsb = new ReflectionToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
		rsb.setAppendStatics( false );
		rsb.setAppendTransients( true );
		rsb.setUpToClass( this.getClass() );
		rsb.setExcludeFieldNames( new String[] { "driver" } );
		return rsb.toString();
	}

}
