package edu.hm.lip.pizza.internal.object.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import edu.hm.lip.pizza.internal.object.AbstractEntityObject;
import edu.hm.lip.pizza.internal.object.query.GPSDataQueryConstants;

/**
 * Entität für die GPS-Daten eines Fahrers. Es werden alle benötigten Geo-Information in dieser Klasse gehalten, wie
 * beispielsweise die lat/lon Koordinaten und der zugehörige Zeitstempel.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "gps_data" )
@NamedQueries( {
		@NamedQuery( name = GPSDataQueryConstants.GET_ALL_GPSDATA, query = GPSDataQueryConstants.GET_ALL_GPSDATA_QUERY ),
		@NamedQuery(	name = GPSDataQueryConstants.GET_DRIVERS_LAST_POSITIONS,
						query = GPSDataQueryConstants.GET_DRIVERS_LAST_POSITIONS_QUERY ) } )
public class EntityGPSData extends AbstractEntityObject
{

	private static final long serialVersionUID = -7934314990291298556L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private Double lat;

	private Double lon;

	@Temporal( value = TemporalType.TIMESTAMP )
	private Date date;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityDriver driver;

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
	public EntityDriver getDriver()
	{
		return driver;
	}

	/**
	 * Setzt das Attribut driver.
	 * 
	 * @param driver
	 *            zu setzender Wert für das Attribut driver
	 */
	public void setDriver( EntityDriver driver )
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
