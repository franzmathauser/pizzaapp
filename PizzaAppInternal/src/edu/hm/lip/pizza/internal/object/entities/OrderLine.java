package edu.hm.lip.pizza.internal.object.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.hm.basic.object.AbstractBasicObject;

/**
 * @author Franz Mathauser
 */
@Entity
public class OrderLine extends AbstractBasicObject
{

	private static final long serialVersionUID = 164883825149781318L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	private Integer count;

	@ManyToOne( fetch = FetchType.LAZY )
	private Order order;

	@ManyToOne( fetch = FetchType.LAZY )
	private ProductConfiguration productConfiguration;

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
	public Order getOrder()
	{
		return order;
	}

	/**
	 * Setzt das Attribut order.
	 * 
	 * @param order
	 *            zu setzender Wert für das Attribut order
	 */
	public void setOrder( Order order )
	{
		this.order = order;
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
	public ProductConfiguration getProductConfiguration()
	{
		return productConfiguration;
	}

	/**
	 * Setzt das Attribut productConfiguration.
	 * 
	 * @param productConfiguration
	 *            zu setzender Wert für das Attribut productConfiguration
	 */
	public void setProductConfiguration( ProductConfiguration productConfiguration )
	{
		this.productConfiguration = productConfiguration;
	}

}
