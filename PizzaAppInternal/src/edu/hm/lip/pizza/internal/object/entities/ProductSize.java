package edu.hm.lip.pizza.internal.object.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import edu.hm.lip.pizza.internal.object.AbstractPizzaAppEntityObject;
import edu.hm.lip.pizza.internal.object.enums.Size;

/**
 * Entität für die Produktgröße. Enthält neben der Größe des Produkts (
 * {@link edu.hm.lip.pizza.internal.object.enums.Size}) auch den der Größe zugeordneten Produktpreis.
 * 
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
public class ProductSize extends AbstractPizzaAppEntityObject
{

	private static final long serialVersionUID = -4869069978324781L;

	@Id
	@Enumerated( value = EnumType.ORDINAL )
	private Size size;

	private Double price;

	@ManyToMany( mappedBy = "sizes" )
	private List<Product> products;

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
	 * Liefert das Attribut products.
	 * 
	 * @return products
	 */
	public List<Product> getProducts()
	{
		return products;
	}

	/**
	 * Setzt das Attribut products.
	 * 
	 * @param products
	 *            zu setzender Wert für das Attribut products
	 */
	public void setProducts( List<Product> products )
	{
		this.products = products;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.basic.object.AbstractBasicObject#toString()
	 */
	@Override
	public String toString()
	{
		return "ProductSize [size=" + size + ", price=" + price + ", products=" + products + "]";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see edu.hm.lip.pizza.internal.object.AbstractPizzaAppEntityObject#validate()
	 */
	@Override
	public void validate()
	{
		// TODO Auto-generated method stub

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
