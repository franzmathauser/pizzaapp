package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.hm.basic.object.AbstractBasicObject;

/**
 * Entität für die Fahrers. Es werden alle benötigten Fahrer-Information in dieser Klasse gehalten, wie beispielsweise
 * der Name, GPS-Daten, Bestellungen
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "driver" )
public class EntityDriver extends AbstractBasicObject
{

	private static final long serialVersionUID = -1902138035458443546L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private String name;

	@OneToMany( mappedBy = "driver" )
	private List<EntityGPSData> gpsData;

	@OneToMany( mappedBy = "driver" )
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
	 * @param entityOrders
	 *            zu setzender Wert für das Attribut orders
	 */
	public void setOrders( List<EntityOrder> entityOrders )
	{
		this.orders = entityOrders;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		return "Driver [id=" + id + ", name=" + name + ", gpsData=" + gpsData + ", orders=" + orders + "]";
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
