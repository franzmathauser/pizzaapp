package edu.hm.lip.pizza.driver.objects.resource;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.hm.lip.pizza.driver.object.AbstractObject;
import edu.hm.lip.pizza.driver.objects.resource.enumeration.Measurement;
import edu.hm.lip.pizza.driver.objects.resource.enumeration.RouteState;

/**
 * Ressource für die Route des Fahrers.
 * 
 * @author Stefan Wörner
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class DriverRoute extends AbstractObject
{

	private static final long serialVersionUID = -1267864727794560303L;

	private Double m_originLat;

	private Double m_originLon;

	private Long m_startTime;

	private Measurement m_measurement;

	private Long m_measurementValue;

	private List<Order> orders;

	private RouteState m_status;

	/**
	 * Liefert das Attribut originLat.
	 * 
	 * @return originLat
	 */
	public Double getOriginLat()
	{
		return m_originLat;
	}

	/**
	 * Setzt das Attribut originLat.
	 * 
	 * @param originLat
	 *            zu setzender Wert für das Attribut originLat
	 */
	public void setOriginLat( Double originLat )
	{
		this.m_originLat = originLat;
	}

	/**
	 * Liefert das Attribut originLon.
	 * 
	 * @return originLon
	 */
	public Double getOriginLon()
	{
		return m_originLon;
	}

	/**
	 * Setzt das Attribut originLon.
	 * 
	 * @param originLon
	 *            zu setzender Wert für das Attribut originLon
	 */
	public void setOriginLon( Double originLon )
	{
		this.m_originLon = originLon;
	}

	/**
	 * Liefert das Attribut startTime.
	 * 
	 * @return startTime
	 */
	public Long getStartTime()
	{
		return m_startTime;
	}

	/**
	 * Setzt das Attribut startTime.
	 * 
	 * @param startTime
	 *            zu setzender Wert für das Attribut startTime
	 */
	public void setStartTime( Long startTime )
	{
		this.m_startTime = startTime;
	}

	/**
	 * Liefert das Attribut measurement.
	 * 
	 * @return measurement
	 */
	public Measurement getMeasurement()
	{
		return m_measurement;
	}

	/**
	 * Setzt das Attribut measurement.
	 * 
	 * @param measurement
	 *            zu setzender Wert für das Attribut measurement
	 */
	public void setMeasurement( Measurement measurement )
	{
		this.m_measurement = measurement;
	}

	/**
	 * Liefert das Attribut measurementValue.
	 * 
	 * @return measurementValue
	 */
	public Long getMeasurementValue()
	{
		return m_measurementValue;
	}

	/**
	 * Setzt das Attribut measurementValue.
	 * 
	 * @param measurementValue
	 *            zu setzender Wert für das Attribut measurementValue
	 */
	public void setMeasurementValue( Long measurementValue )
	{
		this.m_measurementValue = measurementValue;
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
		return m_status;
	}

	/**
	 * Setzt das Attribut status.
	 * 
	 * @param status
	 *            zu setzender Wert für das Attribut status
	 */
	public void setStatus( RouteState status )
	{
		this.m_status = status;
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
