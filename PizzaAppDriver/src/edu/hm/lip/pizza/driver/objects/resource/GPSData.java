package edu.hm.lip.pizza.driver.objects.resource;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.hm.lip.pizza.driver.object.AbstractObject;

/**
 * Resource für die GPS-Daten eines Fahrers.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GPSData extends AbstractObject
{

	private static final long serialVersionUID = 2912274422422894743L;

	private Double m_lat;

	private Double m_lon;

	/**
	 * Konstruktor.
	 */
	public GPSData()
	{

	}

	/**
	 * Konstruktor.
	 * 
	 * @param lat
	 *            Latitude
	 * @param lon
	 *            Longitude
	 */
	public GPSData( Double lat, double lon )
	{
		m_lat = lat;
		m_lon = lon;
	}

	/**
	 * Liefert das Attribut lat.
	 * 
	 * @return lat
	 */
	@JsonProperty( "lat" )
	public Double getLat()
	{
		return m_lat;
	}

	/**
	 * Setzt das Attribut lat.
	 * 
	 * @param lat
	 *            zu setzender Wert für das Attribut lat
	 */
	@JsonProperty( "lat" )
	public void setLat( Double lat )
	{
		this.m_lat = lat;
	}

	/**
	 * Liefert das Attribut lon.
	 * 
	 * @return lon
	 */
	@JsonProperty( "lon" )
	public Double getLon()
	{
		return m_lon;
	}

	/**
	 * Setzt das Attribut lon.
	 * 
	 * @param lon
	 *            zu setzender Wert für das Attribut lon
	 */
	@JsonProperty( "lon" )
	public void setLon( Double lon )
	{
		this.m_lon = lon;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.driver.object.AbstractObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode( INITIAL_NON_ZERO_ODD_NUMBER, MULTIPLIER_NON_ZERO_ODD_NUMBER, this, true,
				this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.driver.object.AbstractObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), null );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.driver.object.AbstractObject#toString()
	 */
	@Override
	public String toString()
	{
		ReflectionToStringBuilder rsb = new ReflectionToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE );
		rsb.setAppendStatics( false );
		rsb.setAppendTransients( true );
		rsb.setUpToClass( this.getClass() );
		rsb.setExcludeFieldNames( null );
		return rsb.toString();
	}

}
