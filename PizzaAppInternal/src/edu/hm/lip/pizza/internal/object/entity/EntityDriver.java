package edu.hm.lip.pizza.internal.object.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für die Fahrer. Es werden alle benötigten Fahrer-Information in dieser Klasse gehalten, wie beispielsweise
 * der Name, seine zugehörigen GPS-Daten und Bestellungen.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "driver" )
public class EntityDriver extends AbstractEntityObject
{

	private static final long serialVersionUID = -4619947632360941269L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String name;

	@OneToMany( mappedBy = "driver", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private List<EntityGPSData> gpsData;

	@OneToMany( mappedBy = "driver", fetch = FetchType.LAZY )
	private List<EntityOrder> orders;

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
	public List<EntityGPSData> getGpsData()
	{
		return gpsData;
	}

	/**
	 * Setzt das Attribut gpsData.
	 * 
	 * @param gpsData
	 *            zu setzender Wert für das Attribut gpsData
	 */
	public void setGpsData( List<EntityGPSData> gpsData )
	{
		this.gpsData = gpsData;
	}

	/**
	 * Liefert das Attribut orders.
	 * 
	 * @return orders
	 */
	public List<EntityOrder> getOrders()
	{
		return orders;
	}

	/**
	 * Setzt das Attribut orders.
	 * 
	 * @param orders
	 *            zu setzender Wert für das Attribut orders
	 */
	public void setOrders( List<EntityOrder> orders )
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
				this.getClass(), new String[] { "orders" } );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		return EqualsBuilder.reflectionEquals( this, obj, true, this.getClass(), new String[] { "orders" } );
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
		rsb.setExcludeFieldNames( new String[] { "orders" } );
		return rsb.toString();
	}

}
