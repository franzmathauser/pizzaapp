package edu.hm.lip.pizza.internal.object.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.hm.lip.pizza.internal.object.AbstractPizzaAppEntityObject;

/**
 * @author Franz Mathauser, Stefan Wörner
 */
@Entity
@Table( name = "OrderTable" )
public class Order extends AbstractPizzaAppEntityObject
{

	private static final long serialVersionUID = -5285810993289089344L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Integer id;

	@ManyToMany
	@JoinTable( name = "OrderLines",
				joinColumns = @JoinColumn( name = "order_id" ),
				inverseJoinColumns = @JoinColumn( name = "product_id" ) )
	private List<Product> products;

	@ManyToOne( fetch = FetchType.LAZY )
	private Customer customer;

	@Temporal( value = TemporalType.TIMESTAMP )
	private Date createDate;

	@ManyToOne( fetch = FetchType.LAZY )
	private Driver driver;

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
	 * Liefert das Attribut customer.
	 * 
	 * @return customer
	 */
	public Customer getCustomer()
	{
		return customer;
	}

	/**
	 * Setzt das Attribut customer.
	 * 
	 * @param customer
	 *            zu setzender Wert für das Attribut customer
	 */
	public void setCustomer( Customer customer )
	{
		this.customer = customer;
	}

	/**
	 * Liefert das Attribut createDate.
	 * 
	 * @return createDate
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * Setzt das Attribut createDate.
	 * 
	 * @param createDate
	 *            zu setzender Wert für das Attribut createDate
	 */
	public void setCreateDate( Date createDate )
	{
		this.createDate = createDate;
	}

	/**
	 * Liefert das Attribut driver.
	 * 
	 * @return driver
	 */
	public Driver getDriver()
	{
		return driver;
	}

	/**
	 * Setzt das Attribut driver.
	 * 
	 * @param driver
	 *            zu setzender Wert für das Attribut driver
	 */
	public void setDriver( Driver driver )
	{
		this.driver = driver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Order [id=" + id + ", products=" + products + ", customer=" + customer + ", createDate=" + createDate
				+ ", driver=" + driver + "]";
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
