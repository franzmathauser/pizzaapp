package edu.hm.lip.pizza.internal.object.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.hm.basic.object.AbstractBasicObject;

/**
 * @author Franz Mathauser
 */
@Entity
@Table( name = "order_line" )
public class EntityOrderLine extends AbstractBasicObject
{

	private static final long serialVersionUID = 164883825149781318L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private Integer count;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityOrder order;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityProductConfiguration productConfiguration;

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
	 * Liefert das Attribut count.
	 * 
	 * @return count
	 */
	public Integer getCount()
	{
		return count;
	}

	/**
	 * Setzt das Attribut count.
	 * 
	 * @param count
	 *            zu setzender Wert für das Attribut count
	 */
	public void setCount( Integer count )
	{
		this.count = count;
	}

	/**
	 * Liefert das Attribut order.
	 * 
	 * @return order
	 */
	public EntityOrder getOrder()
	{
		return order;
	}

	/**
	 * Setzt das Attribut order.
	 * 
	 * @param entityOrder
	 *            zu setzender Wert für das Attribut order
	 */
	public void setOrder( EntityOrder entityOrder )
	{
		this.order = entityOrder;
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Liefert das Attribut productConfiguration.
	 * 
	 * @return productConfiguration
	 */
	public EntityProductConfiguration getProductConfiguration()
	{
		return productConfiguration;
	}

	/**
	 * Setzt das Attribut productConfiguration.
	 * 
	 * @param entityProductConfiguration
	 *            zu setzender Wert für das Attribut productConfiguration
	 */
	public void setProductConfiguration( EntityProductConfiguration entityProductConfiguration )
	{
		this.productConfiguration = entityProductConfiguration;
	}

}
