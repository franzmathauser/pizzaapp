package edu.hm.lip.pizza.api.object.resource.heatmap;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import edu.hm.lip.pizza.api.object.AbstractRessourceObject;

/**
 * Resource für die Heatmap GPS-Daten. Es werden alle benötigten Lat/Lon-Information in dieser Klasse gehalten
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "GPSData" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class GPSData extends AbstractRessourceObject
{

	private static final long serialVersionUID = 4904560866203317592L;

	private Double lat;

	@XmlElement( name = "lng" )
	@JsonProperty( "lng" )
	private Double lon;

	/**
	 * Default Konstruktor.
	 */
	public GPSData()
	{

	}

	/**
	 * Erzeugt ein GPS-Datum mit lat/lon koordinaten.
	 * 
	 * @param latitude
	 *            latitude
	 * @param longitude
	 *            longitue
	 */
	public GPSData( Double latitude, Double longitude )
	{
		this.lat = latitude;
		this.lon = longitude;
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
	 * Liefert das Attribut lon.
	 * 
	 * @return lon
	 */
	public Double getLon()
	{
		return lon;
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
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#hashCode()
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
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), null );
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
		rsb.setExcludeFieldNames( null );
		return rsb.toString();
	}

}
