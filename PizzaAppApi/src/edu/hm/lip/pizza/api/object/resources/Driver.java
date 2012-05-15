package edu.hm.lip.pizza.api.object.resources;

import java.util.List;

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
 * Resource für die Fahrer. Es werden alle benötigten Fahrer-Information in dieser Klasse gehalten, wie beispielsweise
 * der Name, seine zugehörigen GPS-Daten und Bestellungen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "Driver" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class Driver extends AbstractRessourceObject
{

	private static final long serialVersionUID = 1156952828196122081L;

	private Integer id;

	private String name;

	private List<GPSData> gpsData;

	private List<Order> orders;

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
	 * Liefert das Attribut name.
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setzt das Attribut name.
	 * 
	 * @param name
	 *            zu setzender Wert für das Attribut name
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Liefert das Attribut gpsData.
	 * 
	 * @return gpsData
	 */
	public List<GPSData> getGpsData()
	{
		return gpsData;
	}

	/**
	 * Setzt das Attribut gpsData.
	 * 
	 * @param gpsData
	 *            zu setzender Wert für das Attribut gpsData
	 */
	public void setGpsData( List<GPSData> gpsData )
	{
		this.gpsData = gpsData;
	}

	/**
	 * Liefert das Attribut orders.
	 * 
	 * @return orders
	 */
	public List<Order> getOrders()
	{
		return orders;
	}

	/**
	 * Setzt das Attribut orders.
	 * 
	 * @param orders
	 *            zu setzender Wert für das Attribut orders
	 */
	public void setOrders( List<Order> orders )
	{
		this.orders = orders;
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
