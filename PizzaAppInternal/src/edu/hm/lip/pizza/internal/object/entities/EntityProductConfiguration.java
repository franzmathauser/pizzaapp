package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.hm.lip.pizza.api.object.enums.Size;
import edu.hm.lip.pizza.internal.object.AbstractEntityObject;

/**
 * Entität für die Produkt-Konfiguration. Es werden alle benötigten Produkt-Konfigurations-Information in dieser Klasse
 * gehalten, wie beispielsweise die Produktgröße und der zugehörige Produkt-Größen-Preis.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "product_configuration" )
public class EntityProductConfiguration extends AbstractEntityObject
{

	private static final long serialVersionUID = -2410008984145483351L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@Enumerated( value = EnumType.ORDINAL )
	private Size size;

	private Double price;

	@ManyToOne( fetch = FetchType.LAZY )
	private EntityProduct product;

	@OneToMany( mappedBy = "productConfiguration" )
	private List<EntityOrderLine> orderLines;

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
	 * Liefert das Attribut size.
	 * 
	 * @return size
	 */
	public Size getSize()
	{
		return size;
	}

	/**
	 * Setzt das Attribut size.
	 * 
	 * @param size
	 *            zu setzender Wert für das Attribut size
	 */
	public void setSize( Size size )
	{
		this.size = size;
	}

	/**
	 * Liefert das Attribut price.
	 * 
	 * @return price
	 */
	public Double getPrice()
	{
		return price;
	}

	/**
	 * Setzt das Attribut price.
	 * 
	 * @param price
	 *            zu setzender Wert für das Attribut price
	 */
	public void setPrice( Double price )
	{
		this.price = price;
	}

	/**
	 * Liefert das Attribut product.
	 * 
	 * @return product
	 */
	public EntityProduct getProduct()
	{
		return product;
	}

	/**
	 * Setzt das Attribut product.
	 * 
	 * @param product
	 *            zu setzender Wert für das Attribut product
	 */
	public void setProduct( EntityProduct product )
	{
		this.product = product;
	}

	/**
	 * Liefert das Attribut orderLines.
	 * 
	 * @return orderLines
	 */
	public List<EntityOrderLine> getOrderLines()
	{
		return orderLines;
	}

	/**
	 * Setzt das Attribut orderLines.
	 * 
	 * @param orderLines
	 *            zu setzender Wert für das Attribut orderLines
	 */
	public void setOrderLines( List<EntityOrderLine> orderLines )
	{
		this.orderLines = orderLines;
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

}
