package edu.hm.lip.pizza.api.object.resource;

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
 * Resource für die Fahrerankunftszeit.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "DriverArrival" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class DriverArrival extends AbstractRessourceObject
{

	private static final long serialVersionUID = -1165069318983042345L;

	@XmlElement( name = "id" )
	@JsonProperty( "id" )
	private Integer driverId;

	private Long seconds;

	/**
	 * Liefert das Attribut driverId.
	 * 
	 * @return driverId
	 */
	public Integer getDriverId()
	{
		return driverId;
	}

	/**
	 * Liefert das Attribut seconds.
	 * 
	 * @return seconds
	 */
	public Long getSeconds()
	{
		return seconds;
	}

	/**
	 * Setzt das Attribut driverId.
	 * 
	 * @param driverId
	 *            zu setzender Wert für das Attribut driverId
	 */
	public void setDriverId( Integer driverId )
	{
		this.driverId = driverId;
	}

	/**
	 * Setzt das Attribut seconds.
	 * 
	 * @param seconds
	 *            zu setzender Wert für das Attribut seconds
	 */
	public void setSeconds( Long seconds )
	{
		this.seconds = seconds;
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
