package edu.hm.lip.pizza.api.object.resource;

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
import edu.hm.lip.pizza.api.object.enumeration.RouteState;
import edu.hm.lip.pizza.tsp.domain.MatrixContainerAdapter.Measurements;

/**
 * Resource für eine Fahrer-Route. Es werden alle benötigten Routen-Information in dieser Klasse gehalten, wie
 * beispielsweise der Orderliste, Anfangskoordinaten, Dauer/Länge.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@XmlRootElement( name = "DriverRoute" )
@JsonSerialize( include = Inclusion.NON_NULL )
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_XML } )
public class DriverRoute extends AbstractRessourceObject
{

	private static final long serialVersionUID = 1238714315351257250L;

	private Double originLat;

	private Double originLon;

	private Long startTime;

	private Measurements measurement;

	private Long measurementValue;

	private List<Order> orders;

	private RouteState status;

	/**
	 * Liefert das Attribut originLat.
	 * 
	 * @return originLat
	 */
	public Double getOriginLat()
	{
		return originLat;
	}

	/**
	 * Setzt das Attribut originLat.
	 * 
	 * @param originLat
	 *            zu setzender Wert für das Attribut originLat
	 */
	public void setOriginLat( Double originLat )
	{
		this.originLat = originLat;
	}

	/**
	 * Liefert das Attribut originLon.
	 * 
	 * @return originLon
	 */
	public Double getOriginLon()
	{
		return originLon;
	}

	/**
	 * Setzt das Attribut originLon.
	 * 
	 * @param originLon
	 *            zu setzender Wert für das Attribut originLon
	 */
	public void setOriginLon( Double originLon )
	{
		this.originLon = originLon;
	}

	/**
	 * Liefert das Attribut startTime.
	 * 
	 * @return startTime
	 */
	public Long getStartTime()
	{
		return startTime;
	}

	/**
	 * Setzt das Attribut startTime.
	 * 
	 * @param startTime
	 *            zu setzender Wert für das Attribut startTime
	 */
	public void setStartTime( Long startTime )
	{
		this.startTime = startTime;
	}

	/**
	 * Liefert das Attribut measurement.
	 * 
	 * @return measurement
	 */
	public Measurements getMeasurement()
	{
		return measurement;
	}

	/**
	 * Setzt das Attribut measurement.
	 * 
	 * @param measurement
	 *            zu setzender Wert für das Attribut measurement
	 */
	public void setMeasurement( Measurements measurement )
	{
		this.measurement = measurement;
	}

	/**
	 * Liefert das Attribut measurementValue.
	 * 
	 * @return measurementValue
	 */
	public Long getMeasurementValue()
	{
		return measurementValue;
	}

	/**
	 * Setzt das Attribut measurementValue.
	 * 
	 * @param measurementValue
	 *            zu setzender Wert für das Attribut measurementValue
	 */
	public void setMeasurementValue( Long measurementValue )
	{
		this.measurementValue = measurementValue;
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
	 * Liefert das Attribut status.
	 * 
	 * @return status
	 */
	public RouteState getStatus()
	{
		return status;
	}

	/**
	 * Setzt das Attribut status.
	 * 
	 * @param status
	 *            zu setzender Wert für das Attribut status
	 */
	public void setStatus( RouteState status )
	{
		this.status = status;
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
