package edu.hm.lip.pizza.model;

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

@Entity
@Table(name="PizzaOrder")
public class Order
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToMany
	@JoinTable( name = "OrderLines",
				joinColumns = @JoinColumn( name = "order_id" ),
				inverseJoinColumns = @JoinColumn( name = "product_id" ) )
	private List<Product> products;

	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Driver driver;

	
	public Integer getId()
	{
		return id;
	}

	
	public void setId( Integer id )
	{
		this.id = id;
	}

	
	public List<Product> getProducts()
	{
		return products;
	}

	
	public void setProducts( List<Product> products )
	{
		this.products = products;
	}

	
	public Customer getCustomer()
	{
		return customer;
	}

	
	public void setCustomer( Customer customer )
	{
		this.customer = customer;
	}

	
	public Date getCreateDate()
	{
		return createDate;
	}

	
	public void setCreateDate( Date createDate )
	{
		this.createDate = createDate;
	}

	public Driver getDriver()
	{
		return driver;
	}


	public void setDriver( Driver driver )
	{
		this.driver = driver;
	}


	@Override
	public String toString()
	{
		return "Order [id=" + id + ", products=" + products + ", customer=" + customer + ", createDate=" + createDate
				+ ", driver=" + driver + "]";
	}
	
	
	
	
}
